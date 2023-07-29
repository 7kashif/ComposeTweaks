package com.example.composetweaks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.delay

@Composable
fun FollowPath() {

    val pathOffSets = remember {
        mutableStateListOf<Offset>().apply {
            add(Offset(0f,0f))
        }
    }

    var beginAnimation by remember {
        mutableStateOf(false)
    }

    var currentOffSet by remember {
        mutableStateOf(Offset(0f,0f))
    }

    LaunchedEffect(key1 = beginAnimation, block = {
        delay(20)
        if(beginAnimation) {
            pathOffSets.forEach {
                currentOffSet = it
                delay(25)
            }
        }
    })

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                       beginAnimation = true
                    },
                    onDragStart = {
                        beginAnimation = false
                        pathOffSets.clear()
                    }
                ) { change, _ ->
                    pathOffSets.add(change.position)
                }
            },
        onDraw = {
            drawCircle(
                color = Color.Black,
                radius = 20f,
                center = currentOffSet
            )
            drawPoints(
                points = pathOffSets,
                color = Color.Black,
                pointMode = PointMode.Polygon,
                strokeWidth = 2f
            )
        }
    )
}