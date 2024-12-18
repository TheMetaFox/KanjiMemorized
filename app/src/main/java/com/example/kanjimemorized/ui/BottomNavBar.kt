package com.example.kanjimemorized.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.InsertChart
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
//import com.google.firebase.analytics.FirebaseAnalytics
//import com.google.firebase.analytics.ktx.analytics
//import com.google.firebase.analytics.logEvent
//import com.google.firebase.ktx.Firebase

@Composable
fun BottomNavBar(
    navController: NavHostController
    ) {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = {
//                Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
//                    param("screen", "Home")
//                }
                navController.navigate(Screen.Home.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = "Home Icon"
                )
            },
            label = {
                Text(text = "Home")
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {
//                Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
//                    param("screen", "Library")
//                }
                navController.navigate(Screen.Library.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Book,
                    contentDescription = "Library Icon"
                )
            },
            label = {
                Text(text = "Library")
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {
//                Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
//                    param("screen", "Statistics")
//                }
                navController.navigate(Screen.Statistics.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.InsertChart,
                    contentDescription = "Statistics Icon"
                )
            },
            label = {
                Text(text = "Statistics")
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {
//                Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
//                    param("screen", "Settings")
//                }
                navController.navigate(Screen.Settings.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Settings Icon"
                )
            },
            label = {
                Text(text = "Settings")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    BottomNavBar(navController = rememberNavController())
}