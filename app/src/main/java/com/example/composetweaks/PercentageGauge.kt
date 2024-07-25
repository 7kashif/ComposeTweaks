package com.example.composetweaks

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.composetweaks.ui.theme.DarkGreen
import com.example.composetweaks.ui.theme.GREY
import com.example.composetweaks.ui.theme.Green
import com.example.composetweaks.ui.theme.Orange
import com.example.composetweaks.ui.theme.RED
import kotlinx.coroutines.delay

@Composable
fun PercentageGauge(
    modifier: Modifier = Modifier,
    percentage: Float = 84f
) {
    var slideValue by remember { mutableStateOf(0f) }
    var animateVisibility by remember { mutableStateOf(false) }

    val (status, innerArcColor) = remember(percentage) {
        when (percentage) {
            in 0f..50f -> "Warning" to RED
            in 50f..65f -> "Low" to Orange
            in 65f..80f -> "Good" to Green
            else -> "Great!" to DarkGreen
        }
    }

    //Outer arc segments
    val arcSegments = remember {
        mutableStateListOf(
            50f to RED,
            65f to Orange,
            80f to Green,
            100f to DarkGreen
        )
    }

    val animatedStatusAlpha by animateFloatAsState(
        targetValue = if (animateVisibility) 1f else 0f,
        animationSpec = tween(durationMillis = 1000), label = "animatedStatusAlpha"
    )

    LaunchedEffect(key1 = Unit) {
        for (i in 0..percentage.toInt()) {
            slideValue = i.toFloat()
            delay(10)
        }
        animateVisibility = true
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = modifier) {

            Text(
                text = "0",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = -(56).dp)
            )
            Text(
                text = "100",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 72.dp)
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = status,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.alpha(animatedStatusAlpha)
                )
                Text(
                    text = "${slideValue.toInt()}%",
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraBold)
                )
            }


            Canvas(modifier = Modifier.size(200.dp, 100.dp)) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                // Draw inner arc background
                drawArc(
                    color = GREY,
                    startAngle = 0f,
                    sweepAngle = -180f,
                    useCenter = false,
                    style = Stroke(width = 40.dp.toPx()),
                    size = Size(canvasWidth, canvasHeight * 2)
                )

                // Draw inner arc foreground
                drawArc(
                    color = innerArcColor,
                    startAngle = -180f,
                    sweepAngle = slideValue * 1.8f,
                    useCenter = false,
                    style = Stroke(width = 40.dp.toPx()),
                    size = Size(canvasWidth, canvasHeight * 2)
                )

                arcSegments.forEachIndexed { index, (start, color) ->
                    val previousSegment = if (index == 0) 0f else arcSegments[index - 1].first
                    drawArc(
                        color = color,
                        startAngle = -(180f - (previousSegment * 1.8f)),
                        sweepAngle = (start - previousSegment) * 1.8f,
                        useCenter = false,
                        style = Stroke(width = 8.dp.toPx()),
                        size = Size(canvasWidth + 170f, (canvasHeight * 2) + 180f),
                        topLeft = Offset(-85f, -90f)
                    )
                }

                rotate(
                    slideValue * 1.8f,
                    pivot = Offset(canvasWidth / 2, canvasHeight)
                ) {
                    drawCircle(
                        radius = 5.dp.toPx(),
                        center = Offset(35.dp.toPx(), canvasHeight),
                        color = Color.Black
                    )
                    drawPath(
                        path = Path().apply {
                            moveTo(-20.dp.toPx(), canvasHeight)
                            lineTo(33.dp.toPx(), canvasHeight - 5.dp.toPx())
                            lineTo(33.dp.toPx(), canvasHeight + 5.dp.toPx())
                        },
                        color = Color.Black
                    )
                }
            }
        }
    }


}
