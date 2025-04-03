package com.conductorsimulator.gamescreens.speedvagon

import android.graphics.PointF
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import com.conductorsimulator.gamelogic.entities.Passenger
import com.conductorsimulator.gamescreens.toDp

@Composable
fun PassengerImage(
    index:Int,
    passenger: Passenger,
    newZindex: (Int) -> Float,
    newLayer: (Offset) -> Int,
    layerOffset: (Int) -> Float,
    stateUpdatePassenger: (Passenger) -> Unit,
    onPassengerTap: (Int,Passenger) -> Unit,
) {

    var offset by remember { mutableStateOf(Offset(passenger.point.x, passenger.point.y)) }
    var zindex by remember { mutableFloatStateOf(passenger.zindex) }
    var layer by remember { mutableIntStateOf(passenger.layer) }
    var alpha by remember { mutableFloatStateOf(1f) }
    var clickable by remember { mutableStateOf(true) }
    if (passenger.stations==0){
        alpha = 0f
        clickable = false
    }


    Image(
        painter = painterResource(id = passenger.imageRes),
        contentDescription = "Passenger ${passenger.id}",
        modifier = Modifier
            .size(passenger.size.width.toDp(), passenger.size.height.toDp())
            .alpha(alpha)
            .offset(
                x = offset.x.toDp(),
                y = offset.y.toDp()

            )
            .zIndex(zIndex = zindex)
            .pointerInput(Unit) {
                if (passenger.stations > 0) {
                    detectDragGestures(
                        onDragEnd = {
                            layer = newLayer(offset)
                            zindex = newZindex(layer)
                            offset = Offset(offset.x, layerOffset(layer))
                            passenger.point = PointF(offset.x, offset.y)
                            passenger.zindex = zindex
                            passenger.layer = layer
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
            }

            .clickable(enabled = clickable)  {
                onPassengerTap(index,passenger)
                println("layer $layer,zindex  $zindex")
                println(passenger)
            }
    )
}