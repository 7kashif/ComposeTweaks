package com.example.composetweaks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
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

@Composable
fun PercentageGauge(
    modifier: Modifier = Modifier
) {
    val (slideValue, updateSliderValue) = remember {
        mutableStateOf(0f)
    }

    var status by remember {
        mutableStateOf("Warning")
    }

    val innerArcColor by remember(slideValue) {
        mutableStateOf(
            when (slideValue) {
                in 0f..50f -> {
                    status = "Warning"
                    RED
                }

                in 50f..65f -> {
                    status = "Low"
                    Orange
                }

                in 65f..80f -> {
                    status = "Good"
                    Green
                }

                else -> {
                    status = "Great!"
                    DarkGreen
                }
            }
        )
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Slider(
            value = slideValue,
            onValueChange = updateSliderValue,
            valueRange = 0f..100f,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(32.dp),
        )

        Box {

            Text(
                text = "0",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.BottomStart).offset(x = -(56).dp)
            )
            Text(
                text = "100",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.BottomEnd).offset(x = 72.dp)
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = status,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "${slideValue.toInt()}%",
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraBold)
                )
            }

            Canvas(
                modifier = Modifier
                    .size(260.dp, 130.dp)
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                val firstArc = Path().apply {
                    arcTo(
                        rect = Rect(
                            offset = Offset(-85f, -90f),
                            size = Size(canvasWidth + 170f, (canvasHeight * 2) + 180f)
                        ),
                        startAngleDegrees = -180f,
                        sweepAngleDegrees = 50 * 1.8f,
                        forceMoveTo = false
                    )
                }

                val secondArc = Path().apply {
                    arcTo(
                        rect = Rect(
                            offset = Offset(-85f, -90f),
                            size = Size(canvasWidth + 170f, (canvasHeight * 2) + 180f)
                        ),
                        startAngleDegrees = -(180f - (50 * 1.8f)),
                        sweepAngleDegrees = 15f * 1.8f,
                        forceMoveTo = false
                    )
                }

                val thirdArc = Path().apply {
                    arcTo(
                        rect = Rect(
                            offset = Offset(-85f, -90f),
                            size = Size(canvasWidth + 170f, (canvasHeight * 2) + 180f)
                        ),
                        startAngleDegrees = -(180f - (65 * 1.8f)),
                        sweepAngleDegrees = 15f * 1.8f,
                        forceMoveTo = false
                    )
                }
                val fourthArc = Path().apply {
                    arcTo(
                        rect = Rect(
                            offset = Offset(-85f, -90f),
                            size = Size(canvasWidth + 170f, (canvasHeight * 2) + 180f)
                        ),
                        startAngleDegrees = -(180f - (80 * 1.8f)),
                        sweepAngleDegrees = 20f * 1.8f,
                        forceMoveTo = false
                    )
                }

                //draw inner arc background
                drawArc(
                    color = GREY,
                    startAngle = 0f,
                    sweepAngle = -180f,
                    useCenter = false,
                    style = Stroke(width = 40.dp.toPx()),
                    size = Size(canvasWidth, canvasHeight * 2)
                )

                //draw inner arc foreground
                drawArc(
                    color = innerArcColor,
                    startAngle = -180f,
                    sweepAngle = slideValue * 1.8f,
                    useCenter = false,
                    style = Stroke(width = 40.dp.toPx()),
                    size = Size(canvasWidth, canvasHeight * 2)
                )

                //draw outer segmented arc
                drawPath(
                    path = firstArc,
                    color = RED,
                    style = Stroke(width = 8.dp.toPx())
                )
                drawPath(
                    path = secondArc,
                    color = Orange,
                    style = Stroke(width = 8.dp.toPx())
                )
                drawPath(
                    path = thirdArc,
                    color = Green,
                    style = Stroke(width = 8.dp.toPx())
                )
                drawPath(
                    path = fourthArc,
                    color = DarkGreen,
                    style = Stroke(width = 8.dp.toPx())
                )

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

