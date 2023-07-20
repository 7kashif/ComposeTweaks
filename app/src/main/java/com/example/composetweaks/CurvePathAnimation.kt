package com.example.composetweaks

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

@Composable
fun CurvedPathAnimation() {
    val width = LocalConfiguration.current.screenWidthDp.dp
    val height = LocalConfiguration.current.screenHeightDp.dp

    var animate by remember {
        mutableStateOf(false)
    }

    val y by animateDpAsState(
        targetValue = if (animate) height else 1.dp,
        animationSpec = tween(durationMillis = 5000, easing = LinearEasing)
    )

    val x0 by remember {
        derivedStateOf {
            val t = (y.value / height.value)
            val s = t * width.value
            Log.e("test", s.toString())
            s.dp
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        animate = true
    })

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .offset(x0, y)
                .clip(CircleShape)
                .size(32.dp)
                .background(Color.DarkGray)
        )
    }

}

