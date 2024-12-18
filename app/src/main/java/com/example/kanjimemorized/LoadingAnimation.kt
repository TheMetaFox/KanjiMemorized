package com.example.kanjimemorized

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Column {
            val infiniteTransition: InfiniteTransition = rememberInfiniteTransition(label = "loading_transition")
            val animatedColor: State<Color> = infiniteTransition.animateColor(
                initialValue = MaterialTheme.colorScheme.inversePrimary,
                targetValue = MaterialTheme.colorScheme.primary,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 1000),
                    repeatMode = RepeatMode.Reverse
                ),
                label = ""
            )
            val animatedStartAngle: State<Float> = infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 2000),
                    repeatMode = RepeatMode.Restart
                ),
                label = ""
            )
            val animatedSweepAngle: State<Float> = infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 1000),
                    repeatMode = RepeatMode.Reverse
                ),
                label = ""
            )
            Canvas(
                modifier = Modifier
                    .size(200.dp)
            ) {
                drawArc(
                    color = animatedColor.value,
                    startAngle = animatedStartAngle.value,
                    sweepAngle = animatedSweepAngle.value,
                    useCenter = false,
                    style = Stroke(
                        width = 10.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingAnimationPreview() {
    LoadingAnimation()
}