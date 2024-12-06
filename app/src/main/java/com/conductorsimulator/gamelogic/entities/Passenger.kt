package com.conductorsimulator.gamelogic.entities


import android.graphics.PointF
import android.util.Size
import android.util.SizeF

import androidx.compose.ui.unit.DpSize

data class Passenger(
    val id: Int, // Уникальный идентификатор
    var layer: Int, // Слой, на котором находится пассажир
    val location: Int, // Локация в игре
    var point: PointF, // Координаты пассажира
    val ticket: Boolean, // Есть ли у пассажира билет
    val angryLVL: Int, // Уровень недовольства
    val stations: Int, // Количество станций, которые пассажир должен проехать
    val imageRes: Int,// Ресурс изображения
    val size: SizeF
)