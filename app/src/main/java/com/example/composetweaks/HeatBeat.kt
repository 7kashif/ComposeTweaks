package com.example.composetweaks

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import com.example.composetweaks.ui.theme.Blue
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos

@Composable
fun HeartBeat() {
    var waveOffset by remember { mutableStateOf(0f) }
    var widthInFloat by remember {
        mutableStateOf(0f)
    }
    var xCord by remember {
        mutableStateOf(0f)
    }


    LaunchedEffect(Unit) {
        delay(50)
        for (i in 0..widthInFloat.toInt()) {
            xCord = i.toFloat()
            delay(10)
        }
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                widthInFloat = size.width
            }
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val wavePath = Path()
        wavePath.moveTo(0f, canvasHeight / 2)

        val amplitude = 50f
        val frequency = 0.01f

        for (x in 0..canvasWidth.toInt()) {
            val y = canvasHeight / 2 + amplitude * cos(2 * PI * (x - waveOffset) * frequency)
            wavePath.lineTo(x.toFloat(), y.toFloat())
        }

        drawPath(wavePath, color = Blue, style = Stroke(6f))
    }
}