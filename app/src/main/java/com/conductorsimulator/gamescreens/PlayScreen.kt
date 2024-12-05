package com.conductorsimulator.gamescreens
import android.content.res.Resources
import android.graphics.Point
import android.graphics.PointF
import androidx.annotation.Px
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset

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
            GameState.generatePassengers(Random.nextInt(3, 6)) // Например, генерируем 6 пассажиров
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
        println("Sorting passengers by layer and position...")
        // Сортируем пассажиров перед отрисовкой
        state.sortPassengersByLayerAndPosition()
        println("Passengers after sorting: ${state.passengers.map { "ID:${it.id}, Layer:${it.layer}, Pos:${it.point}" }}")

        // Отрисовываем пассажиров
        state.passengers.forEach { passenger ->
            PassengerButton(
                passenger = passenger,
                onEvent = onEvent,
                imageRes = passenger.imageRes,
                onDragEnd = { id, newLayer, newPosition ->
                    println("Passenger $id dragged to newLayer: $newLayer, newPosition: $newPosition")
                    val targetLayer = if (newLayer == 1) 1 else 2

                    state.movePassengerToFront(id, targetLayer, newPosition)
                    println("Passengers after move: ${state.passengers.map { "ID:${it.id}, Layer:${it.layer}, Pos:${it.point}" }}")

                }
            )
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
            onClick = {
                println("Terminal button clicked")
                onEvent(GameEvent.TerminalOn) }
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
    imageRes: Int,
    onDragEnd: (Int, Int, PointF) -> Unit // Передаем ID, новый слой и позицию
) {
    println("Rendering PassengerButton for passenger ID: ${passenger.id}, Layer: ${passenger.layer}, Pos: ${passenger.point}")
    val painter = painterResource(id = imageRes)
    val intrinsicSize = painter.intrinsicSize

    // Масштабируем изображение пропорционально
    val scaleFactor = 6f
    val width = (intrinsicSize.width / scaleFactor)
    val height = (intrinsicSize.height / scaleFactor)

    // Вспомогательное состояние для текущей позиции
    var offsetX by remember { mutableStateOf(passenger.point.x) }
    var offsetY by remember { mutableStateOf(passenger.point.y) }


    Image(
        painter = painter,
        contentDescription = "Passenger ${passenger.id}",
        modifier = Modifier
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
            .size(width.dp, height.dp) // Размер изображения
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                        println("Dragging Passenger ${passenger.id}: offsetX=$offsetX, offsetY=$offsetY")
                    },
                    onDragEnd = {
                        val screenHeight = Resources.getSystem().displayMetrics.heightPixels
                        // Границы для определения слоев
                        val layerBoundary = (screenHeight*0.5f)
                        val newLayer = if (offsetY > layerBoundary) 1 else 2 // Определяем слой
                        println("Passenger ${passenger.id} Layer=$newLayer, PosY=$offsetY , layerBoundary=$layerBoundary")
                        // Корректируем позицию по Y в зависимости от слоя
                        offsetY = if (newLayer == 1) screenHeight * 0.7f - height
                        else screenHeight * 0.65f - height

                        val newPosition = PointF(offsetX, offsetY)
                        println("Passenger ${passenger.id} drag ended at: Layer=$newLayer, Pos=$newPosition")

                        // Сообщаем об изменении позиции пассажира
                        onDragEnd(passenger.id, newLayer, newPosition)
                    }
                )
            }
            .clickable(onClick = {
                println("Passenger ${passenger.id} clicked for payment, Position ${passenger.point.y}")
                onEvent(GameEvent.Payment) })
    )
}



@Composable
fun Int.toDp(): Dp {
    return with(LocalDensity.current) { this@toDp.toDp() }
}

@Composable
fun Dp.toPx(): Float {
    return with(LocalDensity.current) { this@toPx.toPx() }
}



