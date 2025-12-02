package com.example.composetweaks.ui.theme

import androidx.compose.animation.core.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random
import kotlinx.coroutines.delay

@Composable
fun SpinToWinWheel(
    items: List<String> = listOf("100", "200", "50", "500", "0", "300", "150", "250"),
    colors: List<Color> = listOf(
        Color(0xFFFF6B6B), Color(0xFF4ECDC4), Color(0xFFFFE66D),
        Color(0xFF95E1D3), Color(0xFFF38181), Color(0xFFAA96DA),
        Color(0xFFFCBAD3), Color(0xFFA8E6CF)
    )
) {
    var isSpinning by remember { mutableStateOf(false) }
    var rotation by remember { mutableFloatStateOf(0f) }
    var winningItem by remember { mutableStateOf("") }
    var showPopup by remember { mutableStateOf(false) }
    val textMeasurer = rememberTextMeasurer()

    val animatedRotation by animateFloatAsState(
        targetValue = rotation,
        animationSpec = tween(
            durationMillis = 3000,
            easing = FastOutSlowInEasing
        ),
        finishedListener = {
            isSpinning = false
            // Calculate which item won (pointer is at top, so 270 degrees)
            val normalizedRotation = (rotation % 360 + 360) % 360
            val pointerAngle = 270f // Top of the wheel
            val adjustedAngle = (pointerAngle - normalizedRotation + 360) % 360
            val sweepAngle = 360f / items.size
            val winningIndex = ((adjustedAngle / sweepAngle).toInt()) % items.size
            winningItem = items[winningIndex]
        },
        label = "wheel_rotation"
    )

    // Show popup after winning item is set
    LaunchedEffect(winningItem) {
        if (winningItem.isNotEmpty()) {
            delay(300) // Small delay before showing popup
            showPopup = true
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(300.dp)) {
                val sweepAngle = 360f / items.size

                // Draw the colored segments with rotation
                rotate(animatedRotation) {
                    items.forEachIndexed { index, item ->
                        val startAngle = sweepAngle * index

                        drawArc(
                            color = colors[index % colors.size],
                            startAngle = startAngle,
                            sweepAngle = sweepAngle,
                            useCenter = true,
                            size = Size(size.width, size.height)
                        )
                    }
                }

                // Draw text labels always upright (no rotation)
                items.forEachIndexed { index, item ->
                    val startAngle = sweepAngle * index + animatedRotation
                    val angle = Math.toRadians((startAngle + sweepAngle / 2).toDouble())
                    val radius = size.width / 3
                    val textX = center.x + radius * cos(angle).toFloat()
                    val textY = center.y + radius * sin(angle).toFloat()

                    val textLayoutResult = textMeasurer.measure(
                        text = item,
                        style = TextStyle(fontSize = 20.sp, color = Color.White)
                    )

                    drawText(
                        textLayoutResult = textLayoutResult,
                        topLeft = Offset(
                            textX - textLayoutResult.size.width / 2,
                            textY - textLayoutResult.size.height / 2
                        )
                    )
                }
            }

            // Center pointer
            Canvas(modifier = Modifier.size(30.dp)) {
                drawCircle(color = Color.Red, radius = size.width / 2)
            }

            // Top pointer (triangle/needle) - rounded top, pointy bottom
            Canvas(
                modifier = Modifier
                    .size(300.dp)
                    .offset(y = (-10).dp)
            ) {
                val path = androidx.compose.ui.graphics.Path().apply {
                    // Start at bottom point
                    moveTo(center.x, 50f)
                    // Line to left side
                    lineTo(center.x - 20f, 10f)
                    // Curve along the top (rounded)
                    cubicTo(
                        center.x - 20f, 0f,
                        center.x - 10f, -5f,
                        center.x, -5f
                    )
                    cubicTo(
                        center.x + 10f, -5f,
                        center.x + 20f, 0f,
                        center.x + 20f, 10f
                    )
                    // Line to bottom point
                    lineTo(center.x, 50f)
                    close()
                }
                drawPath(
                    path = path,
                    color = Color.Red
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (!isSpinning) {
                    isSpinning = true
                    rotation += 360 * 5 + Random.nextFloat() * 360
                }
            },
            enabled = !isSpinning
        ) {
            Text(if (isSpinning) "Spinning..." else "Spin!")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (winningItem.isNotEmpty()) {
            Text(
                text = "You won: $winningItem",
                style = TextStyle(fontSize = 24.sp, color = Color.Black),
                modifier = Modifier.padding(16.dp)
            )
        }
    }

    if(showPopup) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )
    }

    AnimatedVisibility(
        visible = showPopup,
        enter = scaleIn(
            initialScale = 0.2f,
            animationSpec = tween(durationMillis = 500)
        ) + fadeIn(),
        exit = scaleOut(
            animationSpec = tween(durationMillis = 500)
        ) + fadeOut()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "🎉 Congratulations! 🎉",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF6B6B)
                    )
                )

                Text(
                    text = "You won",
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                )

                Text(
                    text = "$$winningItem",
                    style = TextStyle(
                        fontSize = 48.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF4ECDC4)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { showPopup = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Awesome!", fontSize = 16.sp)
                }
            }
        }
    }
}