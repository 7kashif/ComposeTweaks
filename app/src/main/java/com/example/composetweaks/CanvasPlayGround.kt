package com.example.composetweaks

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.graphics.flatten
import kotlinx.coroutines.delay

private const val DELAY = 1000

@Composable
fun CanvasPlayGround() {
    val height = LocalConfiguration.current.screenHeightDp.toFloat()
    val width = LocalConfiguration.current.screenWidthDp.toFloat()

    val path by remember {
        mutableStateOf(
            Path().apply {
                moveTo(0f, height / 2)

                // Draw lines and curves to form the shape
                lineTo(100f, height / 2)
                relativeLineTo(50f, -200f)
                relativeLineTo(50f, 400f)
                relativeLineTo(50f, -400f)
                relativeLineTo(50f, 400f)
                relativeLineTo(50f, -400f)
                relativeLineTo(50f, 400f)
                relativeLineTo(50f, -400f)
                relativeLineTo(50f, 400f)
                relativeLineTo(50f, -400f)
                relativeLineTo(50f, 400f)
                relativeLineTo(50f, -400f)
                relativeLineTo(50f, 400f)

            }
        )
    }

    val animatedPath = remember {
        mutableStateListOf<Path>()
    }

    var animate by remember {
        mutableStateOf(false)
    }

    val offSets = remember {
        mutableStateListOf(0f, 0f, 0f, 0f)
    }

    val startX by animateFloatAsState(
        targetValue = offSets[0],
        animationSpec = tween(durationMillis = DELAY, easing = LinearEasing)
    )
    val startY by animateFloatAsState(
        targetValue = offSets[1],
        animationSpec = tween(durationMillis = DELAY, easing = LinearEasing)
    )
    val endX by animateFloatAsState(
        targetValue = offSets[2],
        animationSpec = tween(durationMillis = DELAY, easing = LinearEasing)
    )
    val endY by animateFloatAsState(
        targetValue = offSets[3],
        animationSpec = tween(durationMillis = DELAY, easing = LinearEasing)
    )

    LaunchedEffect(key1 = Unit) {
        animate = true
        path.asAndroidPath().flatten().forEach { seg ->
            val a = seg.start.x
            val b = seg.start.y
            val c = seg.end.x
            val d = seg.end.y

            offSets[0] = a
            offSets[1] = b
            offSets[2] = c
            offSets[3] = d
            delay(DELAY.toLong())
        }
    }

    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            drawIntoCanvas {
                drawLine(
                    color = Color.Black,
                    strokeWidth = 8f,
                    cap = StrokeCap.Round,
                    start = Offset(startX, startY),
                    end = Offset(endX, endY)
                )
            }
        }
    )

}

