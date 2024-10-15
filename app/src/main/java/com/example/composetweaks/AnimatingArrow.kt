package com.example.composetweaks

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.StartOffsetType
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.isTraceInProgress
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
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

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Row {
            repeat(4) {
                Icon(
                    painter = painterResource(id = R.drawable.chevron_right),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(x = if (it == 0) 0.dp else (-18 * it).dp, y = 0.dp)
                        .alpha(arrowAlphas[it].value)
                )
            }
        }
    }

}