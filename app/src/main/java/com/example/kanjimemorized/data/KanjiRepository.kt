package com.example.kanjimemorized.data

import android.util.Log
import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.data.entities.Review
import com.example.kanjimemorized.data.entities.Settings
import com.example.kanjimemorized.ui.screens.settings.SettingType
import kotlinx.coroutines.flow.Flow
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalDateTime.parse
import java.time.format.DateTimeFormatter
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.pow

class KanjiRepository(private val kanjiDao: KanjiDao) {
    suspend fun resetKanjiData() {
        kanjiDao.resetKanjiData()
        kanjiDao.deleteAllReviews()
    }

    suspend fun deleteReviewsFromKanji(kanji: Char) {
        kanjiDao.deleteReviewsFromKanji(kanji = kanji)
    }

    suspend fun upsertKanji(kanji: Kanji) {
        kanjiDao.upsertKanji(
            kanji = kanji
        )
    }

    suspend fun insertReview(review: Review) {
        kanjiDao.insertReview(
            review = review
        )
    }

    suspend fun updateSettings(settingType: SettingType, setValue: String) {
        kanjiDao.updateSettings(settingType = settingType, setValue = setValue)
    }

    suspend fun getSettingsFromCode(settingType: SettingType): Settings {
        if (kanjiDao.getSettingsFromCode(settingType = settingType) == null) {
            Log.i("KanjiRepository.kt", "Setting code not found...")
            val setting: Settings = when (settingType) {
                SettingType.DAILY_NEW_KANJI -> {
                    Settings(
                        code = SettingType.DAILY_NEW_KANJI,
                        setValue = "3",
                        defaultValue = "3"
                    )
                }
                SettingType.INITIAL_EASE -> {
                    Settings(
                        code = SettingType.INITIAL_EASE,
                        setValue = "2.5",
                        defaultValue = "2.5"
                    )
                }
                SettingType.RETENTION_THRESHOLD -> {
                    Settings(
                        code = SettingType.RETENTION_THRESHOLD,
                        setValue = "80",
                        defaultValue = "80"
                    )
                }
                SettingType.ANALYTICS_ENABLED -> {
                    Settings(
                        code = SettingType.ANALYTICS_ENABLED,
                        setValue = "false",
                        defaultValue = "false"
                    )
                }
                SettingType.CRASHLYTICS_ENABLED -> {
                    Settings(
                        code = SettingType.CRASHLYTICS_ENABLED,
                        setValue = "false",
                        defaultValue = "false"
                    )
                }
            }
            Log.i("KanjiRepository.kt", "Setting code '${setting.code}' initialized...")
            kanjiDao.insertSetting(setting = setting)
        }
        return kanjiDao.getSettingsFromCode(settingType = settingType)!!
    }

    suspend fun getKanjiComponentsFromKanji(kanji: Char): List<Kanji> {
        return kanjiDao.getKanjiComponentsFromKanji(
            kanji = kanji
        )
    }

    suspend fun getKanjiComponentMeaningsFromKanji(kanji: Char): List<List<String>> {
        var list = listOf<List<String>>()
        kanjiDao.getKanjiComponentsFromKanji(kanji).forEach {
            list = list.plus(listOf(getMeaningsFromKanji(it.unicode)))
        }
        return list
    }

    suspend fun getKanjiComponentsLatestDatesFromKanji(kanji: Char): List<LocalDateTime?> {
        var list = listOf<LocalDateTime?>()
        kanjiDao.getKanjiComponentsFromKanji(
            kanji = kanji
        ).forEach {
            Log.i("KanjiRepository.kt", "Getting kanji component latest date:$it")
            list = list.plus(getLatestSuccessfulReviewDateFromKanji(it.unicode))
        }
        Log.i("KanjiRepository.kt", "Kanji components' latest date list:$list")
        return list
    }

    suspend fun getMeaningsFromKanji(kanji: Char?): List<String> {
        if (kanji == null) {
            Log.i("KanjiRepository.kt", "No kanji provided")
            return listOf()
        }
        return kanjiDao.getMeaningsFromKanji(
            kanji = kanji
        )
    }

    suspend fun getReviewsFromKanji(kanji: Char): List<Review> {
        return kanjiDao.getReviewsFromKanji(
            kanji = kanji
        )
    }

    suspend fun getRetentionFromKanji(kanji: Char): Float {
        val durability: Float = kanjiDao.getDurabilityFromKanji(kanji = kanji)
        if (durability == 0f) {
            return 0f
        }
        val minutes: Float = (Duration.between(
            getLatestSuccessfulReviewDateFromKanji(kanji = kanji),
            LocalDateTime.now()
        ).toMinutes()).toFloat()
        val retention = exp(-((minutes / 1440) / durability))
        //Log.i("KanjiRepository.kt", "Calculating retention with $minutes minutes and $durability durability: $retention")
        return retention
    }

