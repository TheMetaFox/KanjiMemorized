package com.example.kanjimemorized.ui.screens.statistics

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.atan2

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    radius: Float = 300f,
    innerRadius: Float = 150f,
    transparentWidth: Float = 70f,
    inputMap: Map<String, Int>,
    colorMap: Map<String, Color>
) {
    Box(
        modifier = modifier
            .size(300.dp)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
            ) {
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
        topLeft = Offset(-radius, -radius),
        size = Size(width = radius*2, height = radius*2),
        style = Fill
    )
    currentStartAngle += inputMap["Mastered"]!!*(360f/2136f)
    drawArc(
        color = colorMap["Known"]!!,
        startAngle = currentStartAngle,
        sweepAngle = inputMap["Known"]!!*(360f/2136f),
        useCenter = true,
        topLeft = Offset(-radius, -radius),
        size = Size(width = radius*2, height = radius*2),
        style = Fill
    )
    currentStartAngle += inputMap["Known"]!!*(360f/2136f)
    drawArc(
        color = colorMap["Unknown"]!!,
        startAngle = currentStartAngle,
        sweepAngle = inputMap["Unknown"]!!*(360f/2136f),
        useCenter = true,
        topLeft = Offset(-radius, -radius),
        size = Size(width = radius*2, height = radius*2),
        style = Fill
    )
}

@Preview(showBackground = true)
@Composable
fun PieChartPreview() { PieChart(
    inputMap = mapOf("Unknown" to 1612, "Known" to 412, "Mastered" to 112),
    colorMap = mapOf("Unknown" to MaterialTheme.colorScheme.tertiary, "Known" to MaterialTheme.colorScheme.secondary, "Mastered" to MaterialTheme.colorScheme.primary)
)
}
