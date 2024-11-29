package com.conductorsimulator.gamescreens

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    Image(
        painter = painterResource(id = R.drawable.play_bg),
        contentDescription = "play_bg",
        contentScale = ContentScale.FillHeight,
    )

    if (state.passengers.isEmpty()) {
        state.passengers =
            GameState.generatePassengers(Random.nextInt(1, 7)) // Например, генерируем 6 пассажиров
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp), // Отступ сверху
        horizontalAlignment = Alignment.End // Выравнивание по правому краю
    ) {
        Text(
            text = "SCORE: ${state.score}",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp)) // Разделитель между текстами

        Text(
            text = "MONEY: ${state.conductor.money}",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }




    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Верхний ряд пассажиров (Layer 2)
        state.passengers.filter { it.layer == 2 }.forEach { passenger ->
            PassengerButton(passenger, onEvent, passenger.imageRes)
        }

        // Нижний ряд пассажиров (Layer 1)
        state.passengers.filter { it.layer == 1 }.forEach { passenger ->
            PassengerButton(passenger, onEvent, passenger.imageRes)
        }
    }
    if (state.conductor.terminal) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter // Размещение содержимого по центру внизу
        ) {
            Image(
                painter = painterResource(id = R.drawable.terminal),
                contentDescription = "Terminal",
                modifier = Modifier.fillMaxWidth() // Увеличиваем ширину, если нужно
            )
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Отступ от края экрана
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = { onEvent(GameEvent.TerminalOn) }
        ) {
            Text(
                text = "Terminal"
            )
        }
    }
}


@Composable
fun PassengerButton(
    passenger: Passenger,
    onEvent: (GameEvent) -> Unit,
    imageRes: Int
) {
    val painter = painterResource(id = imageRes)
    val intrinsicSize = painter.intrinsicSize

    // Масштабируем изображение пропорционально
    val scaleFactor = 3.5f // Коэффициент уменьшения
    val width = (intrinsicSize.width / scaleFactor).toInt().toDp()
    val height = (intrinsicSize.height / scaleFactor).toInt().toDp()

    // Конвертация y-координаты в Dp
//    val yOffset = (passenger.point.y - (height / 2)).toInt().toDp()
//    println(
//        "Passenger ID: ${passenger.id}, Layer: ${passenger.layer}, " +
//                "X: ${passenger.point.x}, Y: ${yOffset}, Image: ${passenger.imageRes}"
//    )

    Image(
        painter = painter,
        contentDescription = "Passenger ${passenger.id}",
        modifier = Modifier
            .offset(
                x = passenger.point.x.toDp(),
                y = passenger.point.y.toDp()// Убедились, что здесь только Dp
            )
            .size(width, height) // Размеры в Dp
            .clickable(onClick = { onEvent(GameEvent.Payment) })
    )
}

@Composable
fun Int.toDp(): Dp {
    return with(LocalDensity.current) { this@toDp.toDp() }
}
