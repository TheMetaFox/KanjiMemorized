package com.example.kanjimemorized

import com.example.kanjimemorized.data.entities.relations.KanjiComponentCrossRef
import javax.inject.Singleton

object ComponentData {
    @Singleton
    val ComponentData: Array<KanjiComponentCrossRef> = arrayOf(
        KanjiComponentCrossRef(kanjiUnicode = '\u597d', componentUnicode = '\u5973'),
        KanjiComponentCrossRef(kanjiUnicode = '\u597d', componentUnicode = '\u5B50'),
        KanjiComponentCrossRef(kanjiUnicode = '\u674E', componentUnicode = '\u5B50'),
        KanjiComponentCrossRef(kanjiUnicode = '\u674E', componentUnicode = '\u6728'),
    )
}