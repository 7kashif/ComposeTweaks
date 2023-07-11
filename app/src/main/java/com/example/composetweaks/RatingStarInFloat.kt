package com.example.composetweaks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun RatingStarInFloat(
    rating: Float = 1.5f,
    maxRating: Int = 5,
    starSize: Dp = 32.dp
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        repeat(maxRating) { index ->
            Box(
                modifier = Modifier
                    .clip(StarShape())
                    .size(starSize)
                    .border(1.dp, Color.Gray, StarShape())
            ) {
                Canvas(
                    modifier = Modifier
                        .clip(StarShape())
                        .fillMaxSize()
                ) {
                    drawIntoCanvas {
                        drawRect(
                            color = Color.Gray,
                            size = Size(
                                width =
                                if (index < rating.toInt())
                                    this.size.width
                                else if (index == rating.toInt())
                                    this.size.width * (rating - rating.toInt())
                                else
                                   0f,
                                height = this.size.height
                            )
                        )
                    }
                }
            }
        }
    }
}

class StarShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(Path().apply {
        val path = Path()

        // Define the points of the star shape
        val outerRadius = size.minDimension / 2.0f
        val innerRadius = outerRadius / 2.5f
        val numPoints = 5
        val angleStep = 360.0f / numPoints

        var currentAngle = -90.0f
        for (i in 0 until numPoints * 2) {
            val radius = if (i % 2 == 0) outerRadius else innerRadius
            val x =
                size.width / 2.0f + radius * kotlin.math.cos(Math.toRadians(currentAngle.toDouble()))
                    .toFloat()
            val y =
                size.height / 2.0f + radius * kotlin.math.sin(Math.toRadians(currentAngle.toDouble()))
                    .toFloat()
            val point = Offset(x, y)

            if (i == 0) {
                path.moveTo(point.x, point.y)
            } else {
                path.lineTo(point.x, point.y)
            }

            currentAngle += angleStep
        }

        op(path1 = path, path2 = path, operation = PathOperation.Union)
    })
}


