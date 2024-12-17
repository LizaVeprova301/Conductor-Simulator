package com.conductorsimulator.gamescreens


import android.graphics.PointF
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.conductorsimulator.R
import com.conductorsimulator.gamelogic.GameEvent
import com.conductorsimulator.gamelogic.GameState
import com.conductorsimulator.gamelogic.entities.Passenger
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.zIndex
import kotlin.random.Random


@Preview
@Composable
fun PlayScreenPreview() {
    PlayScreen(GameState(), rememberNavController()) {}
}

@Composable
fun PlayScreen(
    state: GameState,
    navController: NavController,
    onEvent: (GameEvent) -> Unit,

    ) {
    println("Initializing PlayScreen with state: ${state.passengers.map { "ID:${it.id}, Layer:${it.layer}, Pos:${it.point}" }}")

    Image(
        painter = painterResource(id = R.drawable.play_bg),
        contentDescription = "play_bg",
        contentScale = ContentScale.FillHeight,
    )

    if (state.passengers.isEmpty()) {
        println("Generating passengers...")
        state.passengers =
            state.generatePassengers(Random.nextInt(3, 4))

        println("Generated passengers: ${state.passengers}")
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Button(
            onClick = {
                println("Pause button clicked")
                navController.navigate("paused")
                onEvent(GameEvent.PauseGame)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                tint = Color(0xFF039be5),
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = "SCORE: ${state.score}",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "MONEY: ${state.conductor.money}",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }

    state.passengers.forEach { passenger ->


        PassengerImage(
            passenger = passenger,
            newZindex = { newLayer ->
                println("я здесь")

                var newZindex = 0f
                if (newLayer == 1) {
                    if (state.passengers.filter { it.layer == 1 }
                            .filter { it.id != passenger.id }.isEmpty()) {
                        newZindex = 20f
                    } else {
                        newZindex = state.passengers.filter { it.layer == 1 }
                            .filter { it.id != passenger.id }.maxBy { it.zindex }
                            .zindex + 1f
                    }

                } else if (state.passengers.filter { it.layer == 2 }
                        .filter { it.id != passenger.id }.isEmpty()) {
                    newZindex = 10f
                } else {
                    newZindex = state.passengers.filter { it.layer == 2 }
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
                state.updatePassenger(newPassenger)
            }
        )
    }





    if (state.conductor.terminal) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.terminal),
                contentDescription = "Terminal",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = {
                println("Terminal button clicked")
                onEvent(GameEvent.TerminalOn)
            }
        ) {
            Text(
                text = "Terminal"
            )
        }
    }
}


@Composable
fun PassengerImage(
    passenger: Passenger,
    newZindex: (Int) -> Float,
    newLayer: (Offset) -> Int,
    layerOffset: (Int) -> Float,
    stateUpdatePassenger: (Passenger) -> Unit
) {
    val density = LocalDensity.current.density
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenWidth = LocalConfiguration.current.screenWidthDp

    var offset by remember { mutableStateOf(Offset(passenger.point.x, passenger.point.y)) }
    var zindex by remember { mutableFloatStateOf(passenger.zindex) }
    var layer by remember { mutableIntStateOf(passenger.layer) }

    Image(
        painter = painterResource(id = passenger.imageRes),
        contentDescription = "Passenger ${passenger.id}",
        modifier = Modifier
            .size(passenger.size.width.toDp(), passenger.size.height.toDp())
            .offset(
                x = with(density) {
                    offset.x
                        .coerceIn(0f, screenWidth.toPx() - passenger.size.width)
                        .toDp()
                },
                y = with(density) {
                    offset.y
                        .coerceIn(
                            0f,
                            screenHeight.toPx() - passenger.size.height
                        )
                        .toDp()
                }
            )
            .zIndex(zIndex = zindex)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        layer = newLayer(offset)
                        zindex = newZindex(layer)
                        passenger.point = PointF(offset.x, offset.y)
                        passenger.zindex = zindex
                        passenger.layer = layer
                        offset = Offset(offset.x, layerOffset(layer))
                        stateUpdatePassenger(passenger)


                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        // Обновляем смещение
                        offset = Offset(offset.x + dragAmount.x, offset.y + dragAmount.y)
                        layer = newLayer(offset)
                        passenger.point = PointF(offset.x, offset.y)
                        passenger.zindex = zindex
                        passenger.layer = layer
                        stateUpdatePassenger(passenger)
                    }
                )

            }

            .clickable {
                println("layer $layer,zindex  $zindex")
                println(passenger)
            }
    )
}

@Composable
fun Float.toDp(): Dp {
    return (this / LocalDensity.current.density).dp
}

@Composable
fun Int.toPx(): Float {
    return this * LocalDensity.current.density
}





