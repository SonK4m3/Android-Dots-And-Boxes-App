package com.mobile.g5.dotsnboxes.presentation

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobile.g5.dotsnboxes.data.GameState
import com.mobile.g5.dotsnboxes.data.Rope
import com.mobile.g5.dotsnboxes.presentation.components.*
import com.mobile.g5.dotsnboxes.ui.theme.Beige
import com.mobile.g5.dotsnboxes.ui.theme.Blue01
import com.mobile.g5.dotsnboxes.ui.theme.Blue02
import com.mobile.g5.dotsnboxes.ui.theme.Orange01
import com.mobile.g5.dotsnboxes.ui.theme.Orange02
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun DotsAndBoxesField(
    gameState: GameState,
    boxesNumber: Int,
    modifier: Modifier = Modifier,
    player1Color: Color = Blue01,
    player2Color: Color = Orange01,
    onTapInField: (x: Int, y: Int, order: Int) -> Unit
) {
    val boardSize = 300.dp
    val cellSize = boardSize / boxesNumber

    var preRope by remember {
        mutableStateOf(false)
    }

    var posPreRope by remember {
        mutableStateOf(Pair(-1, -1))
    }

    var log by remember {
        mutableStateOf("none")
    }

    Canvas(
        modifier = modifier.then(
            Modifier
                .size(boardSize)
                .pointerInput(Unit) {
                    detectTapGestures { it ->
                        val pos = extractRopePosition(
                            x = it.x - (this.size.width - boardSize.toPx()) / 2,
                            y = it.y - (this.size.height - boardSize.toPx()) / 2,
                            scale = cellSize.toPx(),
                            boxesNumber = boxesNumber,
                            threshold = 0.2f
                        )
                        var row = pos.first
                        var col = pos.second
                        var isTapOnRopeField = pos.third

                        if (isTapOnRopeField) {
                            val ord: Int = gameState.field.sumOf { field ->
                                field?.count { rope -> rope != null } ?: 0
                            }
                            Log.d("AAA", "$row $col $ord")
                            log = "tap ${it} $row $col $ord"
                            onTapInField(row, col, ord)
                        }
                    }
                }
                .pointerInput(Unit) {
                    detectDragGestures(
//                        onDragStart = {
//                            val pos = extractRopePosition(
//                                x = it.x,
//                                y = it.y,
//                                scale = cellSize.toPx(),
//                                boxesNumber = boxesNumber,
//                                threshold = 0.2f
//                            )
//                            var row = pos.first
//                            var col = pos.second
//                            var isTapOnRopeField = pos.third
//
//                            if (isTapOnRopeField) {
//                                posPreRope = Pair(row, col)
////                                val ord: Int = gameState.field.sumOf { field ->
////                                    field?.count { rope -> rope != null } ?: 0
////                                }
////                                Log.d("AAA", "$row $col $ord")
////                                onTapInField(row, col, ord)
//                                log = "drag ${it} $row $col"
//                                preRope = true
//                            }
//                        },

                        onDragEnd = {
                            preRope = false
                        },

                        onDrag = { change, _ ->
//                            change.consume()
                            val pos = extractRopePosition(
                                x = change.position.x - (this.size.width - boardSize.toPx()) / 2,
                                y = change.position.y - (this.size.height - boardSize.toPx()) / 2,
                                scale = cellSize.toPx(),
                                boxesNumber = boxesNumber,
                                threshold = 0.2f
                            )
                            var row = pos.first
                            var col = pos.second
                            var isTapOnRopeField = pos.third

                            if (isTapOnRopeField) {
                                posPreRope = Pair(row, col)
//                                val ord: Int = gameState.field.sumOf { field ->
//                                    field?.count { rope -> rope != null } ?: 0
//                                }
//                                Log.d("AAA", "$row $col $ord")
//                                onTapInField(row, col, ord)
                                log = "drag ${change.position} $row $col"
                                preRope = true
                            }

                        }
                    )
                }
        )
    ) {
        gameState.field.forEachIndexed { row, _ ->
            gameState.field[row].forEachIndexed { col, rope ->
                val posRope =
                    if (row > boxesNumber) Offset(
                        (row - boxesNumber - 1) * cellSize.toPx() + (this.size.width - boardSize.toPx()) / 2,
                        col * cellSize.toPx() + (this.size.height - boardSize.toPx()) / 2
                    )
                    else Offset(
                        col * cellSize.toPx() + (this.size.width - boardSize.toPx()) / 2,
                        (row % boxesNumber) * cellSize.toPx() + (this.size.width - boardSize.toPx()) / 2
                    )
                rope?.let {
                    drawRope(
                        position = posRope.plus(Offset(-10.dp.toPx(), -10.dp.toPx())),
                        curvedEdgeLength = cellSize.toPx() + 18.dp.toPx(),
                        curvedEdgeWidth = 18.dp.toPx(),
                        cornerRadius = 9.dp.toPx(),
                        color = if (it.player == 1) player1Color else player2Color,
                        orientation = if (row > boxesNumber) Orientation.Vertical else Orientation.Horizontal
                    )

                }
            }


            if (preRope) {
                drawPreRope(
                    position = if (posPreRope.first > boxesNumber) Offset(
                        (posPreRope.first - boxesNumber - 1) * cellSize.toPx() + (this.size.width - boardSize.toPx()) / 2 - 10.dp.toPx(),
                        posPreRope.second * cellSize.toPx() + (this.size.height - boardSize.toPx()) / 2 - 20.dp.toPx()
                    )
                    else Offset(
                        posPreRope.second * cellSize.toPx() + (this.size.width - boardSize.toPx()) / 2 - 20.dp.toPx(),
                        (posPreRope.first % boxesNumber) * cellSize.toPx() + (this.size.height - boardSize.toPx()) / 2 - 10.dp.toPx()
                    ),
                    curvedEdgeLength = cellSize.toPx() + 44.dp.toPx(),
                    curvedEdgeWidth = 20.dp.toPx(),
                    cornerRadius = 9.dp.toPx(),
                    orientation = if (posPreRope.first > boxesNumber) Orientation.Vertical else Orientation.Horizontal
                )
            }
        }

        gameState.boxes.forEachIndexed { row, _ ->
            gameState.boxes[row].forEachIndexed { col, box ->
                val boxSize = Size(cellSize.toPx(), cellSize.toPx())
                val x = col * cellSize.toPx() + (this.size.width - boardSize.toPx()) / 2
                val y = row * cellSize.toPx() + (this.size.height - boardSize.toPx()) / 2
                val boxTopLeft = Offset(x, y)

                box?.let {
                    drawBoxes(
                        color = if(box == 1) Blue02 else Orange02,
                        topLeft = boxTopLeft,
                        size = boxSize,
                    )
                }
            }
        }


        (0..boxesNumber).forEach { i ->
            (0..boxesNumber).forEach { j ->
                val xBackDstDot = j * cellSize.toPx() + (this.size.width - boardSize.toPx()) / 2
                val yBackDstBot = i * cellSize.toPx() + (this.size.height - boardSize.toPx()) / 2

                drawDot(
                    center = Offset(xBackDstDot, yBackDstBot),
                    circleRadius = 12.dp.toPx(),
                    shadowOffset = 4.dp.toPx()
                )
            }
        }
    }

    Text(text = log)
}

