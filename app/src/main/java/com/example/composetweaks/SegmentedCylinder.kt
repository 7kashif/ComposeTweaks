package com.example.composetweaks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.composetweaks.ui.theme.CyanCylinderBar
import com.example.composetweaks.ui.theme.CyanCylinderTop
import com.example.composetweaks.ui.theme.GrayCylinderBar
import com.example.composetweaks.ui.theme.GrayCylinderTop
import com.example.composetweaks.ui.theme.GreenCylinderBar
import com.example.composetweaks.ui.theme.GreenCylinderTop
import com.example.composetweaks.ui.theme.RedCylinderBar
import com.example.composetweaks.ui.theme.RedCylinderTop
import com.example.composetweaks.ui.theme.YellowCylinderBar
import com.example.composetweaks.ui.theme.YellowCylinderTop

val cylindersList: List<Triple<Float, Color, Color>> = listOf(
    Triple(2f, YellowCylinderBar, YellowCylinderTop),
    Triple(5f, CyanCylinderBar, CyanCylinderTop),
    Triple(15f, RedCylinderBar, RedCylinderTop),
    Triple(20f, GreenCylinderBar, GreenCylinderTop)
)


@Composable
fun SegmentedCylinder(
    cylinderSize: DpSize = DpSize(96.dp, 500.dp),
    cylindersTopWidth: Dp = 32.dp
) {

    require(cylindersList.sumOf { it.first.toDouble() } <= 100.0) {
        throw IllegalArgumentException("Sum of all cylinders should be less than or equal to 100.")
    }

    Canvas(
        modifier = Modifier.size(cylinderSize)
    ) {

        //drawBackground Cylinder
        drawCylinder(
            barColor = GrayCylinderBar,
            topColor = GrayCylinderTop,
            height = size.height + cylindersTopWidth.toPx(),
            radius = cylindersTopWidth.toPx()
        )
        var totalOffSet = 0f
        cylindersList.forEach {  cylinderStats ->
            totalOffSet += cylinderStats.first

            drawCylinder(
                barColor = cylinderStats.second,
                topColor = cylinderStats.third,
                height = ((cylinderStats.first / 100) * size.height) + cylindersTopWidth.toPx(),
                radius = cylindersTopWidth.toPx(),
                yOffSet = (((100 - totalOffSet) / 100) * size.height)
            )
        }
    }

}

fun DrawScope.drawCylinder(
    barColor: Color,
    topColor: Color,
    height: Float,
    radius: Float,
    yOffSet: Float = 0f
) {
    drawRect(
        color = barColor,
        topLeft = Offset(0f, (radius / 2) + yOffSet),
        size = Size(size.width, height - radius),
    )
    drawOval(
        color = barColor,
        topLeft = Offset(0f, (height - radius) + yOffSet),
        size = Size(size.width, radius),
    )
    drawOval(
        color = topColor,
        topLeft = Offset(0f, yOffSet),
        size = Size(size.width, radius),
    )
}