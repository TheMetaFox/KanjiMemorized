package com.example.kanjimemorized.ui.screens.statistics

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kanjimemorized.ui.theme.White
import kotlin.math.PI
import kotlin.math.atan2

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    radius: Float = 300f,
    innerRadius: Float = 150f,
    transparentWidth: Float = 70f,
    input: List<PieChartInput>,
    centerText: String = "2136 Joyo Kanji"
) {
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }
    var inputList by remember {
        mutableStateOf(input)
    }
    var isCenterSelected by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .pointerInput(true){
                    detectTapGestures(
                        onTap = { offset ->  
                            val tapAngleInDegrees = (-atan2(
                                x = circleCenter.y - offset.y,
                                y = circleCenter.x - offset.x
                            ) * (180f / PI).toFloat() - 90f).mod(360f)
                            val centerClicked = if(tapAngleInDegrees<90) {
                                offset.x<circleCenter.x+innerRadius && offset.y<circleCenter.y+innerRadius
                            } else if(tapAngleInDegrees<180) {
                                offset.x>circleCenter.x-innerRadius && offset.y<circleCenter.y+innerRadius
                            } else if(tapAngleInDegrees<270) {
                                offset.x>circleCenter.x-innerRadius && offset.y>circleCenter.y-innerRadius
                            } else {
                                offset.x<circleCenter.x+innerRadius && offset.y>circleCenter.y-innerRadius
                            }

                            if(centerClicked) {
                                Log.i("PieChart.kt", "Center was clicked...")
                                inputList = inputList.map {
                                    it.copy(isSelected = !isCenterSelected)
                                }
                                isCenterSelected = !isCenterSelected
                            }
                        }
                    )
                },

            ) {
            val width = size.width
            val height = size.height
            circleCenter = Offset(x = width / 2f, y = height / 2f)

            val totalValue = 2136
            val anglePerValue = 360f / totalValue
            var currentStartAngle = 0f

            input.forEach { pieChartInput ->
                val scale = if (pieChartInput.isSelected) 1.2f else 1f
                val sweepAngle = pieChartInput.value * anglePerValue
                scale(scale) {
                    drawArc(
                        color = pieChartInput.color,
                        startAngle = currentStartAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true,
                        size = Size(width = radius * 2f, height = radius * 2f),
                        topLeft = Offset(width / 2f - radius, height / 2f - radius)
                    )
                    currentStartAngle += sweepAngle
                }
                var rotationAngle = currentStartAngle - (sweepAngle/2f) - 90
                var factor = 1f
                if (rotationAngle > 90f) {
                    rotationAngle += 180f.mod(360f)
                    factor = -0.92f
                }
                val percentage = (pieChartInput.value/totalValue.toFloat()*100).toInt()

                drawContext.canvas.nativeCanvas.apply {
                    if (percentage > 3) {
                        rotate(rotationAngle) {
                            drawText(
                                "$percentage%",
                                circleCenter.x,
                                circleCenter.y + radius - (radius - innerRadius)/2f * factor,
                                Paint().apply {
                                    textSize = 12.sp.toPx()
                                    textAlign = Paint.Align.CENTER
                                    color = White.toArgb()
                                }
                            )
                        }
                    }
                }
                if (pieChartInput.isSelected) {
                    Log.i("PieChart.kt", "Pie Chart Input Selected...")
                    val tabRotation = currentStartAngle - sweepAngle - 90f
                    rotate(tabRotation) {
                        drawRoundRect(
                            topLeft = circleCenter,
                            size = Size(12f, radius*1.2f),
                            color = Color.Gray,
                            cornerRadius = CornerRadius(15f)
                        )
                    }
                    rotate(tabRotation+sweepAngle) {
                        drawRoundRect(
                            topLeft = circleCenter,
                            size = Size(12f, radius*1.2f),
                            color = Color.Gray,
                            cornerRadius = CornerRadius(15f)
                        )
                    }
                    drawContext.canvas.nativeCanvas.apply {
                        if (percentage > 3) {
                            rotate(rotationAngle) {
                                drawText(
                                    "$percentage%",
                                    circleCenter.x,
                                    circleCenter.y + radius * 1.3f * factor,
                                    Paint().apply {
                                        textSize = 22.sp.toPx()
                                        textAlign = Paint.Align.CENTER
                                        color = White.toArgb()
                                        isFakeBoldText = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
            drawContext.canvas.nativeCanvas.apply {
                drawCircle(
                    circleCenter.x,
                    circleCenter.y,
                    innerRadius,
                    Paint().apply {
                        color = Color.White.copy(alpha = 0.6f).toArgb()
                        setShadowLayer(10f, 0f, 0f, Color.Gray.toArgb())
                    }
                )
            }
            drawCircle(
                color = White.copy(0.2f),
                radius = innerRadius + (transparentWidth/2)
            )
        }
        Text(
            text = centerText,
            modifier = Modifier
                .width(width = Dp(innerRadius/1.5f))
                .padding(all = 25.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}
