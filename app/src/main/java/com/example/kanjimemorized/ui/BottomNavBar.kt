package com.example.kanjimemorized.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.InsertChart
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.ui.theme.KanjiMemorizedTheme

@Composable
fun BottomNavBar(
    selected: String,
    navController: NavHostController
    ) {
    NavigationBar(
        modifier = Modifier
            .wrapContentWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(MaterialTheme.colorScheme.outline, MaterialTheme.colorScheme.background),
                    endY = 50f
                )
            ),
        ) {
        listOf(
            listOf(Screen.Home.route, Icons.Outlined.Home, "Home"),
            listOf(Screen.Library.route, Icons.Outlined.Book, "Library"),
            listOf(Screen.Statistics.route, Icons.Outlined.InsertChart, "Statistics"),
            listOf(Screen.Settings.route, Icons.Outlined.Settings, "Settings")
        ).forEach {
            NavigationBarItem(
                selected = (selected == it[2]),
                onClick = {
                    navController.navigate(it[0] as String)
                },
                icon = {
                    Icon(
                        imageVector = it[1] as ImageVector,
                        contentDescription = "${it[2]} Icon"
                    )
                },
                label = {
                    Text(
                        text = it[2].toString(),
                        fontSize = 10.sp,
                        letterSpacing = 0.1f.sp
                    )
                },
                alwaysShowLabel = false
            )

        }
    }
}

class SelectedParameterPreview: PreviewParameterProvider<String> {
    override val values: Sequence<String> get() = sequenceOf(
        "Home",
        "Library",
        "Statistics",
        "Settings"
    )
}


@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BottomNavBarPreview_LightDark(
    @PreviewParameter(provider = SelectedParameterPreview::class)
    selected: String
) {
    KanjiMemorizedTheme {
        BottomNavBar(selected = selected, navController = rememberNavController())
    }
}

@Preview(name = "85%", fontScale = 0.85f)
@Preview(name = "150%", fontScale = 1.5f)
@Preview(name = "200%", fontScale = 2f)
@Composable
fun BottomNavBarPreview_FontScale(
    @PreviewParameter(provider = SelectedParameterPreview::class)
    selected: String
) {
    KanjiMemorizedTheme {
        BottomNavBar(selected = selected, navController = rememberNavController())
    }
}