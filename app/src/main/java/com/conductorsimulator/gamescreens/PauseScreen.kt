package com.conductorsimulator.gamescreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            text = "PAUSE",
            color = Color.Cyan,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Button(
            onClick = {
                navController.navigate("menu")
                onEvent(GameEvent.KillGame)
            }
        ) {
            Text(
                text = "menu"
            )
        }
    }

}