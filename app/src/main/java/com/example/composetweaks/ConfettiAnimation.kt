package com.example.composetweaks.ui.theme

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import kotlin.random.Random

private val listOfColor = listOf(
    Color.Black,
    Color.Blue,
    Color.Yellow,
    Color.Red,
    Color.Green,
    Color.Cyan,
    Color.Magenta
)

private const val particleCount = 100

@Composable
fun ConfettiAnimation() {
    val colors = remember {
        List(particleCount) {
            listOfColor.shuffled()[0]
        }
    }

    val animation = rememberInfiniteTransition()

    val tY = List(particleCount) {
        randomTransitions(animation = animation)
    }

    val tX = List(particleCount) {
        randomTransitions(animation = animation)
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        repeat(particleCount) {
            drawRect(
                color = colors[it],
                size = Size(20f, 20f),
                topLeft = Offset(
                    tX[it].value,
                    tY[it].value
                ),
            )
        }
    }
}

@Composable
fun randomTransitions(animation: InfiniteTransition) = animation.animateFloat(
    initialValue = 0f,
    targetValue = Random.nextInt(0, 500).toFloat(),
    animationSpec = InfiniteRepeatableSpec(
        animation = tween(1400),
        repeatMode = RepeatMode.Reverse
    )
)
