package com.example.composetweaks

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.composetweaks.ui.theme.Green
import com.example.composetweaks.ui.theme.RED
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

private const val DELAY = 500

@Composable
fun CardSwipeStack(
    modifier: Modifier = Modifier
) {
    val itemsList = remember {
        mutableStateListOf(*Array(10) { "Item ${it + 1}" })
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        itemsList.forEachIndexed { index, item ->
            SwipeToMarkCard(
                onSwiped = {
                    itemsList.remove(item)
                },
                content = {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.h6
                    )
                }
            )
        }
    }
}

@Composable
fun SwipeToMarkCard(
    modifier: Modifier = Modifier,
    onSwiped: () -> Unit,
    content: @Composable () -> Unit
) {
    var cardOffSet by remember {
        mutableStateOf(DpOffset(0.dp, 0.dp))
    }

    var animateCardBackTo0 by remember {
        mutableStateOf(false)
    }

    var rotationDegree by remember {
        mutableStateOf(0f)
    }

    var overLayAlpha by remember {
        mutableStateOf(0f)
    }

    var overLayColor by remember {
        mutableStateOf(RED)
    }

    var swipeProgress by remember {
        mutableStateOf(0f)
    }

    val animatedSwipeProgress by animateFloatAsState(
        targetValue = swipeProgress,
        animationSpec = if (animateCardBackTo0) tween(DELAY) else tween(0),
        label = "animatedRedAlha"
    )

    val animatedOverLayAlpha by animateFloatAsState(
        targetValue = overLayAlpha,
        animationSpec = if (animateCardBackTo0) tween(DELAY) else tween(0),
        label = "animatedRedAlha"
    )

    val animatedOffsetX by animateDpAsState(
        targetValue = cardOffSet.x,
        animationSpec = if (animateCardBackTo0) tween(DELAY) else tween(0),
        label = "animateOffsetX"
    )

    val animatedOffsetY by animateDpAsState(
        targetValue = cardOffSet.y,
        animationSpec = if (animateCardBackTo0) tween(DELAY) else tween(0),
        label = "animateOffsetY"
    )

    val animatedRotationDegree by animateFloatAsState(
        targetValue = rotationDegree,
        animationSpec = if (animateCardBackTo0) tween(DELAY) else tween(0),
        label = "animatedRotationDegree"
    )

    val scope = rememberCoroutineScope()

    Card(
        modifier = modifier
            .offset(animatedOffsetX, animatedOffsetY)
            .rotate(animatedRotationDegree)
            .fillMaxSize()
            .pointerInput(key1 = Unit) {
                detectDragGestures(
                    onDragEnd = {
                        cardOffSet = DpOffset(0.dp, 0.dp)
                        rotationDegree = 0f
                        overLayAlpha = 0f
                        animateCardBackTo0 = true

                        Log.e("SwipeToMarkCard", "swipeProgress: $swipeProgress")

                        if (abs(swipeProgress) >= 1f)
                            onSwiped()

                        swipeProgress = 0f
                        scope.launch {
                            delay(DELAY.toLong())
                            animateCardBackTo0 = false
                        }
                    }
                ) { _, dragAmount ->
                    val offSetX = cardOffSet.x + dragAmount.x.toDp()
                    val offSetY = cardOffSet.y + dragAmount.y.toDp()

                    rotationDegree = lerp(0f, 10f, -(offSetX.value / 300))
                    swipeProgress = lerp(0f, 100f, offSetX.value / 30000)

                    val lerpAlpha = lerp(0f, 1f, offSetX.value / 300)

                    overLayColor = if (lerpAlpha > 0)
                        RED
                    else
                        Green

                    overLayAlpha = abs(lerpAlpha).coerceIn(0f, 1f)

                    cardOffSet = DpOffset(offSetX, offSetY)
                }
            },
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(overLayColor.copy(alpha = animatedOverLayAlpha))
            )

            Column(
                modifier = Modifier
                    .align(if (overLayColor == RED) Alignment.TopStart else Alignment.TopEnd)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp),
                    progress = animatedSwipeProgress,
                    strokeWidth = 8.dp,
                    color = Color.Black
                )
                Text(
                    text = if (overLayColor == RED) "Mark as Unread" else "Mark as Read",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.alpha(animatedOverLayAlpha),
                    color = Color.Black
                )
            }
        }
    }
}