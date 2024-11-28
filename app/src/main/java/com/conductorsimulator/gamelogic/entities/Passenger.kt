package com.conductorsimulator.gamelogic.entities

import android.graphics.Point

data class Passenger(
    val id: Int, // Уникальный идентификатор
    val layer: Int, // Слой, на котором находится пассажир
    val location: Int, // Локация в игре
    val point: Point, // Координаты пассажира
    val ticket: Boolean, // Есть ли у пассажира билет
    val angryLVL: Int, // Уровень недовольства
    val stations: Int, // Количество станций, которые пассажир должен проехать
    val imageRes: Int// Ресурс изображения
)