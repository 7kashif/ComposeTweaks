package com.example.composetweaks

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
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

@Composable
fun ConfettiAnimation(
    particleCount: Int = 100
) {

    val colors = remember {
        List(particleCount) {
            listOfColor.shuffled()[0]
        }
    }

    val randomsForSize = remember {
        List(particleCount) {
            Size(
                Random.nextInt(-20, 20).toFloat(),
                Random.nextInt(-20, 20).toFloat()
            )
        }
    }

    val randomXsForLine = remember {
        List(particleCount) {
            Random.nextInt(-30, 30)
        }
    }

    val randomYsForLine = remember {
        List(particleCount) {
            Random.nextInt(-30, 30)
        }
    }

    val animation = rememberInfiniteTransition()



    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        propagateMinConstraints = false
    ) {
        val tY = List(particleCount) {
            randomTransitions(
                animation = animation,
                init = constraints.maxHeight.toFloat(),
                from = constraints.maxHeight * 0.1f,
                to = constraints.maxHeight * 0.8f
            )
        }

        val tX = List(particleCount) {
            randomTransitions(
                animation = animation,
                init = if (it % 2 == 0) constraints.maxWidth * 0.3f else constraints.maxWidth * 0.7f,
                from = if (it % 2 == 0) constraints.maxWidth * 0.1f else constraints.maxWidth * 0.4f,
                to = if (it % 2 == 0) constraints.maxWidth * 0.6f else constraints.maxWidth * 0.9f
            )
        }

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {

            repeat(particleCount) {
                drawRect(
                    color = colors[it],
                    size = randomsForSize[it],
                    topLeft = Offset(tX[it].value, tY[it].value)
                )

                drawLine(
                    color = colors[it],
                    start = Offset(tX[it].value, tY[it].value),
                    end = Offset(
                        tX[it].value + randomXsForLine[it],
                        tY[it].value + randomYsForLine[it]
                    ),
                    strokeWidth = 2f
                )

            }
        }
    }
}

@Composable
fun randomTransitions(animation: InfiniteTransition, init: Float, from: Float, to: Float) =
    animation.animateFloat(
        initialValue = init,
        targetValue = Random.nextInt(from.toInt(), to.toInt()).toFloat(),
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(1400),
            repeatMode = RepeatMode.Restart
        )
    )
