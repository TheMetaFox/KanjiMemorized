package com.example.kanjimemorized.ui

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.tooling.preview.Preview
import com.example.kanjimemorized.ui.theme.KanjiMemorizedTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = title) },
        modifier = modifier,
        actions = {
            var showDropdownMenu by remember { mutableStateOf(false) }
            IconButton(
                onClick = { showDropdownMenu = true },
                colors = IconButtonDefaults.iconButtonColors().copy(
//                            contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = "more vertical"
                )
            }
            val localUriHandler: UriHandler = LocalUriHandler.current
            DropdownMenu(
                expanded = showDropdownMenu,
                onDismissRequest = { showDropdownMenu = false },
            ) {
                DropdownMenuItem(
                    text = { Text("App Guide") },
                    onClick = {
                        showDropdownMenu = false
                        localUriHandler.openUri("https://github.com/TheMetaFox/KanjiMemorized?tab=readme-ov-file#app-guide")
                    },
                    leadingIcon = { Icon(Icons.Outlined.Info, "info") }
                )
                DropdownMenuItem(
                    text = { Text("Feedback") },
                    onClick = {
                        showDropdownMenu = false
                        localUriHandler.openUri("https://docs.google.com/forms/d/e/1FAIpQLScQzby5vRCzXCFfAlnzWv6iUmuMwS1J6PlYcG7HzOxW8hTwnw/viewform?usp=sf_link")
                    },
                    leadingIcon = { Icon(Icons.Outlined.Feedback, "feedback") }
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        )
    )
}

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TopBarPreview_LightDark() {
    KanjiMemorizedTheme {
        TopBar(title = "Home")
    }
}

@Preview(name = "85%", fontScale = 0.85f)
@Preview(name = "150%", fontScale = 1.5f)
@Preview(name = "200%", fontScale = 2f)
@Composable
fun TopBarPreview_FontScale() {
    KanjiMemorizedTheme {
        TopBar(title = "Home")
    }
}