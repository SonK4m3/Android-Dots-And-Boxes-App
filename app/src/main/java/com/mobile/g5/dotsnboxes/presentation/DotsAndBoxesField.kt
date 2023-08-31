package com.mobile.g5.dotsnboxes.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobile.g5.dotsnboxes.data.GameState
import com.mobile.g5.dotsnboxes.ui.components.GameScreen

@Composable
fun DotsAndBoxesField(
    gameState: GameState,
    modifier: Modifier,
    onTapInField: (x: Int, y: Int) -> Unit
) {
    Canvas(
        modifier = modifier,
        onDraw = {

        }
    )
}