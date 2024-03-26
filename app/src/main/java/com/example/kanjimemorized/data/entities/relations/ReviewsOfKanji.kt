package com.example.kanjimemorized.data.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.data.entities.Review


data class ReviewsOfKanji(
    @Embedded val kanji: Kanji,
    @Relation(
        parentColumn = "unicode",
        entityColumn = "unicode"
    )
    val reviews: List<Review>
)
