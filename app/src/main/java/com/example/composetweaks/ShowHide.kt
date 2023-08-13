package com.example.composetweaks

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun ShowHide() {

    var show by remember {
        mutableStateOf(false)
    }

    val animateCurve by animateFloatAsState(
        targetValue = if (show) 80f else -80f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )


    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    show = !show
                }
            },
        onDraw = {
            val hh = size.height / 2
            val hw = size.width / 2
            val path = Path().apply {
                moveTo(hw - 200f, hh)
                cubicTo(
                    x3 = hw + 200f,
                    y3 = hh,
                    x1 = hw - 100f,
                    y1 = hh - animateCurve,
                    x2 = hw + 100f,
                    y2 = hh - animateCurve
                )
                if(show) {
                    cubicTo(
                        x3 = hw - 200f,
                        y3 = hh,
                        x1 = hw - 100f,
                        y1 = hh + 80f,
                        x2 = hw + 100f,
                        y2 = hh + 80f
                    )
                }
            }

            drawPath(
                path = path,
                color = Color.Black,
                style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    )

}