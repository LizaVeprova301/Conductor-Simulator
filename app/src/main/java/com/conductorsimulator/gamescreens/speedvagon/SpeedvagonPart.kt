package com.conductorsimulator.gamescreens.speedvagon

import androidx.compose.runtime.Composable
import com.conductorsimulator.gamelogic.GameEvent
import com.conductorsimulator.gamelogic.GameState
import com.conductorsimulator.gamelogic.GameViewModel
import kotlin.random.Random

@Composable
fun SpeedvagonPart(
    index: Int,
    state: GameState,
    viewModel: GameViewModel,
    onEvent: (GameEvent) -> Unit,
) {
//    println("Initializing PlayScreen with state: ${state.passengers[index].map { "ID:${it.id}, Layer:${it.layer}, Pos:${it.point}" }}")

    if (state.passengers[index].isEmpty()) {
        println("Generating passengers...")
        state.passengers[index] =
            state.generatePart(Random.nextInt(3, 4))

        println("Generated passengers: ${state.passengers[index]}")
    }

    val passengers = state.passengers[index];


    passengers.forEach { passenger ->
        if (passenger.stations ==0){
            if (!passenger.ticket){
                onEvent(GameEvent.Rabbit)
                passenger.ticket = true
            }
        }
        PassengerImage(
            index = index,
            passenger = passenger,
            newZindex = { newLayer ->


                var newZindex = 0f
                if (newLayer == 1) {
                    if (passengers.filter { it.layer == 1 }
                            .filter { it.id != passenger.id }.isEmpty()) {
                        newZindex = 20f
                    } else {
                        newZindex = passengers.filter { it.layer == 1 }
                            .filter { it.id != passenger.id }.maxBy { it.zindex }
                            .zindex + 1f
                    }

                } else if (passengers.filter { it.layer == 2 }
                        .filter { it.id != passenger.id }.isEmpty()) {
                    newZindex = 10f
                } else {
                    newZindex = passengers.filter { it.layer == 2 }
                        .filter { it.id != passenger.id }
                        .maxBy { it.zindex }
                        .zindex + 1f
                }

                newZindex

            },
            newLayer = { offset ->
                val layerBoundary = state.screenSize.height * 1.3f
                val newLayer = if (offset.y > layerBoundary) 1 else 2

                newLayer
            },
            layerOffset = { layer ->
                val layerBoundary1 = state.screenSize.height * 1.9f
                val layerBoundary2 = state.screenSize.height * 1.8f
                val yOffset = when (layer) {
                    1 -> (layerBoundary1 - passenger.size.height)
                    else -> (layerBoundary2 - passenger.size.height)
                }
                yOffset

            },
            stateUpdatePassenger = { newPassenger ->
                state.updatePassenger(index, newPassenger)
            },
            onPassengerTap = { index, passenger ->
                viewModel.onPassengerTap(index, passenger)
            },

        )
    }
}
