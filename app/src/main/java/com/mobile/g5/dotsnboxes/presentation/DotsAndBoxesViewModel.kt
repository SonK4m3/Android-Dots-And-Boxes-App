package com.mobile.g5.dotsnboxes.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.g5.dotsnboxes.data.GameState
import com.mobile.g5.dotsnboxes.data.MakeTurn
import com.mobile.g5.dotsnboxes.data.RealtimeMessagingClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class DotsAndBoxesViewModel @Inject constructor(
    private val client: RealtimeMessagingClient
) : ViewModel() {

    val state = client.getGameStateStream()
        .onStart { _isConnecting.value = true }
        .onEach { _isConnecting.value = false }
        .catch { t -> _showConnectionError.value = t is ConnectException }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), GameState())

    private val _isConnecting = MutableStateFlow(false)
    val isConnecting = _isConnecting.asStateFlow()

    private val _showConnectionError = MutableStateFlow(false)
    val showConnectionError = _showConnectionError.asStateFlow()

    fun finishTurn(x: Int, y: Int, order: Int) {
        if(state.value.field[x][y] != null || state.value.winningPlayer != null)
            return

        viewModelScope.launch {
            client.sendAction(MakeTurn(x, y, order))
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            client.close()
        }
    }
}