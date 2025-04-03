package com.conductorsimulator.gamelogic.entitiesaction

import android.graphics.PointF
import android.util.SizeF
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.conductorsimulator.R
import com.conductorsimulator.gamelogic.entities.Passenger
import com.conductorsimulator.gamelogic.entities.ScreenSettings
import com.conductorsimulator.gamelogic.getPassengerImages
import kotlin.random.Random

object PassengerFactory {
    @Composable
    fun createPassenger(id:Int): Passenger {
        val id = id
        val layer = createLayer()
        val location = 1
        val ticket = false
        val angryLVL = 0
        val stations = createCountStations(8)
        val imageRes = createImageRes()
        val size = createSize(imageRes)
        val point = createPoint(size, layer)
        val zindex = createZindex(layer, id)

        return Passenger(id, layer, location, point, ticket, angryLVL, stations, imageRes, size, zindex)
    }

    private fun createPoint(size: SizeF,layer:Int): PointF {
        val layerBoundaries = listOf(ScreenSettings.screenHeight * 1.9f, ScreenSettings.screenHeight * 1.8f)
        println("СЛОИ ${layerBoundaries[0]} ${layerBoundaries[1]} ${ScreenSettings.screenHeight}")
        val minX = (size.width / 2)
        val maxX = (ScreenSettings.screenWidth*3 - size.width / 2)
        val xOffset =
            minX + (Random.nextFloat() * (maxX - minX))
        val yOffset = when (layer) {
            1 -> (layerBoundaries[0] - size.height)
            else -> (layerBoundaries[1] - size.height)
        }
        return PointF(xOffset,yOffset)
    }


    @Composable
    private fun createSize(imageRes: Int): SizeF {
        val painter = painterResource(id = imageRes)
        val intrinsicSize = painter.intrinsicSize
        val scaleFactor = 5f // Коэффициент уменьшения
        val passengerWidth = (intrinsicSize.width / scaleFactor)
        val passengerHeight = (intrinsicSize.height / scaleFactor)
        return SizeF(passengerWidth,passengerHeight)
    }
    private fun createImageRes(): Int {
        val images = getPassengerImages().toMutableList()
        val imageRes = images.removeAt(Random.nextInt(images.size))
        return imageRes
    }

    private fun createLayer(): Int {
        return Random.nextInt(1, 3)
    }


    private fun createCountStations(max: Int): Int {
        return Random.nextInt(1, max)
    }

    private fun createZindex(layer:Int,passengerIndex:Int): Float {
        val zindex = when (layer) {
            1 -> (20 + passengerIndex)
            else -> (10 + passengerIndex)
        }
        return zindex.toFloat()
    }
//    private fun createId(passengers: List<Passenger>): Int {
//        val id = passengers.maxBy { it.id }.id + 1
//        return id
//    }


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
}