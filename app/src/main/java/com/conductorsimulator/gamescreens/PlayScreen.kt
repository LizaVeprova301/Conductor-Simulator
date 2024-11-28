package com.conductorsimulator.gamescreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.conductorsimulator.gamelogic.GameEvent
import com.conductorsimulator.gamelogic.GameState
import com.conductorsimulator.gamelogic.entities.Passenger

import kotlin.random.Random


@Preview
@Composable
fun PlayScreenPreview() {
    val state = GameState()
    PlayScreen(GameState(), rememberNavController()) {}
}

@Composable
fun PlayScreen(
    state: GameState,
    navController: NavController,
    onEvent: (GameEvent) -> Unit,
) {
    if (state.passengers.isEmpty()) {
        state.passengers = GameState.generatePassengers(Random.nextInt(1,7)) // Например, генерируем 6 пассажиров
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "${state.score}",
                color = Color.Black,
                fontSize = 20.sp,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = { onEvent(GameEvent.PlusScore) }
            ) {
                Text(
                    text = "+1"
                )
            }

            Button(
                onClick = {
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
        // Вторая строка
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(LocalConfiguration.current.screenHeightDp.dp * 0.2f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically // Центрирование по вертикали
        ) {}

        // Третья строка
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(LocalConfiguration.current.screenHeightDp.dp * 0.2f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            state.passengers.filter { it.layer == 2 }.chunked(3).forEach { passengerGroup ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    passengerGroup.forEach { passenger ->
                        PassengerButton(passenger, onEvent, passenger.imageRes)
                    }
                }
            }
        }

        // Четвёртая строка
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(LocalConfiguration.current.screenHeightDp.dp * 0.2f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            state.passengers.filter { it.layer == 1 }.chunked(3).forEach { passengerGroup ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    passengerGroup.forEach { passenger ->
                        PassengerButton(passenger, onEvent, passenger.imageRes)
                    }
                }
            }
        }
    }
}

@Composable
fun PassengerButton(
    passenger: Passenger,
    onEvent: (GameEvent) -> Unit,
    imageRes: Int
) {
    Button(
        onClick = { onEvent(GameEvent.PlusScore) },
        modifier = Modifier
            .width(80.dp) // Устанавливаем ширину кнопки
            .height(150.dp) // Устанавливаем высоту кнопки
            .padding(4.dp), // Отступы между кнопками
        shape = RectangleShape // Прямоугольная форма кнопки
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Passenger ${passenger.id}",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Растянуть изображение под размер кнопки
        )
    }
}


