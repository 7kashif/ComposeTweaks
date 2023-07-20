package com.example.composetweaks

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun CurvedPathAnimation() {
    val width = LocalConfiguration.current.screenWidthDp.dp - 32.dp
    val height = LocalConfiguration.current.screenHeightDp.dp - 32.dp

    var animate by remember {
        mutableStateOf(false)
    }

    val frac by animateFloatAsState(
        targetValue = if (animate) 1f else 0f,
        animationSpec = tween(durationMillis = 5000)
    )
    val delta by animateFloatAsState(
        targetValue = if (animate) 0.6f else 0f,
        animationSpec = tween(durationMillis = 5000)
    )

    var y by remember {
        mutableStateOf(0f)
    }
    var x by remember {
        mutableStateOf(0f)
    }

    LaunchedEffect(key1 = frac, block = {
        // Horizontal movement from left to right (0% to 50% progress)
        x = (frac - delta) * width.value

        // Vertical movement from top to bottom (50% to 100% progress)
        y = frac * height.value
    })

    LaunchedEffect(key1 = Unit, block = {
        animate = true
    })

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .offset(x.dp, y.dp)
                .clip(CircleShape)
                .size(32.dp)
                .background(Color.DarkGray)
        )
    }

}

