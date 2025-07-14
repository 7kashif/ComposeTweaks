package com.example.composetweaks

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun FlashingNewAnimation(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primary,
    paddingValues: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
    textColor: Color = Color.White,
    textStyle: TextStyle = MaterialTheme.typography.subtitle2
) {
    val infiniteTransition = rememberInfiniteTransition()

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        // 1) Pulsing Rounded Rectangle
        val rectScale by infiniteTransition.animateFloat(
            initialValue = 0.01f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 1900
                    1.5f at 400
                    1.00f at 800
                    1.00f at 1000
                    1.00f at 1200
                    1.00f at 1400
                    1.00f at 1600
                    0.05f at 1800
                    0.01f at 1900
                },
                repeatMode = RepeatMode.Restart
            )
        )

        val rectScaleN by infiniteTransition.animateFloat(
            initialValue = 0.01f,
            targetValue = 0.15f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 1900
                    1.5f at 400
                    1.00f at 800
                    1.00f at 1000
                    1.00f at 1200
                    1.00f at 1400
                    0.05f at 1600
                    0.01f at 1900
                },
                repeatMode = RepeatMode.Restart,
                initialStartOffset = StartOffset(offsetMillis = 200)
            )
        )

        val rectScaleE by infiniteTransition.animateFloat(
            initialValue = 0.01f,
            targetValue = 0.15f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 1900
                    1.5f at 400
                    1.00f at 800
                    1.00f at 1000
                    1.00f at 1200
                    1.00f at 1400
                    0.05f at 1600
                    0.01f at 1900
                },
                repeatMode = RepeatMode.Restart,
                initialStartOffset = StartOffset(offsetMillis = 400)
            )
        )

        val rectScaleW by infiniteTransition.animateFloat(
            initialValue = 0.01f,
            targetValue = 0.15f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 1900
                    1.5f at 400
                    1.00f at 800
                    1.00f at 1000
                    1.00f at 1200
                    1.00f at 1400
                    0.05f at 1600
                    0.01f at 1900
                },
                repeatMode = RepeatMode.Restart,
                initialStartOffset = StartOffset(offsetMillis = 600)
            )
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .matchParentSize()
                .graphicsLayer { scaleX = rectScale; scaleY = rectScale }
                .background(backgroundColor)
        )

        Row(
            modifier = Modifier
                .graphicsLayer { scaleX = rectScale; scaleY = rectScale }
                .padding(paddingValues = paddingValues)
        ) {
            Text(
                text = "N",
                color = textColor,
                modifier = Modifier.graphicsLayer {
                    scaleX = rectScaleN
                    scaleY = rectScaleN
                },
                style = textStyle
            )
            Text(
                text = "E",
                color = textColor,
                modifier = Modifier.graphicsLayer {
                    scaleX = rectScaleE
                    scaleY = rectScaleE
                },
                style = textStyle
            )
            Text(
                text = "W",
                color = textColor,
                modifier = Modifier.graphicsLayer {
                    scaleX = rectScaleW
                    scaleY = rectScaleW
                },
                style = textStyle
            )
        }
    }
}