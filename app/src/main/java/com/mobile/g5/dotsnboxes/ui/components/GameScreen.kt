package com.mobile.g5.dotsnboxes.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobile.g5.dotsnboxes.ui.theme.*

@Composable
fun GameScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBackground)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Player 1: 0", fontSize = 10.sp)
            Text(text = "Player 2: 0", fontSize = 10.sp)
            Text(text = "Player 3: 0", fontSize = 10.sp)
            Text(text = "Player 4: 0", fontSize = 10.sp)
        }

        Text(
            text = "Dots And Boxs",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            BoardBase(matrixSize = 5, modifier = Modifier)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Player 1's turn", fontStyle = FontStyle.Italic)
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.elevation(5.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = BlueCustom,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Play Again")
            }
        }

    }
}

@Composable
fun BoardBase(
    matrixSize: Int,
    modifier: Modifier
) {
    val colorStroke = Color.Gray
    val boardSize = 300.dp

    Canvas(
        modifier = modifier
            .size(boardSize + 30.dp)
            .padding(10.dp)
    ) {
        val cellSize = boardSize.toPx() / matrixSize

//        for (i in 0..matrixSize) {
//            val y = i * cellSize
//            drawLine(
//                color = colorStroke,
//                start = Offset(0f, y),
//                end = Offset(boardSize.toPx(), y),
//                strokeWidth = 4.dp.toPx(),
//            )
//        }
//
//        for (j in 0..matrixSize) {
//            val x = j * cellSize
//            drawLine(
//                color = colorStroke,
//                start = Offset(x, 0f),
//                end = Offset(x, boardSize.toPx()),
//                strokeWidth = 4.dp.toPx(),
//            )
//        }


        val tran = -10.dp.toPx()

        withTransform({
            translate(tran, tran)
        }) {
            exampleBox(
                curvedEdgeLength = cellSize + 20.dp.toPx(),
                curvedEdgeWidth = 20.dp.toPx(),
                cornerRadius = 10.dp.toPx(),
                color = Blue01,
            )        }


        for (i in 0..matrixSize) {
            for (j in 0..matrixSize) {

                val offset_x =
//                    if (j == 0) 10.dp.toPx() else
                            (if (j == matrixSize) -10.dp.toPx() else 0f)
                val offset_y =
//                    if (i == 0) 10.dp.toPx() else
                            (if (i == matrixSize) -10.dp.toPx() else 0f)

                val x = j * cellSize - size.width / 2
                val y = i * cellSize - size.height / 2

                withTransform({
                    translate(x, y)
                }) {
                    drawDot()
                }
            }
        }
    }
}

fun DrawScope.drawDot() {
//    withTransform({ translate(20f, 15f) }) {
//        drawCircle(
//            color = Color.Gray,
//            radius = 10.dp.toPx()
//        )
//    }
//    drawCircle(
//        color = Color.White,
//        radius = 8.dp.toPx()
//    )
    drawCircle(
        color = Black01,
        radius = 10.dp.toPx(),
//        style = Stroke(width = 4.dp.toPx())
    )
}

fun DrawScope.drawBoxLine(
    vertical: Boolean = true,
    color: Color,
    start: Offset,
    end: Offset,
    modifier: Modifier
) {

}

@Composable
fun RoundedCornerShapeDemo() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .padding(5.dp),
        contentAlignment = Alignment.TopStart,
    ) {
        Canvas(modifier = Modifier, onDraw = {
            exampleBox(
                curvedEdgeLength = 250f,
                curvedEdgeWidth = 60f,
                cornerRadius = 25f,
                color = OrangeRed
            )
        })
    }
}

fun DrawScope.exampleBox(
    cornerRadius: Float,
    curvedEdgeLength: Float,
    curvedEdgeWidth: Float,
    color: Color = Blue01,
    orientation: Orientation = Orientation.Vertical
) {
    val arcHeight = (curvedEdgeLength - 2 * cornerRadius)
    val arcWidth = curvedEdgeWidth / 3
    val top = Offset(cornerRadius, -arcWidth / 2)
    val bottom = Offset(cornerRadius, curvedEdgeWidth - arcWidth / 2)

    drawRoundRect(
        color = color, topLeft = Offset.Zero,
        size = Size(curvedEdgeLength, curvedEdgeWidth),
        cornerRadius = CornerRadius(cornerRadius),
        style = Fill
    )

    drawArc(
        color = Beige,
        topLeft = top,
        size = Size(arcHeight, arcWidth),
        startAngle = 0f,
        sweepAngle = 180f,
        useCenter = true
    )

    drawArc(
        color = Beige,
        topLeft = bottom,
        size = Size(arcHeight, arcWidth),
        startAngle = 180f,
        sweepAngle = 180f,
        useCenter = true
    )
}

@Preview(showBackground = true)
@Composable
fun Prev() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(Beige),
        contentAlignment = Alignment.Center
    ) {
        BoardBase(matrixSize = 5, modifier = Modifier)
    }
}

@Preview
@Composable
fun PrevRoot() {
    RoundedCornerShapeDemo()
}