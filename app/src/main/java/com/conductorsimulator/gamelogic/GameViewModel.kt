package com.conductorsimulator.gamelogic

import android.content.res.Configuration
import android.graphics.PointF
import android.util.Size
import android.util.SizeF
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.conductorsimulator.gamelogic.entities.Passenger
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random


class GameViewModel() : ViewModel() {
    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()
    fun onEvent(event: GameEvent) {
        when (event) {
            GameEvent.StartGame -> {
                val highScore = state.value.highScore
                _state.update { updateGame(it, reset = true) }
                _state.update { it.copy(highScore = highScore, gameState = State.PLAY) }

                viewModelScope.launch {
                    while (state.value.gameState == State.PLAY) {
                        delay(1000)

                        _state.value = updateGame(state.value)
                    }
                }
            }

            GameEvent.KillGame -> {
                val newHighScore =
                    if (state.value.score > state.value.highScore) state.value.score
                    else state.value.highScore

                _state.update {
                    it.copy(
                        gameState = State.MENU,
                        highScore = newHighScore,
                        isOver = true
                    )
                }


            }

            GameEvent.PauseGame -> {
                _state.update { it.copy(gameState = State.PAUSED) }
            }

            GameEvent.PlusScore -> {
                _state.update { it.copy(score = state.value.score + 1) }
            }

            GameEvent.TerminalOn -> {
                _state.update {
                    if (state.value.conductor.terminal) {
                        it.copy(
                            conductor = it.conductor.copy(terminal = false)
                        )
                    } else {
                        it.copy(
                            conductor = it.conductor.copy(terminal = true)
                        )
                    }
                }
            }

            GameEvent.Payment -> {
                if (state.value.conductor.terminal) {
                    _state.update { it.copy(score = state.value.score + 10) }
                    _state.update {
                        it.copy(
                            conductor = it.conductor.copy(money = state.value.conductor.money + 33) // Устанавливаем terminal в true
                        )
                    }
                }
            }
            GameEvent.Rabbit ->{
                _state.update { it.copy(score = state.value.score - 20) }
                _state.update {
                    it.copy(
                        conductor = it.conductor.copy(money = state.value.conductor.money -50) // Устанавливаем terminal в true
                    )
                }
            }
        }

    }

    private fun updateGame(currentGame: GameState, reset: Boolean = false): GameState {
        return if (reset) {
            GameState() // Возвращаем новое состояние для перезапуска игры
        } else {
            if (currentGame.isOver) {
                currentGame
            } else {
                currentGame // Ваши обновления для текущего состояния
            }
        }
    }

     fun onPassengerTap(index:Int,newPassenger: Passenger) {

        val state = _state.value
         if (!state.conductor.terminal) {return}
        val passengerIndex = state.passengers[index].indexOfFirst { it.id == newPassenger.id }
        if (passengerIndex == -1) return
        if (state.passengers[index][passengerIndex].ticket){
            _state.update { it.copy(score = state.score - 10) }
            _state.update {
                it.copy(
                    conductor = it.conductor.copy(money = state.conductor.money -33) // Устанавливаем terminal в true
                )
            }

        } else {
            state.passengers[index][passengerIndex] = newPassenger.copy(
                ticket = true
            )
            _state.update { it.copy(score = state.score + 10) }
            _state.update {
                it.copy(
                    conductor = it.conductor.copy(money = state.conductor.money +33) // Устанавливаем terminal в true
                )
            }
        }

    }




}


