package com.example.kanjimemorized.data.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.kanjimemorized.data.entities.Kanji

data class ComponentsOfKanji(
    @Embedded val kanji: Kanji,
    @Relation(
        parentColumn = "unicode",
        entityColumn = "unicode",
        associateBy = Junction(KanjiComponentCrossRef::class)
    )
    val components: List<Kanji>

)
