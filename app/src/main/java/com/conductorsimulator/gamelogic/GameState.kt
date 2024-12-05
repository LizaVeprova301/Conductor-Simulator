package com.conductorsimulator.gamelogic

import android.content.res.Resources
import android.graphics.Point
import android.graphics.PointF
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.conductorsimulator.R
import com.conductorsimulator.gamelogic.entities.Conductor
import com.conductorsimulator.gamelogic.entities.Passenger
import kotlin.random.Random

data class GameState(
    val score: Int = 0,
    val highScore: Int = 0,
    val lives: Int = 3,
    private val passengersPicture: Set<String> = setOf(),
    private val unusedPassengersPicture: Set<String> = setOf("1", "2", "3"),
    val conductor: Conductor = Conductor(),
    val isOver: Boolean = false,
    val gameState: State = State.MENU
) {
    // Начальная инициализация пассажиров
    private val initialPassengers: List<Passenger> = emptyList()

    // Теперь passengers отслеживается Compose
    var passengers by mutableStateOf(initialPassengers)
    companion object {
        @Composable
        fun generatePassengers(count: Int): List<Passenger> {
            val random = Random(System.currentTimeMillis())
            val images = getPassengerImages().toMutableList() // Список доступных изображений
            val screenWidth = Resources.getSystem().displayMetrics.widthPixels
            val screenHeight = Resources.getSystem().displayMetrics.heightPixels

            // Лимиты на количество пассажиров в каждом слое
            val layerLimits = mutableMapOf(1 to 3, 2 to 3)
            val layerCounts = mutableMapOf(1 to 0, 2 to 0) // Текущее количество пассажиров в слоях

            return (1..count).map { i ->
                if (images.isEmpty()) throw IllegalStateException("Not enough unique images for passengers!")

                // Определяем слой с учётом ограничений
                val availableLayers =
                    layerLimits.filter { (layer, limit) -> layerCounts[layer]!! < limit }.keys
                if (availableLayers.isEmpty()) throw IllegalStateException("No layers available for more passengers!")

                val layer = availableLayers.random() // Выбираем случайный доступный слой
                layerCounts[layer] = layerCounts[layer]!! + 1 // Увеличиваем счётчик для слоя

                // Рассчитываем координаты пассажира
                val imageRes = images.removeAt(random.nextInt(images.size)) // Уникальная картинка
                val painter = painterResource(id = imageRes) // Для получения размеров изображения
                val intrinsicSize = painter.intrinsicSize

                val scaleFactor = 5f // Коэффициент уменьшения
                val passengerWidth = (intrinsicSize.width / scaleFactor)
                val passengerHeight = (intrinsicSize.height / scaleFactor)
                val minX = (passengerWidth / 2)// Левая граница
                val maxX = (screenWidth - passengerWidth / 2)// Правая граница

                val xOffset = minX + (random.nextFloat() * (maxX - minX))// Случайное значение с учётом размеров
                val yOffset = when (layer) {
                    1 -> (screenHeight * 0.35f - passengerHeight)
                    else -> (screenHeight * 0.3f - passengerHeight)
                }

                // Создаём пассажира
                val passenger = Passenger(
                    id = i,
                    layer = layer,
                    location = 1,
                    point = PointF(xOffset, yOffset),
                    ticket = false,
                    angryLVL = 0,
                    stations = random.nextInt(1, 10),
                    imageRes = imageRes
                )
                passenger
            }
        }
    }
    fun sortPassengersByLayerAndPosition() {
        passengers = passengers.sortedWith(compareByDescending<Passenger> { it.layer }
            .thenByDescending { it.point.y }) // Сортируем, не удаляя объекты
    }





    fun movePassengerToFront(id: Int, newLayer: Int, newPosition: PointF) {
        // Находим пассажира по ID
        val passengerIndex = passengers.indexOfFirst { it.id == id }
        if (passengerIndex == -1) return // Если пассажир не найден, выходим

        val updatedPassenger = passengers[passengerIndex].copy( // Создаём копию с обновлёнными параметрами
            layer = newLayer,
            point = newPosition
        )

        passengers = passengers.toMutableList().apply {
            this[passengerIndex] = updatedPassenger // Обновляем только нужного пассажира
        }

        println("Updated passengers list: ${passengers.map { it.toString() }}")
        sortPassengersByLayerAndPosition()
    }





}




fun getPassengerImages(): List<Int> {
    return listOf(
        R.drawable.passenger_1,
        R.drawable.passenger_2,
        R.drawable.passenger_3,
        R.drawable.passenger_4, // Добавьте все ваши ресурсы
        R.drawable.passenger_5,
        R.drawable.passenger_6,
        R.drawable.passenger_7,
        R.drawable.passenger_8
    )


}


enum class State {
    MENU,
    PLAY,
    PAUSED
}