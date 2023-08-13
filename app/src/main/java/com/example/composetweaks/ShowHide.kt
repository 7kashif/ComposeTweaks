package com.example.composetweaks

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val eyeDelay: Long = 500

@Composable
fun ShowHide() {
    var show by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()

    var eyeOffSetX by remember {
        mutableStateOf(0f)
    }

    val animateCurve by animateFloatAsState(
        targetValue = if (show) 100f else -100f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        finishedListener = {
            if(it == 100f) {
                scope.launch {
                    eyeOffSetX = -120f
                    delay(eyeDelay)
                    eyeOffSetX = 120f
                    delay(eyeDelay)
                    eyeOffSetX = 0f
                }
            }
        }
    )
    val eyeLashesLength by animateFloatAsState(
        targetValue = if(show) 0f else 80f,
        animationSpec = tween(eyeDelay.toInt())
    )


    val eyeOffSet by animateFloatAsState(
        targetValue = eyeOffSetX,
        animationSpec = tween(eyeDelay.toInt())
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
            }

            val path2 = Path().apply {
                moveTo(hw - 200f, hh)
                cubicTo(
                    x3 = hw + 200f,
                    y3 = hh,
                    x1 = hw - 100f,
                    y1 = hh + 100f,
                    x2 = hw + 100f,
                    y2 = hh + 100f
                )
            }

            val eyeLashes = Path().apply {
                moveTo(hw - 60f, hh + 70f)
                relativeLineTo(dx = 0f, dy = eyeLashesLength)
                moveTo(hw, hh + 80f)
                relativeLineTo(dx = 0f, dy = eyeLashesLength)
                moveTo(hw + 60f, hh + 70f)
                relativeLineTo(dx = 0f, dy = eyeLashesLength)
            }


            drawCircle(
                color = Color.Black,
                center = Offset(hw + eyeOffSet,hh),
                radius = 40f,
                alpha = if(show) 1f else 0f,
            )

            drawPath(
                path = path,
                color = Color.Black,
                style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
            )
            drawPath(
                path = path2,
                color = Color.Black,
                style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round),
            )
            drawPath(
                path = eyeLashes,
                color = Color.Black,
                style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round),
            )
        }
    )

}