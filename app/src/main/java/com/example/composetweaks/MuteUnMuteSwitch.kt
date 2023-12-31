package com.example.composetweaks

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.composetweaks.ui.theme.Blue
import kotlinx.coroutines.delay

private const val DELAY = 1000

@Composable
fun MuteUnMuteSwitch() {
    var hh by remember {
        mutableStateOf(0f)
    }
    var hw by remember {
        mutableStateOf(0f)
    }

    var isMute by remember {
        mutableStateOf(false)
    }

    val animateLine by animateOffsetAsState(
        targetValue = if (isMute) Offset(hw + 80, hh + 130) else Offset(hw - 150f, hh - 110),
        animationSpec = tween(DELAY)
    )

    val animateMuteLineAlpha by animateFloatAsState(
        targetValue = if (isMute) 1f else 0f,
        animationSpec = tween(DELAY)
    )
    val animateSoundLinesAlpha by animateFloatAsState(
        targetValue = if (isMute) 0f else 1f,
        animationSpec = tween(DELAY)
    )
    val soundLinesOffSet by animateFloatAsState(
        targetValue = if (isMute) 0f else 40f,
        animationSpec = tween(DELAY)
    )

    val soundLines = remember {
        mutableStateListOf<Path>()
    }

    val pathsList = remember {
        mutableStateListOf<Path>()
    }

    LaunchedEffect(key1 = Unit, block = {
        delay(30)
        soundLines.addAll(
            listOf(
                Path().apply {
                    moveTo(hw + 20 + soundLinesOffSet, hh - 80f)
                    relativeCubicTo(
                        dx3 = 0f,
                        dy3 = 180f,
                        dx1 = 40f,
                        dy1 = 60f,
                        dx2 = 40f,
                        dy2 = 120f
                    )
                },
                Path().apply {
                    moveTo(hw + 60 + soundLinesOffSet, hh - 130f)
                    relativeCubicTo(
                        dx3 = 0f,
                        dy3 = 280f,
                        dx1 = 60f,
                        dy1 = 90f,
                        dx2 = 60f,
                        dy2 = 190f
                    )
                },
                Path().apply {
                    moveTo(hw + 100 + soundLinesOffSet, hh - 180f)
                    relativeCubicTo(
                        dx3 = 0f,
                        dy3 = 390f,
                        dx1 = 80f,
                        dy1 = 130f,
                        dx2 = 80f,
                        dy2 = 260f
                    )
                }
            )
        )
        pathsList.addAll(soundLines)
    })

    LaunchedEffect(key1 = isMute, block = {
        if(isMute.not()) {
            soundLines.forEach {
                pathsList.add(it)
                delay(180)
            }
        } else {
            delay(150)
            pathsList.removeAt(2)
            delay(150)
            pathsList.removeAt(1)
            delay(150)
            pathsList.removeAt(0)
        }
    })

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    isMute = !isMute
                }
            },
        onDraw = {
            hw = size.width / 2
            hh = size.height / 2


            val path1 = Path().apply {
                moveTo(hw - 150f, hh - 40f)
                relativeLineTo(0f, 100f)
                relativeLineTo(80f, 0f)
                relativeLineTo(80f, 60f)
                relativeLineTo(0f, -220f)
                relativeLineTo(-80f, 60f)
                relativeLineTo(-80f, 0f)
            }

            drawLine(
                color = Blue,
                strokeWidth = 8.dp.toPx(),
                cap = StrokeCap.Round,
                start = Offset(hw - 150f, hh - 110),
                end = animateLine,
                alpha = animateMuteLineAlpha
            )

            drawPath(
                path = path1,
                color = Blue,
                style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
            )

            pathsList.forEach {
                drawPath(
                    path = it,
                    color = Blue,
                    style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round),
                    alpha = animateSoundLinesAlpha
                )
            }
        }
    )

}