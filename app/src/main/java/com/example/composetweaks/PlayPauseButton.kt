package com.example.composetweaks

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.composetweaks.ui.theme.Blue

private const val DELAY = 1000

@Composable
fun PlayPauseButton() {
    var hw by remember {
        mutableStateOf(0f)
    }
    var hh by remember {
        mutableStateOf(0f)
    }

    var isPaused by remember {
        mutableStateOf(true)
    }

    val playAlpha by animateFloatAsState(
        targetValue = if (isPaused) 0f else 1f,
        animationSpec = tween(DELAY)
    )

    val playRotation by animateFloatAsState(
        targetValue = if (isPaused) 30f else 0f,
        animationSpec = tween(DELAY)
    )

    val pauseAlpha by animateFloatAsState(
        targetValue = if (isPaused) 1f else 0f,
        animationSpec = tween(DELAY)
    )

    val pauseRotation by animateFloatAsState(
        targetValue = if (isPaused) 0f else 40f,
        animationSpec = tween(DELAY)
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    isPaused = !isPaused
                }
            },
        onDraw = {
            hw = size.width / 2
            hh = size.height / 2

            val path = Path().apply {
                moveTo(hw - 50f, hh - 70f)
                relativeLineTo(40.dp.toPx(), 25.dp.toPx())
                relativeLineTo(-40.dp.toPx(), 25.dp.toPx())
                close()
            }

            drawCircle(
                center = Offset(hw, hh),
                radius = 220f,
                style = Stroke(width = 8.dp.toPx()),
                color = Color.Black
            )



            drawRoundRect(
                color = Color.Black,
                topLeft = Offset((hw - 100f) - pauseRotation, (hh - 120f) - pauseRotation),
                cornerRadius = CornerRadius(12f.dp.toPx(), 12f.dp.toPx()),
                size = Size(30.dp.toPx(), 90f.dp.toPx()),
                alpha = pauseAlpha
            )

            rotate(
                degrees = 30f
            ) {
                drawPath(
                    path = path,
                    color = Color.Black,
                    style = Stroke(width = 40.dp.toPx(), join = StrokeJoin.Round),
                    alpha = playAlpha
                )
            }

            drawRoundRect(
                color = Color.Black,
                topLeft = Offset((hw + 10) - pauseRotation, (hh - 120f) - pauseRotation),
                cornerRadius = CornerRadius(12f.dp.toPx(), 12f.dp.toPx()),
                size = Size(30.dp.toPx(), 90f.dp.toPx()),
                alpha = pauseAlpha
            )
        }
    )
}