    suspend fun getForecastFromKanji(kanji: Char): Float {
        val durability: Int = kanjiDao.getDurabilityFromKanji(kanji = kanji).toInt()
        if (durability == 0) {
            return 0f
        }
        val minutes: Double = (Duration.between(
            getLatestSuccessfulReviewDateFromKanji(kanji = kanji),
            LocalDateTime.now()
        ).toMinutes()).toDouble()
        val forecast = (1440*ln((getSettingsFromCode(SettingType.RETENTION_THRESHOLD).setValue.toFloat()/100f).pow(-durability)) - minutes).toFloat()
//        Log.i("KanjiRepository.kt", "Minutes: $minutes with Forecast: $forecast")

        return forecast
    }

    private suspend fun getLatestSuccessfulReviewDateFromKanji(kanji: Char): LocalDateTime? {
        val date: String? = kanjiDao.getLatestSuccessfulReviewDateFromKanji(kanji = kanji)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        Log.i("KanjiRepository.kt", "Latest review date of $kanji is $date")
        return if (date == null) null else parse(date, formatter)
    }

    suspend fun getKanjiList(): List<Kanji> {
        return kanjiDao.getKanjiList()
    }

    suspend fun getKnownKanjiList(): List<Kanji> {
        return kanjiDao.getKnownKanjiList()
    }

    suspend fun getUnlockedKanjiList(): List<Kanji> {
        var unlockedKanjiList: List<Kanji> = listOf()
        val calculatedRetentions: MutableMap<Kanji, Float> = mutableMapOf()
        kanjiDao.getKanjiList().forEach { kanji ->
            var isLocked = false
            getKanjiComponentsFromKanji(kanji.unicode).forEach { component ->
                if (!calculatedRetentions.contains(component)) {
                    calculatedRetentions[component] = getRetentionFromKanji(component.unicode)
                }
                if (calculatedRetentions[component]!! <= getSettingsFromCode(SettingType.RETENTION_THRESHOLD).setValue.toFloat()/100f) {
                    isLocked = true
                }
            }
            if (!isLocked) {
                unlockedKanjiList = unlockedKanjiList.plus(kanji)
            }
        }
        return unlockedKanjiList
    }

    suspend fun isKanjiAvailable(kanji: Kanji): Boolean {
        var isAvailable = true
        getKanjiComponentsFromKanji(kanji.unicode).forEach { component ->
            if (getRetentionFromKanji(component.unicode) <= getSettingsFromCode(SettingType.RETENTION_THRESHOLD).setValue.toFloat()/100f) {
                isAvailable = false
            }
        }
        return isAvailable
    }

    suspend fun getEarliestDateCountFromToday(): Int {
        return kanjiDao.getEarliestDateCountFromToday(today = LocalDate.now().toString())
    }

//    suspend fun getRandomKanji(): Kanji {
//        return kanjiDao.getKanjiList().random()
//    }

    fun getKanjiOrderedByUnicode(): Flow<List<Kanji>> {
        return kanjiDao.getKanjiOrderedByUnicode()
    }

    fun getKanjiOrderedByStrokes(): Flow<List<Kanji>> {
        return kanjiDao.getKanjiOrderedByStrokes()
    }

    fun getKanjiOrderedByDurability(): Flow<List<Kanji>> {
        return kanjiDao.getKanjiOrderedByDurability()
    }

    fun getMeaningOrderedByUnicode(): Flow<List<String>> {
        return kanjiDao.getMeaningOrderedByUnicode()
    }

    fun getMeaningOrderedByStrokes(): Flow<List<String>> {
        return kanjiDao.getMeaningOrderedByStrokes()
    }

    fun getMeaningOrderedByDurability(): Flow<List<String>> {
        return kanjiDao.getMeaningOrderedByDurability()
    }

    fun getLatestDateOrderedByUnicode(): Flow<List<String?>> {
        return kanjiDao.getLatestDateOrderedByUnicode()
    }

    fun getLatestDateOrderedByStrokes(): Flow<List<String?>> {
        return kanjiDao.getLatestDateOrderedByStrokes()
    }

    fun getLatestDateOrderedByDurability(): Flow<List<String?>> {
        return kanjiDao.getLatestDateOrderedByDurability()
    }

    suspend fun getSettings(): List<Settings> {
        Log.i("KanjiRepository.kt", kanjiDao.getSettings().toString())
        return kanjiDao.getSettings()
    }
}