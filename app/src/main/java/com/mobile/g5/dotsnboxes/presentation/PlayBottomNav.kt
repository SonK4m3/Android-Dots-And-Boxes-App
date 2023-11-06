package com.mobile.g5.dotsnboxes.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.mobile.g5.dotsnboxes.R

enum class BottomIcons {
    MENU, SHARE, FAV, DELETE
}

@Composable
fun PlayBottomNav(
    modifier: Modifier = Modifier,
    items: List<String> = listOf(""),
) {
    val selected = remember { mutableStateOf(BottomIcons.MENU) }

    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color(0xFFF0E7BC),
        //contentColor = Color.Red,
        cutoutShape = CircleShape,
        //elevation = 20.dp,
        //contentPadding = AppBarDefaults.ContentPadding,
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row() {
                    IconButton(onClick = { selected.value = BottomIcons.FAV }) {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = null,
                            tint = if (selected.value == BottomIcons.FAV) Color.Red else Color.Black
                        )
                    }
                    IconButton(onClick = { selected.value = BottomIcons.SHARE }) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = null,
                            tint = if (selected.value == BottomIcons.SHARE) Color.Red else Color.Black
                        )
                    }
                    IconButton(onClick = { selected.value = BottomIcons.DELETE }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = null,
                            tint = if (selected.value == BottomIcons.DELETE) Color.Red else Color.Black
                        )
                    }
                }
                Row() {
                    IconButton(onClick = { selected.value = BottomIcons.MENU }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = null,
                            tint = if (selected.value == BottomIcons.MENU) Color.Red else Color.Black
                        )
                    }
                }
            }
        })
}

@Preview
@Composable
fun BottomNavPrev() {
    PlayBottomNav()
}