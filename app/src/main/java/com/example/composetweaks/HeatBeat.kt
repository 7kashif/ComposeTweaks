package com.example.composetweaks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.composetweaks.ui.theme.Blue
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos

@Composable
fun HeartBeat() {
    var widthInFloat by remember {
        mutableStateOf(0f)
    }
    var xCord by remember {
        mutableStateOf(0f)
    }

    var start by remember {
        mutableStateOf(0f)
    }

    var startMovingBack by remember {
        mutableStateOf(false)
    }


    LaunchedEffect(Unit) {
        delay(50)
        for (i in 0..Int.MAX_VALUE) {
            xCord = i.toFloat()
            delay(10)
            if (i == widthInFloat.toInt() / 2)
                startMovingBack = true
        }
    }

    LaunchedEffect(key1 = startMovingBack) {
        while (startMovingBack) {
            start -= 2f
            delay(60)
        }
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .offset(x = start.dp)
            .graphicsLayer {
                widthInFloat = size.width
            }
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val wavePath = Path()
        wavePath.moveTo(0f, canvasHeight / 2)


        val amplitude = 80f
        val frequency = 0.015f

        for (x in 0..xCord.toInt()) {
            val y = canvasHeight / 2 + amplitude * cos(2 * PI * x * frequency)
            wavePath.lineTo(x.toFloat(), y.toFloat())
        }

        drawPath(wavePath, color = Blue, style = Stroke(6f))

        drawLine(
            color = Blue,
            start = Offset(0f, canvasHeight / 2),
            end = Offset(canvasWidth + xCord, canvasHeight / 2),
            strokeWidth = 4f
        )
    }
}