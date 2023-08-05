package com.example.kanjimemorized.ui.screens.library.ideogram

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.ui.theme.spacing

@Composable
fun IdeogramScreen(
    modifier: Modifier,
    navController: NavController,
    ideogramState: IdeogramState,
    onIdeogramEvent: (IdeogramEvent) -> Unit
) {
    Scaffold (
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                start = MaterialTheme.spacing.small,
                top = MaterialTheme.spacing.small,
                end = MaterialTheme.spacing.small
            )
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Row(
                modifier = Modifier
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = ideogramState.ideogram?.unicode.toString(),
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 100.sp)
                }
            }
            Column(

            ) {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IdeogramScreenPreview() {
    IdeogramScreen(modifier = Modifier, navController = rememberNavController(), IdeogramState()) {}
}