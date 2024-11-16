package com.example.kanjimemorized.ui.screens.statistics

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PieChart(
    modifier: Modifier = Modifier.size(size = 300.dp),
    inputMap: Map<String, Int>,
    colorMap: Map<String, Color>
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
            ) {
            val radius: Float = size.height/2f
            Log.i("PieChart.kt", "Radius: $radius")
            drawArcs(radius = radius, inputMap = inputMap, colorMap = colorMap)
        }
    }
}

fun DrawScope.drawArcs(
    radius: Float,
    inputMap: Map<String, Int>,
    colorMap: Map<String, Color>
) {
    var currentStartAngle = 0f
    drawArc(
        color = colorMap["Mastered"]!!,
        startAngle = currentStartAngle,
        sweepAngle = inputMap["Mastered"]!!*(360f/2136f),
        useCenter = true,
        size = Size(width = radius*2, height = radius*2),
        style = Fill
    )
    currentStartAngle += inputMap["Mastered"]!!*(360f/2136f)
    drawArc(
        color = colorMap["Known"]!!,
        startAngle = currentStartAngle,
        sweepAngle = inputMap["Known"]!!*(360f/2136f),
        useCenter = true,
        size = Size(width = radius*2, height = radius*2),
        style = Fill
    )
    currentStartAngle += inputMap["Known"]!!*(360f/2136f)
    drawArc(
        color = colorMap["Unknown"]!!,
        startAngle = currentStartAngle,
        sweepAngle = inputMap["Unknown"]!!*(360f/2136f),
        useCenter = true,
        size = Size(width = radius*2, height = radius*2),
        style = Fill
    )
}

@Preview(showBackground = true)
@Composable
fun PieChartPreview() { PieChart(
    inputMap = mapOf("Unknown" to 1612, "Known" to 412, "Mastered" to 112),
    colorMap = mapOf("Unknown" to MaterialTheme.colorScheme.tertiary, "Known" to MaterialTheme.colorScheme.secondary, "Mastered" to MaterialTheme.colorScheme.primary)
) }