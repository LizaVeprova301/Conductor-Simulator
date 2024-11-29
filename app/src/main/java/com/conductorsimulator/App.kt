package com.conductorsimulator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.conductorsimulator.gamelogic.GameViewModel
import com.conductorsimulator.gamescreens.MenuScreen
import com.conductorsimulator.gamescreens.PauseScreen
import com.conductorsimulator.gamescreens.PlayScreen
import com.conductorsimulator.managers.DataStoreManager

@Composable
fun App(dataStoreManager: DataStoreManager) {
    val viewModel = viewModel<GameViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "menu"
    ) {
        composable("menu") {
            MenuScreen(
                state = state,
                dataStoreManager = dataStoreManager,
                navController = navController,
                onEvent = viewModel::onEvent
            )
        }
        composable("play") {
            PlayScreen (
                state = state,
                navController = navController,
                onEvent = viewModel::onEvent
            )
        }
        composable("paused") {
            PauseScreen (
                state = state,
                navController = navController,
                onEvent = viewModel::onEvent
            )
        }
    }
}
