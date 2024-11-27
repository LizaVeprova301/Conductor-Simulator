package com.conductorsimulator.gamelogic

import kotlin.random.Random

data class GameState (
    val score: Int = 0,
    val highScore: Int = 0,
    val lives: Int = 3,
    private val passengers: Set<String> = setOf(),
    private val unusedPassengers: Set<String> = setOf("1", "2", "3"),
    val isOver: Boolean = false,
    val gameState: State = State.MENU
) {
    companion object {
        fun generateRandPassenger(): Int {
            /* TODO */
            return Random.nextInt(from = 1, until = 3)
        }
    }
}

enum class State {
    MENU,
    PLAY,
    PAUSED
}