package com.conductorsimulator.gamelogic

import android.content.res.Resources
import android.graphics.Point
import androidx.compose.runtime.Composable
import com.conductorsimulator.R
import com.conductorsimulator.gamelogic.entities.Passenger
import kotlin.random.Random

data class GameState (
    val score: Int = 0,
    val highScore: Int = 0,
    val lives: Int = 3,
    private val passengersPicture: Set<String> = setOf(),
    private val unusedPassengersPicture: Set<String> = setOf("1", "2", "3"),
    var passengers: List<Passenger> = emptyList(),
    val isOver: Boolean = false,
    val gameState: State = State.MENU
) {
    companion object {
        @Composable
        fun generatePassengers(count: Int): List<Passenger> {
            val random = Random(System.currentTimeMillis())
            val images = getPassengerImages().toMutableList() // Список доступных изображений
            val screenWidth = Resources.getSystem().displayMetrics.widthPixels
            val screenHeight = Resources.getSystem().displayMetrics.heightPixels
            val rowHeight = screenHeight * 0.2f

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
                val xOffset = (screenWidth / (count + 1)) * i
                val yOffset = when (layer) {
                    1 -> (screenHeight - rowHeight * 1.5f).toInt()
                    else -> (screenHeight - rowHeight * 2.5f).toInt()
                }

                // Уникальная картинка
                val imageRes = images.removeAt(random.nextInt(images.size))

                // Создаём пассажира
                Passenger(
                    id = i,
                    layer = layer,
                    location = 1,
                    point = Point(xOffset, yOffset),
                    ticket = random.nextBoolean(),
                    angryLVL = 0,
                    stations = random.nextInt(1, 10),
                    imageRes = imageRes
                )
            }
        }
    }
}
        fun getPassengerImages(): List<Int> {
    return listOf(
        R.drawable.passenger_1,
        R.drawable.passenger_2,
        R.drawable.passenger_3,
        R.drawable.passenger_4, // Добавьте все ваши ресурсы
        R.drawable.passenger_5,
        R.drawable.passenger_6
    )
}

enum class State {
    MENU,
    PLAY,
    PAUSED
}