@Preview(showSystemUi = true, device = "id:pixel_4", showBackground = true)
@Composable
fun PrevApp() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        DotsAndBoxesField(
            modifier = Modifier
                .border(width = 1.dp, color = Color.Green)
                .background(Beige)
                .aspectRatio(1f),
            gameState = GameState(
                field = arrayOf(
                    arrayOf(Rope(1, 1), Rope(1, 3), null, null, null),  // vertical 1
                    arrayOf(Rope(1, 8), null, null, null, null),
                    arrayOf(null, null, null, null, null),
                    arrayOf(null, null, null, null, null),
                    arrayOf(null, null, null, null, null),
                    arrayOf(null, null, null, null, null),  // vertical 6
                    arrayOf(Rope(1, 6), null, null, null, null),  // horizontal 1
                    arrayOf(Rope(2, 7), null, null, null, null),
                    arrayOf(null, null, null, null, null),
                    arrayOf(null, null, Rope(2, 2), null, null),
                    arrayOf(null, Rope(1, 4), null, Rope(2, 5), null),
                    arrayOf(null, null, Rope(3, 0), null, null),  // horizontal 6
                ),
                boxes = arrayOf(
                    arrayOf(1, null, null, null, null),
                    arrayOf(null, null, null, null, null),
                    arrayOf(null, null, null, null, null),
                    arrayOf(null, null, null, 2, null),
                    arrayOf(null, null, null, null, null)
                )
            ),
            boxesNumber = 5,
            onTapInField = { x, y, ord -> println("$x $y $ord") }
        )
    }

}

private fun extractRopePosition(
    x: Float,
    y: Float,
    scale: Float,
    boxesNumber: Int,
    threshold: Float
): Triple<Int, Int, Boolean> {

    if (x < 0f || y < 0f)
        return Triple(-1, -1, false)

    val posX = x / scale
    val posY = y / scale
    val roundUpX = ceil(posX).toInt()
    val roundDownX = floor(posX).toInt()
    val roundUpY = ceil(posY).toInt()
    val roundDownY = floor(posY).toInt()

    var row = -1
    var column = -1
    var isTapOnRopeField = false

    if (abs(roundUpX - posX) <= threshold || abs(posX - roundDownX) <= threshold) {
        row =
            if (roundUpX - posX <= threshold) boxesNumber + roundUpX + 1 else boxesNumber + roundDownX + 1
        column = roundDownY
        isTapOnRopeField = true
    } else if (roundUpY - posY <= threshold || posY - roundDownY <= threshold) {
        row = if (roundUpY - posY <= threshold) roundUpY else roundDownY
        column = roundDownX
        isTapOnRopeField = true
    }

    return Triple(row, column, isTapOnRopeField)
}