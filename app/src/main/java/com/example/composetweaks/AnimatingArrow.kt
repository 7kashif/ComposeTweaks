package com.example.composetweaks

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun AnimatingArrow() {
    val delay by remember {
        mutableLongStateOf(300L)
    }

    val starts = remember {
        mutableStateListOf(*Array(4) { false })
    }

    val arrowAlphas = List(4) { index ->
        animateFloatAsState(
            targetValue = if (starts[index]) 1f else 0f,
            animationSpec = tween(
                durationMillis = if (starts[index]) 0 else delay.toInt(),
                easing = LinearEasing
            ),
            label = "",
            finishedListener = {
                starts[index] = false
            }
        )
    }

    LaunchedEffect(Unit) {
        delay(1000)
        while (true) {
            repeat(4) {
                starts[it] = true
                delay(100)
            }
            delay(delay)
        }
    }

    val density= LocalDensity.current

    val strokePx = remember {
        with(density) {
            6.dp.toPx()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Row {
            repeat(4) {
                Box(
                    modifier = Modifier
                        .offset(x = if (it == 0) 0.dp else (-3 * it).dp, y = 0.dp)
                        .clip(ChevronShape(strokePx))
                        .size(12.dp, 16.dp)
                        .alpha(arrowAlphas[it].value)
                        .background(Color(0xFFb56f02))
                )
            }
        }
    }
}

class ChevronShape(
    private val strokeWidth: Float
): Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline = Outline.Generic(path = Path().apply {
        moveTo(0f,0f)
        lineTo(size.width - strokeWidth, size.height / 2)
        lineTo(0f, size.height)
        lineTo(0f + strokeWidth, size.height)
        lineTo(size.width, size.height / 2)
        lineTo(strokeWidth, 0f)
        close()
    })

}