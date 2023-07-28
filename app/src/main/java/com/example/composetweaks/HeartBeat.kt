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
fun HeartBeat(
    amplitude: Float = 80f,
    frequency: Float = 0.015f
) {
    var widthInFloat by remember {
        mutableStateOf(0f)
    }
    var heightInFloat by remember {
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
                heightInFloat = size.height
            }
    ) {
        val wavePath = Path()
        wavePath.moveTo(0f, heightInFloat / 2)

        for (x in 0..xCord.toInt()) {
            val y =  (heightInFloat / 2 + amplitude * cos(2 * PI * x * frequency)).toFloat()
            wavePath.lineTo(x.toFloat(), y)
        }

        drawPath(wavePath, color = Blue, style = Stroke(6f))

        drawLine(
            color = Blue,
            start = Offset(0f, heightInFloat / 2),
            end = Offset(widthInFloat + xCord, heightInFloat / 2),
            strokeWidth = 4f
        )
    }
}