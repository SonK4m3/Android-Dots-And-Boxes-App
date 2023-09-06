package com.mobile.g5.dotsnboxes.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobile.g5.dotsnboxes.data.GameState
import com.mobile.g5.dotsnboxes.presentation.components.*
import com.mobile.g5.dotsnboxes.ui.theme.Blue01
import com.mobile.g5.dotsnboxes.ui.theme.Orange01
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun DotsAndBoxesField(
    gameState: GameState,
    boxesNumber: Int,
    modifier: Modifier = Modifier,
    player1Color: Color = Blue01,
    player2Color: Color = Orange01,
    onTapInField: (x: Int, y: Int) -> Unit
) {
    val boardSize = 300.dp
    val cellSize = boardSize / boxesNumber

    var log by remember { mutableStateOf("none") }

    Canvas(
        modifier = modifier.then(
            Modifier
                .size(boardSize)
                .pointerInput(true) {
                    detectTapGestures {
                        val posX = it.x / cellSize.toPx()
                        val posY = it.y / cellSize.toPx()
                        val roundUpX = ceil(posX).toInt()
                        val roundDownX = floor(posX).toInt()
                        val roundUpY = ceil(posY).toInt()
                        val roundDownY = floor(posY).toInt()
                        var x = 0
                        var y = 0
                        var ori: Orientation? = null

                        if (roundUpX - posX <= 0.2f || posX - roundDownX <= 0.2f) {
                            ori = Orientation.Vertical
                            x = boxesNumber + 1 + (if (roundUpX - posX <= 0.2f) roundUpX else roundDownX)
                            y = roundDownY
                        }
                        else if (roundUpY - posY <= 0.2f || posY - roundDownY <= 0.2f) {
                            ori = Orientation.Horizontal
                            x = if (roundUpY - posY <= 0.2f) roundUpY else roundDownY
                            y = roundDownX
                        }

                        log =
                            "$roundDownY $roundUpY $posY  $ori $x $y"

                        onTapInField(x, y)
                    }
                }
        )
    ) {
        val backDstRope = -9.dp.toPx()

        withTransform({
            translate(backDstRope, backDstRope)
        }) {
            gameState.field.forEachIndexed { y, _ ->
                gameState.field[y].forEachIndexed { x, player ->
                    val posRope =
                        if (y > boxesNumber) Offset(
                            (y - boxesNumber - 1) * cellSize.toPx(),
                            x * cellSize.toPx()
                        )
                        else Offset(x * cellSize.toPx(), (y % boxesNumber) * cellSize.toPx())
                    player?.let {
                        when (player) {
                            3 -> drawPreRope(
                                position = posRope.plus(
                                    if (y > boxesNumber) Offset(0f, -13.dp.toPx())
                                    else Offset(-13.dp.toPx(), 0f)
                                ),
                                curvedEdgeLength = cellSize.toPx() + 44.dp.toPx(),
                                curvedEdgeWidth = 20.dp.toPx(),
                                cornerRadius = 9.dp.toPx(),
                                orientation = if (y > boxesNumber) Orientation.Vertical else Orientation.Horizontal
                            )

                            else -> drawRope(
                                position = posRope,
                                curvedEdgeLength = cellSize.toPx() + 18.dp.toPx(),
                                curvedEdgeWidth = 18.dp.toPx(),
                                cornerRadius = 9.dp.toPx(),
                                color = if (it == 1) player1Color else player2Color,
                                orientation = if (y > boxesNumber) Orientation.Vertical else Orientation.Horizontal
                            )
                        }
                    }
                }
            }
        }

        for (i in 0..boxesNumber) {
            for (j in 0..boxesNumber) {

                val xBackDstDot = j * cellSize.toPx() - boardSize.toPx() / 2
                val yBackDstBot = i * cellSize.toPx() - boardSize.toPx() / 2

                withTransform({
                    translate(xBackDstDot, yBackDstBot)
                }) {
                    drawDot(12.dp.toPx(), 3.dp.toPx())
                }
            }
        }
    }

    Text(
        log,
        modifier = Modifier
            .padding(top = 10.dp),
    )
}

@Preview
@Composable
fun PrevApp() {
    DotsAndBoxesField(
        modifier = Modifier,
        gameState = GameState(
            field = arrayOf(
                arrayOf(1, 1, 1, null, 2),  // vertical 1
                arrayOf(2, 1, 2, null, null),
                arrayOf(null, null, null, null, null),
                arrayOf(null, 1, null, null, null),
                arrayOf(null, null, 1, 2, null),
                arrayOf(null, null, null, null, null),  // vertical 6
                arrayOf(1, 1, null, null, null),  // horizontal 1
                arrayOf(1, 2, null, 3, null),
                arrayOf(null, null, null, null, null),
                arrayOf(null, null, 1, null, null),
                arrayOf(null, 1, null, 2, null),
                arrayOf(null, null, 3, null, 1),  // horizontal 6
            )
        ),
        boxesNumber = 5,
        onTapInField = { x, y -> }
    )

}