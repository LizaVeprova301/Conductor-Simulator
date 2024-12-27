package com.conductorsimulator.gamescreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.conductorsimulator.R
import com.conductorsimulator.gamelogic.GameEvent
import com.conductorsimulator.gamelogic.GameState

@Preview
@Composable
fun PauseScreenPreview() {
    PauseScreen(GameState(), rememberNavController()) { }
}

@Composable
fun PauseScreen(
    state: GameState,
    navController: NavController,
    onEvent: (GameEvent) -> Unit
) {
    Image(
        painter = painterResource(id = R.drawable.pause_bg),
        contentDescription = "pause_bg",
        contentScale = ContentScale.FillHeight,
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .offset(y = 145.dp)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.resumebutton),
            contentDescription = "resume_button",
            alignment = Alignment.Center,
            modifier = Modifier
                .clickable(onClick = {
                    navController.navigate("play")
                })
                .scale(0.9f)
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .offset(y = 290.dp)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.menubutton),
            contentDescription = "menubutton",
            alignment = Alignment.Center,
            modifier = Modifier
                .clickable(onClick = {
                    onEvent(GameEvent.KillGame)
                    navController.navigate("menu")
                })
                .scale(0.9f)
        )
    }

}