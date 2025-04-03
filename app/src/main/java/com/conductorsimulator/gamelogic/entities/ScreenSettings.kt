package com.conductorsimulator.gamelogic.entities

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.DisplayMetrics


object ScreenSettings {
    private var displayMetrics: DisplayMetrics = DisplayMetrics()

    val density: Float
        get() = displayMetrics.density

    val screenHeight: Int
        get() = displayMetrics.heightPixels/3

    val screenWidth: Int
        get() = displayMetrics.widthPixels/3

    fun initialize(application: Application) {
        displayMetrics = application.resources.displayMetrics
    }
}