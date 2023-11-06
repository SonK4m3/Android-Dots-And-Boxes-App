package com.mobile.g5.dotsnboxes.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobile.g5.dotsnboxes.ui.theme.*

fun DrawScope.drawDot(
    center: Offset = Offset.Zero,
    circleRadius: Float,
    shadowOffset: Float
) {
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color.Black, Color.Transparent),
            radius = circleRadius,
            center = center.plus(Offset(shadowOffset, shadowOffset))
        ),
        center = center.plus(Offset(shadowOffset, shadowOffset))
    )

    drawCircle(
        color = Black01,
        radius = circleRadius,
        center = center
    )
}

fun DrawScope.drawRope(
    color: Color,
    position: Offset = Offset.Zero,
    curvedEdgeLength: Float,
    curvedEdgeWidth: Float,
    cornerRadius: Float,
    orientation: Orientation = Orientation.Horizontal,
) {

    val arcHeight = (curvedEdgeLength - 4f * (cornerRadius - 0.5.dp.toPx()))
    val arcWidth = curvedEdgeWidth * 0.4f

    val top = if (orientation == Orientation.Horizontal)
        Offset((curvedEdgeLength - arcHeight) / 2, -arcWidth / 2 - 2f)
    else
        Offset(-arcWidth / 2 - 2f, (curvedEdgeLength - arcHeight) / 2)

    val bottom = if (orientation == Orientation.Horizontal)
        Offset((curvedEdgeLength - arcHeight) / 2, curvedEdgeWidth - arcWidth / 2 + 2f)
    else
        Offset(curvedEdgeWidth - arcWidth / 2 + 2f, (curvedEdgeLength - arcHeight) / 2)

    val angleTop = if (orientation == Orientation.Horizontal)
        Offset(0f, 180f)
    else
        Offset(-90f, 180f)

    val angleBottom = if (orientation == Orientation.Horizontal)
        Offset(0f, -180f)
    else
        Offset(-90f, -180f)

    drawRoundRect(
        color = color,
        topLeft = position,
        size = if (orientation == Orientation.Horizontal) Size(
            curvedEdgeLength, curvedEdgeWidth - 2f
        ) else Size(curvedEdgeWidth, curvedEdgeLength - 2f),
        cornerRadius = CornerRadius(cornerRadius),
        style = Fill
    )

    drawArc(
        color = Beige,
        topLeft = position.plus(top),
        size = if (orientation == Orientation.Horizontal) Size(
            arcHeight, arcWidth
        ) else Size(arcWidth, arcHeight),
        startAngle = angleTop.x,
        sweepAngle = angleTop.y,
        useCenter = true,
        blendMode = DrawScope.DefaultBlendMode,
    )

    drawArc(
        color = Beige,
        topLeft = position.plus(bottom),
        size = if (orientation == Orientation.Horizontal) Size(
            arcHeight, arcWidth
        ) else Size(arcWidth, arcHeight),
        startAngle = angleBottom.x,
        sweepAngle = angleBottom.y,
        useCenter = true,
        blendMode = DrawScope.DefaultBlendMode
    )
}

fun DrawScope.drawPreRope(
    position: Offset = Offset.Zero,
    cornerRadius: Float,
    curvedEdgeLength: Float,
    curvedEdgeWidth: Float,
    color: Color = Red01,
    orientation: Orientation = Orientation.Horizontal,
) {
    drawRope(
        position = position,
        cornerRadius = cornerRadius,
        curvedEdgeWidth = curvedEdgeWidth,
        curvedEdgeLength = curvedEdgeLength,
        color = color,
        orientation = orientation
    )

    val startLine = if (orientation == Orientation.Horizontal)
        position.plus(Offset(0f, curvedEdgeWidth / 2))
    else
        position.plus(Offset(curvedEdgeWidth / 2, 0f))

    val endLine = if (orientation == Orientation.Horizontal)
        position.plus(Offset(curvedEdgeLength, curvedEdgeWidth / 2))
    else
        position.plus(Offset(curvedEdgeWidth / 2, curvedEdgeLength))

    drawLine(
        color = Red02,
        start = startLine,
        end = endLine,
        strokeWidth = 3.dp.toPx()
    )
}

fun DrawScope.drawBoxes(
    color: Color,
    topLeft: Offset = Offset.Zero,
    size: Size,
) {
    val cornerRadiusValue = 18.dp.toPx()

    drawRoundRect(
        color = color,
        topLeft = topLeft,
        size = size,
        style = Fill,
        cornerRadius = CornerRadius(cornerRadiusValue)
    )

    drawRoundRect(
        brush = Brush.radialGradient(
            colors = listOf(Color.Gray, Color.Transparent),
        ),
        topLeft = topLeft,
        size = size,
        style = Fill,
        cornerRadius = CornerRadius(cornerRadiusValue)
    )
}

@Preview
@Composable
fun PrevRoot() {
    Box(
        modifier = Modifier
            .size(150.dp)
            .padding(5.dp)
            .background(Color.Gray),
        contentAlignment = Alignment.TopStart,
    ) {
        Canvas(modifier = Modifier, onDraw = {
            drawRope(
                curvedEdgeLength = 250f,
                curvedEdgeWidth = 60f,
                cornerRadius = 9f,
                color = Orange01,
                orientation = Orientation.Vertical
            )

            translate(0f, 30.dp.toPx()) {
                drawPreRope(
                    curvedEdgeLength = 300f,
                    curvedEdgeWidth = 60f,
                    cornerRadius = 9f,
                    orientation = Orientation.Vertical
                )
            }
        })
    }
}