package com.example.composetweaks

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.composetweaks.ui.theme.Green
import kotlinx.coroutines.delay


@Composable
fun ZoomingDotsAnimation(
    numberOfDots: Int = 4
) {
    val delay by remember {
        mutableLongStateOf(300L)
    }

    val starts = remember {
        mutableStateListOf(*Array(numberOfDots) { false })
    }

    val dpSizes = List(numberOfDots) {index ->
        animateDpAsState(
            targetValue = if (starts[index]) 2.dp else 20.dp,
            animationSpec = tween(durationMillis = delay.toInt(), easing = LinearEasing),
            label = "",
            finishedListener = {
                starts[index] = false
            }
        )
    }

    LaunchedEffect(Unit) {
        delay(1000)
        while (true) {
            repeat(numberOfDots) {
                starts[it] = true
                delay(delay)
            }
            delay(delay)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(numberOfDots) {
                Box(
                    modifier = Modifier.size(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(dpSizes[it].value)
                            .background(Green)
                    )
                }
            }
        }
    }

}
