package com.conductorsimulator.gamelogic

import android.content.res.Configuration
import android.graphics.PointF
import android.provider.MediaStore.Images
import android.util.Size
import android.util.SizeF
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
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
    var passengers: MutableList<MutableList<Passenger>> = mutableStateListOf(),
    val conductor: Conductor = Conductor(),
    var isOver: Boolean = false,
    val gameState: State = State.MENU,
    var screenSize: Size = Size(0, 0),
    var density: Float = 0f,
    var timer: Int = 100,
    var round: Int = 0
) {
    @Composable
    fun GenerateParts(amount: Int) {
        for (i in 0..amount) {
            passengers.add(ArrayList());
        }
    }

    @Composable
    fun generatePart(count: Int): MutableList<Passenger> {
        val configuration = LocalConfiguration.current
        val density = LocalDensity.current.density
        setScreenSizeAndDensity(configuration, density)



        val images = getPassengerImages().toMutableList()
        
        // Лимиты на количество пассажиров в каждом слое
        val layerLimits = mutableMapOf(1 to 3, 2 to 3)
        val layerCounts = mutableMapOf(1 to 0, 2 to 0)



        return (1..count).map { i ->
            addPassenger(images,layerLimits,layerCounts,i,7)
//            if (images.isEmpty()) throw IllegalStateException("Not enough unique images for passengers!")
//
//            // Определяем слой с учётом ограничений
//            val availableLayers =
//                layerLimits.filter { (layer, limit) -> layerCounts[layer]!! < limit }.keys
//            if (availableLayers.isEmpty()) throw IllegalStateException("No layers available for more passengers!")
//
//            val layer = availableLayers.random()
//            layerCounts[layer] = layerCounts[layer]!! + 1
//
//            // Рассчитываем координаты пассажира
//            val imageRes = images.removeAt(random.nextInt(images.size))
//            val painter = painterResource(id = imageRes)
//            val intrinsicSize = painter.intrinsicSize
//
//
//            val scaleFactor = 5f // Коэффициент уменьшения
//            val passengerWidth = (intrinsicSize.width / scaleFactor)
//            val passengerHeight = (intrinsicSize.height / scaleFactor)
//            val minX = (passengerWidth / 2)
//            val maxX = (screenWidth - passengerWidth / 2)
//
//            val xOffset =
//                minX + (random.nextFloat() * (maxX - minX))
//            val layerBoundary1 = screenHeight * 1.9f
//            val layerBoundary2 = screenHeight * 1.8f
//            val yOffset = when (layer) {
//                1 -> (layerBoundary1 - passengerHeight)
//                else -> (layerBoundary2 - passengerHeight)
//            }
//            val zindex = when (layer) {
//                1 -> (20 + i)
//                else -> (10 + i)
//            }
//
//            // Создаём пассажира
//            val passenger = Passenger(
//                id = i,
//                layer = layer,
//                location = 1,
//                point = PointF(xOffset, yOffset),
//                ticket = false,
//                angryLVL = 0,
//                stations = random.nextInt(5, 10),
//                imageRes = imageRes,
//                size = SizeF(passengerWidth, passengerHeight),
//                zindex = zindex.toFloat()
//            )
//            passenger
        }.toMutableList()
    }


    fun updatePassenger(
        index: Int,
        newPassenger: Passenger
    ) {
        val passengerIndex = passengers[index].indexOfFirst { it.id == newPassenger.id }
        if (passengerIndex == -1) return
        passengers[index][passengerIndex] = newPassenger.copy(
            layer = newPassenger.layer,
            point = newPassenger.point,
            zindex = newPassenger.zindex
        )
    }

    @Composable
    fun addPassenger(
        images:  MutableList<Int>,
        layerLimits:  MutableMap<Int, Int>,
        layerCounts: MutableMap<Int, Int>,
        passengerindex: Int,
        stations: Int)
    :Passenger{
        val random = Random(System.currentTimeMillis())
        if (images.isEmpty()) throw IllegalStateException("Not enough unique images for passengers!")

        // Определяем слой с учётом ограничений
        val availableLayers =
            layerLimits.filter { (layer, limit) -> layerCounts[layer]!! < limit }.keys
        if (availableLayers.isEmpty()) throw IllegalStateException("No layers available for more passengers!")

        val layer = availableLayers.random()
        layerCounts[layer] = layerCounts[layer]!! + 1

        // Рассчитываем координаты пассажира
        val imageRes = images.removeAt(random.nextInt(images.size))
        val painter = painterResource(id = imageRes)
        val intrinsicSize = painter.intrinsicSize


        val scaleFactor = 5f // Коэффициент уменьшения
        val passengerWidth = (intrinsicSize.width / scaleFactor)
        val passengerHeight = (intrinsicSize.height / scaleFactor)
        val minX = (passengerWidth / 2)
        val maxX = (this.screenSize.width - passengerWidth / 2)

        val xOffset =
            minX + (random.nextFloat() * (maxX - minX))
        val layerBoundary1 = this.screenSize.height * 1.9f
        val layerBoundary2 = this.screenSize.height * 1.8f
        val yOffset = when (layer) {
            1 -> (layerBoundary1 - passengerHeight)
            else -> (layerBoundary2 - passengerHeight)
        }
        val zindex = when (layer) {
            1 -> (20 + passengerindex)
            else -> (10 + passengerindex)
        }

        // Создаём пассажира
        val passenger = Passenger(
            id = passengerindex,
            layer = layer,
            location = 1,
            point = PointF(xOffset, yOffset),
            ticket = false,
            angryLVL = 0,
            stations = random.nextInt(1, stations),
            imageRes = imageRes,
            size = SizeF(passengerWidth, passengerHeight),
            zindex = zindex.toFloat()
        )
        return passenger
    }


    fun setScreenSizeAndDensity(configuration: Configuration, density: Float) {
        val screenHeight = configuration.screenHeightDp
        val screenWidth = configuration.screenWidthDp
        this.density = density

        this.screenSize = Size(screenWidth, screenHeight)
    }

    @Composable
    fun Float.toPx(): Float {
        return this * LocalDensity.current.density
    }

    fun ChangeRound() {
        this.round+=1
        for (i in 0.. this.passengers.size-1) {
            this.passengers[i].forEach { passenger ->
                if (passenger.stations==0){
                    return
                }
                passenger.stations -=1

            }
        }
    }


}


fun getPassengerImages(): List<Int> {
    return listOf(
        R.drawable.passenger_1,
        R.drawable.passenger_2,
        R.drawable.passenger_3,
        R.drawable.passenger_4,
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