package com.example.composetweaks

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.delay

//sky blue color
private val skyBlue = Color(0xFF87CEEB)

//night dark color
private val nightDark = Color(0xFF262626)
private val nightDark2 = Color(0xFF333333)
private val white2 = Color(0xFFE7E7E7)

private const val DELAY = 500


@Composable
fun CloudyNightToggle(
    height: Dp = 64.dp,
    width: Dp = 156.dp
) {
    val density = LocalDensity.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        context as MainActivity
        WindowCompat.getInsetsController(context.window, context.window.decorView).hide(WindowInsetsCompat.Type.statusBars())
    }

    var isNight by remember {
        mutableStateOf(false)
    }
    val radiusPx = remember {
        with(density) {
            (height - 4.dp).toPx() / 2
        }
    }

    val circleColor by animateColorAsState(
        targetValue = if (isNight) Color.White else yellow,
        animationSpec = tween(durationMillis = DELAY, easing = LinearEasing),
        label = "color animation"
    )

    val cloudsColor by animateColorAsState(
        targetValue = if (isNight) nightDark else Color.White,
        animationSpec = tween(durationMillis = DELAY, easing = LinearEasing),
        label = "color animation"
    )
    val cloudsColor2 by animateColorAsState(
        targetValue = if (isNight) nightDark2 else white2,
        animationSpec = tween(durationMillis = DELAY, easing = LinearEasing),
        label = "color animation"
    )

    val bgColor by animateColorAsState(
        targetValue = if (isNight) nightDark.copy(alpha = 0.5f) else skyBlue,
        animationSpec = tween(durationMillis = DELAY, easing = LinearEasing),
        label = "color animation"
    )


    val cloudsRotation by animateFloatAsState(
        targetValue = if (isNight) 0f else -10f,
        animationSpec = tween(durationMillis = DELAY, easing = LinearEasing),
        label = "color animation"
    )

    val widthPx = remember {
        with(density) {
            width.toPx()
        }
    }
    val startOffSet by animateFloatAsState(
        targetValue = if (isNight) widthPx - radiusPx else radiusPx,
        animationSpec = tween(durationMillis = DELAY, easing = LinearEasing),
        label = "color animation"
    )

    Box(
        modifier = Modifier.fillMaxSize().consumeWindowInsets(WindowInsets.statusBars),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .clip(RoundedCornerShape(height / 2))
                .size(width, height)
                .background(bgColor)
                .border(2.dp, Color.Black, RoundedCornerShape(height / 2))
                .clickable {
                    isNight = !isNight
                }
        ) {
            val canvasHeight = size.height
            val canvasWidth = size.width
            rotate(
                pivot = Offset(canvasWidth / 2, canvasHeight),
                degrees = cloudsRotation
            ) {
                drawCircle(
                    color = cloudsColor2,
                    radius = 28.dp.toPx(),
                    center = Offset(16.dp.toPx(), canvasHeight - 34)
                )
                drawCircle(
                    color = cloudsColor2,
                    radius = 28.dp.toPx(),
                    center = Offset(40.dp.toPx(), canvasHeight - 22f)
                )
                drawCircle(
                    color = cloudsColor2,
                    radius = 26.dp.toPx(),
                    center = Offset(64.dp.toPx(), canvasHeight - 42f)
                )
                drawCircle(
                    color = cloudsColor2,
                    radius = 28.dp.toPx(),
                    center = Offset(96.dp.toPx(), canvasHeight - 32f)
                )
                drawCircle(
                    color = cloudsColor2,
                    radius = 28.dp.toPx(),
                    center = Offset(136.dp.toPx(), canvasHeight - 32f)
                )
            }
            drawCircle(
                color = circleColor,
                radius = radiusPx,
                center = Offset(startOffSet, canvasHeight / 2)
            )
            rotate(
                pivot = Offset(canvasWidth / 2, canvasHeight),
                degrees = cloudsRotation
            ) {
                drawCircle(
                    color = cloudsColor,
                    radius = 28.dp.toPx(),
                    center = Offset(16.dp.toPx(), canvasHeight)
                )
                drawCircle(
                    color = cloudsColor,
                    radius = 28.dp.toPx(),
                    center = Offset(40.dp.toPx(), canvasHeight + 22f)
                )
                drawCircle(
                    color = cloudsColor,
                    radius = 26.dp.toPx(),
                    center = Offset(64.dp.toPx(), canvasHeight + 22f)
                )
                drawCircle(
                    color = cloudsColor,
                    radius = 28.dp.toPx(),
                    center = Offset(96.dp.toPx(), canvasHeight + 22f)
                )
                drawCircle(
                    color = cloudsColor,
                    radius = 28.dp.toPx(),
                    center = Offset(136.dp.toPx(), canvasHeight + 22f)
                )
            }
        }
    }
}
