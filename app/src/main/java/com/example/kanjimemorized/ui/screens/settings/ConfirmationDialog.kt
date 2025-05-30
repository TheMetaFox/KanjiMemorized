package com.example.kanjimemorized.ui.screens.settings

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationDialog(
    onSettingsEvent: (SettingsEvent) -> Unit,
    settingsState: SettingsState,
    modifier: Modifier = Modifier
) {
    BasicAlertDialog(
        onDismissRequest = {
            onSettingsEvent(SettingsEvent.HideConfirmationDialog)
        },
        modifier = modifier
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
//            border = BorderStroke(
//                width = 1.dp,
//                color = MaterialTheme.colorScheme.primary
//            )
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Confirm",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 36.sp
                )
                    Text(
                        text = buildAnnotatedString {
                            append("Allow Kanji Memorized to use services provided by ")
                            withLink(
                                LinkAnnotation.Url(
                                    url = when (settingsState.confirmationSetting) {
                                        SettingType.ANALYTICS_ENABLED -> "https://policies.google.com/technologies/partner-sites"
                                        SettingType.CRASHLYTICS_ENABLED -> "https://firebase.google.com/docs/android/play-data-disclosure#crashlytics"
                                        else -> ""
                                    },
                                    styles = TextLinkStyles(
                                        style = SpanStyle(
                                            color = MaterialTheme.colorScheme.tertiary
                                        )
                                    )
                                ),
                            ) {
                                when (settingsState.confirmationSetting) {
                                    SettingType.ANALYTICS_ENABLED-> append("Google Analytics")
                                    SettingType.CRASHLYTICS_ENABLED -> append("Firebase Crashlytics")
                                    else -> { }
                                }
                            }
                            append(" to track and analyze in-app usage data to help us improve the app in future releases.")
                        }
                    )
                Row(
                    modifier = Modifier
                        .align(Alignment.End),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    Button(
                        onClick = {
                            onSettingsEvent(SettingsEvent.HideConfirmationDialog)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.primary,
                        ),
                        border = BorderStroke(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = "Cancel"
                        )
                    }
                    Button(
                        onClick = {
                            Log.i("ConfirmationScreen.kt", "Checked changed...")
                            when (settingsState.confirmationSetting) {
                                SettingType.ANALYTICS_ENABLED -> onSettingsEvent(SettingsEvent.UpdateSwitch(switch = SettingType.ANALYTICS_ENABLED, checked = true))
                                SettingType.CRASHLYTICS_ENABLED -> onSettingsEvent(SettingsEvent.UpdateSwitch(switch = SettingType.CRASHLYTICS_ENABLED, checked = true))
                                else -> { }
                            }
                            onSettingsEvent(SettingsEvent.UpdateButtons)
                            onSettingsEvent(SettingsEvent.HideConfirmationDialog)
                        }
                    ) {
                        Text(
                            text = "Confirm"
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ConfirmationDialogPreview() {
    ConfirmationDialog(
        onSettingsEvent = { },
        settingsState = SettingsState(),
    )
}