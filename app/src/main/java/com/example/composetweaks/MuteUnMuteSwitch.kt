package com.example.composetweaks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import com.example.composetweaks.ui.theme.Blue

@Composable
fun MuteUnMuteSwitch() {
    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            val hw = size.width / 2
            val hs = size.height / 2


            val path1 = Path().apply {
                moveTo(hw - 300f, hs - 40f)
                relativeLineTo(0f, 100f)
                relativeLineTo(140f, 0f)
                relativeLineTo(160f, 60f)
                relativeLineTo(0f, -220f)
                relativeLineTo(-160f, 60f)
                relativeLineTo(-140f, 0f)
            }
            val path2 = Path().apply {
                moveTo(hw + 60, hs - 170f)
                relativeCubicTo(
                    dx3 = 0f,
                    dy3 = 400f,
                    dx1 = 60f,
                    dy1 = 100f,
                    dx2 = 60f,
                    dy2 = 300f
                )
            }


            drawPath(
                path = path1,
                color = Blue,
                style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
            )
            drawPath(
                path = path2,
                color = Blue,
                style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
            )

        }
    )

}