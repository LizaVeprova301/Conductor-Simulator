package com.conductorsimulator.gamelogic

import android.content.res.Configuration
import android.graphics.PointF
import android.util.Size
import android.util.SizeF
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.conductorsimulator.R
import com.conductorsimulator.gamelogic.entities.Conductor
import com.conductorsimulator.gamelogic.entities.Passenger
import com.conductorsimulator.gamelogic.entities.ScreenSettings
import com.conductorsimulator.gamelogic.entitiesaction.PassengerFactory
import com.conductorsimulator.gamelogic.entitiesaction.PassengerFactory.createPassenger
import kotlin.random.Random

data class GameState(
    val score: Int = 0,
    val highScore: Int = 0,
    val images: MutableList<Int> = getPassengerImages().toMutableList(),
    var passengers: MutableList<MutableList<Passenger>> = mutableStateListOf(),
    val conductor: Conductor = Conductor(),
    var isOver: Boolean = false,
    val gameState: State = State.MENU,
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
        return (1..count).map { i ->
            createPassenger(i)
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
        passengerIndex: Int,
        stations: Int,
        layerBoundaries:  List<Float>)
    :Passenger{
        val random = Random(System.currentTimeMillis())
        if (images.isEmpty()) throw IllegalStateException("Not enough unique images for passengers!")

        val layer = Random.nextInt(1, 3)


        // Рассчитываем координаты пассажира
        val imageRes = images.removeAt(random.nextInt(images.size))
        val painter = painterResource(id = imageRes)
        val intrinsicSize = painter.intrinsicSize


        val scaleFactor = 5f // Коэффициент уменьшения
        val passengerWidth = (intrinsicSize.width / scaleFactor)
        val passengerHeight = (intrinsicSize.height / scaleFactor)
        val minX = (passengerWidth / 2)
        val maxX = (ScreenSettings.screenWidth*3 - passengerWidth / 2)


        val xOffset =
            minX + (random.nextFloat() * (maxX - minX))

        val yOffset = when (layer) {
            1 -> (layerBoundaries.get(0) - passengerHeight)
            else -> (layerBoundaries.get(1) - passengerHeight)
        }
        val zindex = when (layer) {
            1 -> (20 + passengerIndex)
            else -> (10 + passengerIndex)
        }

        // Создаём пассажира
        val passenger = Passenger(
            id = passengerIndex,
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


    @Composable
    fun Float.toPx(): Float {
        return this * LocalDensity.current.density
    }

    fun сhangeRound() {
        this.round+=1
        for (i in 0..<this.passengers.size) {
            this.passengers[i].forEach { passenger ->
                if (passenger.stations>0){
                    passenger.stations -=1
                }
            }
        }
    }


    @Composable
    fun generateNewPass(index: Int) {
        val randomNewPassCount =  Random.nextInt(getPassengerImages().toMutableList().size, getPassengerImages().toMutableList().size + passengers[index].count { it.stations == 0 })
        for (i in 0 .. randomNewPassCount){
            passengers[index].add(createPassenger(passengers[index].maxBy { it.id }.id +1))
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