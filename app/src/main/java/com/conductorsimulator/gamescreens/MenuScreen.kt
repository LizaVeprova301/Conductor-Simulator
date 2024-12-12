package com.conductorsimulator.gamescreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.conductorsimulator.gamelogic.GameEvent
import com.conductorsimulator.gamelogic.GameState
import com.conductorsimulator.R

@Preview
@Composable
fun MenuScreenPreview() {
    MenuScreen(GameState(), rememberNavController()) { }
}


@Composable
fun MenuScreen(
    state: GameState,
    navController: NavController,
    onEvent: (GameEvent) -> Unit
) {
    Image(
        painter = painterResource(id = R.drawable.menu_bg),
        contentDescription = "menu_bg",
        contentScale = ContentScale.FillHeight,
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .offset(x = (-150).dp, y = 160.dp)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.play_bt),
            contentDescription = "play_button",
            alignment = Alignment.Center,
            modifier = Modifier
                .offset(x = 200.dp, y = (-185).dp)
                .clickable(onClick = {
                    onEvent(GameEvent.StartGame)
                    navController.navigate("play")

                })
                .scale(0.5f)

        )

        Text(
            text = "${state.highScore}",
            color = Color.Cyan,
            fontSize = 50.sp,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold
        )

    }
}

