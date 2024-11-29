package com.conductorsimulator.gamelogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class GameViewModel() : ViewModel() {
    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()


    fun onEvent(event: GameEvent) {

        when (event) {
            GameEvent.StartGame -> {
                _state.update { updateGame(it, reset = true) }
                _state.update { it.copy(gameState = State.PLAY) }

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
                            conductor = it.conductor.copy(terminal = false) // Устанавливаем terminal в true
                        )
                    } else {
                        it.copy(
                            conductor = it.conductor.copy(terminal = true) // Устанавливаем terminal в true
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
}
