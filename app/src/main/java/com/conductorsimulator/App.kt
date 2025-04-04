package com.conductorsimulator

import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.InspectableModifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.conductorsimulator.gamelogic.GameViewModel
import com.conductorsimulator.gamelogic.entities.ScreenSettings.initialize
import com.conductorsimulator.gamescreens.EndScreen
import com.conductorsimulator.gamescreens.MenuScreen
import com.conductorsimulator.gamescreens.PauseScreen
import com.conductorsimulator.gamescreens.PlayScreen
import com.conductorsimulator.gamescreens.RoundScreen
import com.conductorsimulator.managers.DataStoreManager
import kotlinx.coroutines.delay

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
                navController = navController,
                viewModel = viewModel,
                state = state,
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
        composable("round") {
            RoundScreen (
                state = state,
                navController = navController,
            )
        }
        composable("end") {
            EndScreen (
                state = state,
                navController = navController,
            )
        }
    }
}
