package com.example.composetweaks

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


private const val DELAY = 500

@Composable
fun DayNightToggle(
    modifier: Modifier = Modifier,
    height: Dp = 32.dp,
    width: Dp = 96.dp
) {
    val density = LocalDensity.current
    var isNight by remember {
        mutableStateOf(false)
    }

    val color by animateColorAsState(
        targetValue = if (isNight) dark else yellow,
        animationSpec = tween(durationMillis = DELAY, easing = LinearEasing),
        label = "color animation"
    )

    val radiusPx = remember {
        with(density) {
            (height - 2.dp).toPx() / 2
        }
    }

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

    val overlayCircleSize by animateFloatAsState(
        targetValue = if (isNight) radiusPx - 8 else 0f,
        animationSpec = tween(durationMillis = DELAY, easing = LinearEasing),
        label = "color animation"
    )

    val rotation by animateFloatAsState(
        targetValue = if (isNight) 360f else 0f,
        animationSpec = tween(durationMillis = DELAY, easing = LinearEasing),
        label = "color animation"
    )

    Canvas(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .size(width, height)
            .border(1.dp, color, RoundedCornerShape(24.dp))
            .background(yellow)
            .clickable {
                isNight = !isNight
            }
    ) {
        drawRect(
            color = color,
            size = Size(
                height = size.height,
                width = startOffSet
            )
        )
        rotate(pivot = Offset(startOffSet, size.height / 2), degrees = rotation) {
            drawCircle(
                color = Color.White,
                radius = radiusPx,
                center = Offset(startOffSet, size.height / 2)
            )
            drawCircle(
                color = color,
                radius = overlayCircleSize,
                center = Offset(startOffSet -12, (size.height / 2) - 8)
            )
        }
    }


}