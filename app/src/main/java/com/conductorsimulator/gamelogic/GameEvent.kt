package com.conductorsimulator.gamelogic

sealed class GameEvent {
    data object StartGame: GameEvent()
    data object PauseGame: GameEvent()
    data object KillGame: GameEvent()
    data object PlusScore: GameEvent()
    data object TerminalOn: GameEvent()
    data object Payment: GameEvent()
    data object Rabbit: GameEvent()

}