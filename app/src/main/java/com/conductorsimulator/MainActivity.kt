package com.conductorsimulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.conductorsimulator.gamelogic.GameViewModel
import com.conductorsimulator.gamescreens.MenuScreen
import com.conductorsimulator.gamescreens.PauseScreen
import com.conductorsimulator.gamescreens.PlayScreen
import com.conductorsimulator.ui.theme.ConductorSimulatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConductorSimulatorTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "menu"
                ) {
                    val data = getSharedPreferences("data", MODE_PRIVATE)
                    composable("menu") {
                        val viewModel = viewModel<GameViewModel>()
                        val state by viewModel.state.collectAsStateWithLifecycle()
                        MenuScreen(
                            state = state,
                            navController = navController,
                            onEvent = viewModel::onEvent
                        )
                    }
                    composable("play") {
                        val viewModel = viewModel<GameViewModel>()
                        val state by viewModel.state.collectAsStateWithLifecycle()
                        PlayScreen (
                            state = state,
                            navController = navController,
                            onEvent = viewModel::onEvent
                        )
                    }
                    composable("paused") {
                        val viewModel = viewModel<GameViewModel>()
                        val state by viewModel.state.collectAsStateWithLifecycle()
                        PauseScreen (
                            state = state,
                            navController = navController,
                            onEvent = viewModel::onEvent
                        )
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
    }
}



