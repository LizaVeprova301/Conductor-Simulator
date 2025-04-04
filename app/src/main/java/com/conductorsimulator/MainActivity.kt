package com.conductorsimulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.conductorsimulator.gamelogic.entities.ScreenSettings.initialize
import com.conductorsimulator.managers.DataStoreManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize(application)
        enableEdgeToEdge()
        setContent {
            val dataStoreManager = DataStoreManager(this)
            App(dataStoreManager)
        }
    }
}