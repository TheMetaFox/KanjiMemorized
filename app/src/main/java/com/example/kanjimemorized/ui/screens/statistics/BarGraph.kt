package com.example.kanjimemorized.ui.screens.statistics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BarGraph(
    modifier: Modifier = Modifier.size(size = 300.dp),
    barColor: Color = MaterialTheme.colorScheme.primary,
    axisColor: Color = MaterialTheme.colorScheme.onBackground,
    labelColor: Color = MaterialTheme.colorScheme.onBackground,
    barGraphSpan: BarGraphSpan = BarGraphSpan.WEEK1,
    inputMap: Map<Int, Int>,
) {
    val barCount: Int = when (barGraphSpan) {
        BarGraphSpan.WEEK1 -> { 7 }
        BarGraphSpan.MONTH1 -> { 30 }
        BarGraphSpan.MONTH3 -> { 90 }
        BarGraphSpan.YEAR1 -> { 365 }
        BarGraphSpan.ALL -> { inputMap.maxOf { entry -> entry.key } }
    }
    val maxValue: Int = inputMap.maxOf { entry -> entry.value }
    Box(
        modifier = modifier.padding(top = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        val textMeasurer = rememberTextMeasurer()
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val width: Float = size.width
            val height: Float = size.height
            val graphWidth: Float = width-(width/17f)
            val graphHeight: Float = height-(height/15f)
            drawBars(valueMap = inputMap, barCount = barCount, barColor = barColor, width = width, graphWidth = graphWidth, graphHeight = graphHeight)
            drawAxis(width = width, graphWidth = graphWidth, graphHeight = graphHeight, strokeWidth = 5f, axisColor = axisColor)
            drawLabels(width = width, height = height, graphWidth = graphWidth, textMeasurer = textMeasurer, horizontalMax = barCount, verticalMax = maxValue, labelColor = labelColor)
        }
    }
}
fun DrawScope.drawBars(
    valueMap: Map<Int, Int>,
    barCount: Int,
    barColor: Color,
    width: Float,
    graphWidth: Float,
    graphHeight: Float
) {
    val max: Float = valueMap.maxOf { entry -> entry.value }.toFloat()
    for (i in 1..barCount) {
        val value = valueMap.getOrDefault(key = i, defaultValue = 0)
        drawLine(
            color = barColor,
            start = Offset(x = (width-graphWidth) + graphWidth/barCount/2f + graphWidth/barCount*(i-1), y = graphHeight),
            end = Offset(x = (width-graphWidth) + graphWidth/barCount/2f+graphWidth/barCount*(i-1), y = graphHeight-(value/max)*graphHeight),
            strokeWidth = graphWidth/barCount - (2f / barCount)
        )
    }
}

fun DrawScope.drawAxis(
    width: Float,
    graphWidth: Float,
    graphHeight: Float,
    strokeWidth: Float,
    axisColor: Color,
) {
    drawLine(
        color = axisColor,
        start = Offset(x = width-graphWidth - strokeWidth/2f, y = 0f),
        end = Offset(x = width-graphWidth - strokeWidth/2f, y = graphHeight + strokeWidth),
        strokeWidth = strokeWidth
    )
    drawLine(
        color = axisColor,
        start = Offset(x = width-graphWidth - strokeWidth, y = graphHeight + strokeWidth/2),
        end = Offset(x = width, y = graphHeight + strokeWidth/2),
        strokeWidth = strokeWidth
    )
}

fun DrawScope.drawLabels(
    width: Float,
    height: Float,
    graphWidth: Float,
    textMeasurer: TextMeasurer,
    horizontalMax: Int,
    verticalMax: Int,
    labelColor: Color
    ) {
    for (i in 1..horizontalMax step horizontalMax/6+1) {
        val textResult = textMeasurer.measure(text = "$i")
        drawText(
            textLayoutResult = textResult,
            color = labelColor,
            topLeft = Offset(x = (width-graphWidth) + (graphWidth/horizontalMax/2f) - (textResult.size.width/2) + (graphWidth/horizontalMax*(i-1)), y = height - textResult.size.height)
        )
    }
    for (i in 1..verticalMax step verticalMax/5+1) {
        val textResult = textMeasurer.measure(text = "$i")
        drawText(
            textLayoutResult = textResult,
            color = labelColor,
            topLeft = Offset(x = ((width-graphWidth)/2)-textResult.size.width.toFloat(), y = height - textResult.size.height/2 - (height/verticalMax*(i)))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BarGraphPreview1() {
    BarGraph(inputMap = mapOf(1 to 2, 2 to 1, 3 to 3), barGraphSpan = BarGraphSpan.WEEK1)
}
@Preview(showBackground = true)
@Composable
fun BarGraphPreview2() {
    BarGraph(inputMap = mapOf(1 to 2, 2 to 1, 3 to 3), barGraphSpan = BarGraphSpan.MONTH1)
}