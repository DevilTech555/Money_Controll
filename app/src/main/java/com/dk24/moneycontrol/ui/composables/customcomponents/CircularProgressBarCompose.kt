package com.dk24.moneycontrol.ui.composables.customcomponents

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularProgressBarCompose(
    modifier: Modifier = Modifier,
    percentage: Float,
    fillColor: Color,
    backgroundColor: Color,
    strokeWidth: Dp
) {
    Canvas(
        modifier = modifier
    ) {
        // Background Line
        drawArc(
            color = backgroundColor,
            140f,
            260f,
            false,
            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
            size = Size(size.width, size.height)
        )

        drawArc(
            color = fillColor,
            140f,
            percentage * 260f,
            false,
            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
            size = Size(size.width, size.height)
        )


        val angleInDegrees = (percentage * 260.0) + 50.0
        val radius = (size.height / 2)
        val x = -(radius * sin(Math.toRadians(angleInDegrees))).toFloat() + (size.width / 2)
        val y = (radius * cos(Math.toRadians(angleInDegrees))).toFloat() + (size.height / 2)

        drawCircle(
            color = Color.White,
            radius = 5f,
            center = Offset(x, y)
        )
    }
}
