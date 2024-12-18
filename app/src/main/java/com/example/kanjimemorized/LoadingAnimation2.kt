package com.example.kanjimemorized

import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingAnimation2(
    modifier: Modifier = Modifier.size(200.dp),
    color: Color = Color.LightGray,
    isAnimationPlaying: Boolean = false
) {
    Box(
        modifier = modifier
    ) {
        val endingAnimatedOuterRadius: State<Float> = animateFloatAsState(
            targetValue = if (!isAnimationPlaying) 0f else 1f,
            animationSpec = tween(
                durationMillis = 500,
                delayMillis = 0,
                easing = FastOutSlowInEasing
            ),
            label = "outer radius animation"
        )

        val startingAnimatedInnerRadius: State<Float> = animateFloatAsState(
            targetValue = if (isAnimationPlaying) 0f else 1f,
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = 0,
                easing = EaseOut
            ),
            label = "inner radius animation"
        )

        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (isAnimationPlaying) {
                val outerRadius = size.minDimension/2f * 1f
                val innerRadius = size.minDimension/2f * startingAnimatedInnerRadius.value
                val thickness = outerRadius - innerRadius
                if (thickness > 0.01f) {
                    drawCircle(
                        color = color,
                        radius = outerRadius - thickness/2,
                        style = Stroke(
                            width = thickness,
                            cap = StrokeCap.Round
                        )
                    )
                }
            } else {
                val outerRadius = size.minDimension/2f * endingAnimatedOuterRadius.value
                val innerRadius = size.minDimension/2f * 0f
                val thickness = outerRadius - innerRadius
                if (thickness > 0.01f) {
                    drawCircle(
                        color = color,
                        radius = outerRadius - thickness/2,
                        style = Stroke(
                            width = thickness,
                            cap = StrokeCap.Round
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingAnimationPreview2() {
    LoadingAnimation2()
}