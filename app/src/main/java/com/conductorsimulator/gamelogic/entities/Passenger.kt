package com.conductorsimulator.gamelogic.entities


import android.graphics.PointF
import android.util.SizeF

data class Passenger(
    val id: Int,
    var layer: Int,
    val location: Int,
    var point: PointF,
    val ticket: Boolean,
    val angryLVL: Int,
    val stations: Int,
    val imageRes: Int,
    val size: SizeF,
    var zindex: Float
)