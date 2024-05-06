package com.example.kanjimemorized.ui.screens.library.kanji

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kanjimemorized.ui.theme.spacing
import java.time.temporal.TemporalField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KanjiReviewDataDialog(
    onKanjiEvent: (KanjiEvent) -> Unit,
    kanjiState: KanjiState,
    modifier: Modifier = Modifier
) {
    BasicAlertDialog(
        onDismissRequest = {
            onKanjiEvent(KanjiEvent.HideKanjiReviewData)
        }
    ) {
        Surface(
            modifier = Modifier
                .size(300.dp, 500.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(MaterialTheme.spacing.small)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Date & Time",
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 28.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Box(
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Rating",
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 28.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier
                ) {
                    items(kanjiState.reviews) { review ->
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                            ) {
                                Text(
                                    text = review.date,
                                    modifier = Modifier.align(Alignment.Center),
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            Box(
                                modifier = Modifier
                            ) {
                                Text(
                                    text = review.rating.toString(),
                                    modifier = Modifier.align(Alignment.Center),
                                    fontSize = 24.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KanjiReviewDataDialogPreview() {
    KanjiReviewDataDialog(onKanjiEvent = { }, kanjiState = KanjiState())
}
