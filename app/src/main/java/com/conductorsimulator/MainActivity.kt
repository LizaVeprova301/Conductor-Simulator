package com.conductorsimulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.conductorsimulator.gamelogic.GameViewModel
import com.conductorsimulator.gamescreens.MenuScreen
import com.conductorsimulator.gamescreens.PauseScreen
import com.conductorsimulator.gamescreens.PlayScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val viewModel: GameViewModel = viewModel()

            NavHost(navController = navController, startDestination = "menu") {
                composable("menu") {
                    MenuScreen(
                        state = viewModel.state.collectAsStateWithLifecycle().value,
                        navController = navController,
                        onEvent = viewModel::onEvent
                    )
                }
                composable("play") {
                    PlayScreen(
                        state = viewModel.state.collectAsStateWithLifecycle().value,
                        navController = navController,
                        onEvent = viewModel::onEvent
                    )
                }
                composable("paused") {
                    PauseScreen(
                        state = viewModel.state.collectAsStateWithLifecycle().value,
                        navController = navController,
                        onEvent = viewModel::onEvent
                    )
                }
            }
        }

//                val data = getSharedPreferences("data", MODE_PRIVATE)
//                var state = GameState()
//
//                val navController = rememberNavController()
//                NavHost(
//                    navController = navController,
//                    startDestination = "menu"
//                ) {
//                    composable("menu") {
//                        val newHighScore = state.score
//                        val prevHighScore = data.getInt("highScore", 0)
//                        state = GameState()
//
//                        MenuScreen(
//                            navController,
//                            if (newHighScore > prevHighScore) newHighScore
//                            else prevHighScore
//                        )
//                    }
//                    composable("game") { PlayScreen(navController, state) }
//                    composable("pause") { PauseScreen(navController, state) }


    }
}



