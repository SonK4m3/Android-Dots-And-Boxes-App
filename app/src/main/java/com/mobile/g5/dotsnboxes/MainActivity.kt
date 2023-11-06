package com.mobile.g5.dotsnboxes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.FormatListNumbered
import androidx.compose.material.icons.rounded.ViewList
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mobile.g5.dotsnboxes.data.GameState
import com.mobile.g5.dotsnboxes.data.Rope
import com.mobile.g5.dotsnboxes.presentation.DotsAndBoxesField
import com.mobile.g5.dotsnboxes.presentation.DotsAndBoxesViewModel
import com.mobile.g5.dotsnboxes.presentation.PlayBottomNav
import com.mobile.g5.dotsnboxes.ui.theme.Beige
import com.mobile.g5.dotsnboxes.ui.theme.Brown01
import com.mobile.g5.dotsnboxes.ui.theme.DotsAndBoxsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DotsAndBoxsTheme {
                val viewModel = hiltViewModel<DotsAndBoxesViewModel>()
                val state by viewModel.state.collectAsState()
                val isConnecting by viewModel.isConnecting.collectAsState()
                val showConnectionError by viewModel.showConnectionError.collectAsState()

                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        PlayBottomNav()
                    }
                ) { innerPadding ->
                    if (showConnectionError) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Could not connect to server",
                                color = MaterialTheme.colors.error
                            )
                        }
                        return@Scaffold
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Brown01),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            IconButton(
                                modifier = Modifier.size(36.dp),
                                onClick = {}) {
                                Icon(
                                    Icons.Rounded.FormatListNumbered,
                                    contentDescription = null
                                )
                            }

                            Row(
                                modifier = Modifier.padding(top = 30.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "1",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Icon(
                                    modifier = Modifier.size(84.dp),
                                    imageVector = Icons.Rounded.Face,
                                    contentDescription = null
                                )
                                Icon(
                                    modifier = Modifier.size(84.dp),
                                    imageVector = Icons.Rounded.Face,
                                    contentDescription = null
                                )
                                Text(
                                    text = "1",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }

                            IconButton(
                                modifier = Modifier.size(36.dp),
                                onClick = {}) {
                                Icon(
                                    Icons.Rounded.ViewList,
                                    contentDescription = null
                                )
                            }
                        }


                        DotsAndBoxesField(
//                            gameState = GameState(
//                                field = arrayOf(
//                                    arrayOf(
//                                        Rope(1, 1),
//                                        Rope(1, 3),
//                                        null,
//                                        null,
//                                        null
//                                    ),  // vertical 1
//                                    arrayOf(null, null, null, null, null),
//                                    arrayOf(null, null, null, null, null),
//                                    arrayOf(null, null, null, null, null),
//                                    arrayOf(null, null, null, null, null),
//                                    arrayOf(null, null, null, null, null),  // vertical 6
//                                    arrayOf(null, null, null, null, null),  // horizontal 1
//                                    arrayOf(null, null, null, null, null),
//                                    arrayOf(null, null, null, null, null),
//                                    arrayOf(null, null, Rope(2, 2), null, null),
//                                    arrayOf(null, Rope(1, 4), null, Rope(2, 5), null),
//                                    arrayOf(null, null, Rope(3, 0), null, null),  // horizontal 6
//                                )
//                            ),
                            gameState = state,
                            boxesNumber = 5,
                            onTapInField = viewModel::finishTurn,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Beige)
                                .aspectRatio(1f)
                                .padding(10.dp)
                        )
                    }
                    if (isConnecting) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AppPrev() {
//    val viewModel = hiltViewModel<DotsAndBoxesViewModel>()
//    val scaffoldState = rememberScaffoldState()
//    Scaffold(
//        scaffoldState = scaffoldState,
//        bottomBar = {
//            PlayBottomNav()
//        }
//    )
}