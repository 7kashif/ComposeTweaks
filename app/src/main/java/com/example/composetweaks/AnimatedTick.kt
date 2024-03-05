package com.example.composetweaks

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import kotlinx.coroutines.delay

@Composable
fun AnimatedTick() {
    var start by remember {
        mutableStateOf(false)
    }
    var tickStart by remember {
        mutableStateOf(false)
    }

    val circleOne by animateFloatAsState(
        targetValue = if (start) 80f else 0f,
        label = "circle1",
        animationSpec = tween(1000),
        finishedListener = {
            tickStart = true
        }
    )
    val circleTwo by animateFloatAsState(
        targetValue = if (start) 120f else 0f,
        label = "circle1",
        animationSpec = tween(1000)
    )

    val tick1 by animateOffsetAsState(
        targetValue = if (tickStart) Offset(60f, -30f) else Offset(0f, 0f),
        label = "circle1",
        animationSpec = spring()
    )
    val tick2 by animateOffsetAsState(
        targetValue = if (tickStart) Offset(20f, 30f) else Offset(0f, 0f),
        label = "circle1",
        animationSpec = spring()
    )

    LaunchedEffect(key1 = Unit) {
        delay(300)
        start = true
    }

    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            drawCircle(
                color = Color.Gray,
                radius = circleTwo,
                center = center
            )
            drawCircle(
                color = Color.DarkGray,
                radius = circleOne,
                center = center
            )
            if (tickStart) {
                drawLine(
                    color = Color.Cyan,
                    start = center - Offset(20f, -20f),
                    end = center - Offset(20f, 20f) + tick1,
                    strokeWidth = 12f,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = Color.Cyan,
                    start = center - Offset(20f, -20f),
                    end = center - Offset(20f, -20f) - tick2,
                    strokeWidth = 12f,
                    cap = StrokeCap.Round
                )
            }
        }
    )

}