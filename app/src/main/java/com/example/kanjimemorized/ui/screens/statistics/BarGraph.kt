package com.example.kanjimemorized.ui.screens.statistics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PageSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BarGraph(
    modifier: Modifier = Modifier,
    width: Float = 300f,
    height: Float = 300f,
    barColor: Color = MaterialTheme.colorScheme.primary,
    barGraphSpan: BarGraphSpan = BarGraphSpan.WEEK1,
    inputMap: Map<Int, Int>,
    titleText: String = ""
) {
    val barCount: Int = when (barGraphSpan) {
        BarGraphSpan.WEEK1 -> { 7 }
        BarGraphSpan.MONTH1 -> { 30 }
        BarGraphSpan.MONTH3 -> { 90 }
        BarGraphSpan.YEAR1 -> { 365 }
        BarGraphSpan.ALL -> { inputMap.maxOf { entry -> entry.key } }
    }
    Box(
        modifier = modifier
            .size(width = width.dp, height = height.dp)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        val textMeasurer = rememberTextMeasurer()
        val textResult = textMeasurer.measure(text = "1")
        Canvas(
            modifier = Modifier
        ) {
            drawBars(valueMap = inputMap, barCount = barCount, barColor = barColor, width = width, height = height)
            drawAxis(width = width, height = height, strokeWidth = 5f)
            drawLabels(width = width, height = height, textMeasurer = textMeasurer, horizontalMax = barCount, verticalMax = inputMap.maxOf { entry -> entry.value })
        }
    }
}
fun DrawScope.drawBars(
    valueMap: Map<Int, Int>,
    barCount: Int,
    barColor: Color,
    width: Float,
    height: Float
) {
    val max: Float = valueMap.maxOf { entry -> entry.value }.toFloat()
    for (i in 1..barCount) {
        val value = valueMap.getOrDefault(key = i, defaultValue = 0)
        drawLine(
            color = barColor,
            start = Offset(x = -width + (width/barCount) + (width/barCount*(i-1)*2), y = height -(value/max)*height*2),
            end = Offset(x = -width + (width/barCount) + (width/barCount*(i-1)*2), y = height),
            strokeWidth = width/barCount*2 - (2 * barCount)
        )
    }
}

fun DrawScope.drawAxis(
    width: Float,
    height: Float,
    strokeWidth: Float
) {
    drawLine(
        color = Color.Gray,
        start = Offset(x = -width -strokeWidth/2, y = -height),
        end = Offset(x = -width -strokeWidth/2, y = height + strokeWidth/2),
        strokeWidth = strokeWidth
    )
    drawLine(
        color = Color.Gray,
        start = Offset(x = -width - strokeWidth/2, y = height),
        end = Offset(x = width, y = height),
        strokeWidth = strokeWidth
    )
}

fun DrawScope.drawLabels(
    width: Float,
    height: Float,
    textMeasurer: TextMeasurer,
    horizontalMax: Int,
    verticalMax: Int
) {
    for (i in 1..horizontalMax step horizontalMax/6+1) {
        val textResult = textMeasurer.measure(text = "$i")
        drawText(
            textLayoutResult = textResult,
            color = Color.LightGray,
            topLeft = Offset(x = -width - (textResult.size.width/2) + (width/horizontalMax) + (width/horizontalMax*(i-1)*2), y = height)
        )
    }
    for (i in 1..verticalMax step verticalMax/5+1) {
        val textResult = textMeasurer.measure(text = "$i")
        drawText(
            textLayoutResult = textResult,
            color = Color.LightGray,
            topLeft = Offset(x = -width - textResult.size.width-10, y = height - textResult.size.height/2 - (height/verticalMax*(i)*2))
        )
    }
}

@Preview
@Composable
fun BarGraphPreview() {
    BarGraph(inputMap = mapOf(1 to 2, 2 to 1, 3 to 3))
}