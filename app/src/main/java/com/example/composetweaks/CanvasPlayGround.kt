package com.example.composetweaks

import android.util.Log
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.graphics.flatten
import kotlinx.coroutines.delay

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

    LaunchedEffect(key1 = Unit) {
        path.asAndroidPath().flatten().forEach { seg ->
            val startX = seg.start.x
            val endX = seg.end.x
            val startY = seg.start.y
            val endY = seg.end.y

            val p = Path().apply {
                moveTo(seg.start.x, seg.start.y)
                lineTo(seg.end.x, seg.end.y)
            }
            animatedPath.add(p)
            delay(60)
        }
    }

    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            animatedPath.forEach { path ->
                drawPath(
                    path = path,
                    color = Color.Black,
                    style = Stroke(
                        width = 12f,
                        cap = StrokeCap.Round
                    )
                )
            }
        }
    )

}

/**
 * Function to get a partial path based on the current animated value.
 */
fun getPartialPath(path: Path, animatedValue: Float): Path {
    // The 'animatedValue' ranges from 0f to 1f representing the progress of animation.
    // You need to calculate the length of each segment and determine which segment to draw
    // based on the current animated value.

    val partialPath = Path()
    path.asAndroidPath().flatten().forEachIndexed { index,seg ->
        seg.endFraction
    }

    // Add the segments you want to draw based on the animatedValue.

    return partialPath
}