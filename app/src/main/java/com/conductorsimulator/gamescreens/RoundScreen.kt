package com.conductorsimulator.gamescreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.conductorsimulator.R
import com.conductorsimulator.gamelogic.GameState
import kotlinx.coroutines.delay


@Composable
fun RoundScreen(
    state: GameState,
    navController: NavController,
) {
    var newtimer by remember { mutableIntStateOf(5) }
    LaunchedEffect(newtimer) {
        delay(1000)
        if (newtimer == 0) {
            navController.navigate("play")
        } else {
            newtimer -= 1
        }
    }

    Image(
        painter = painterResource(id = R.drawable.station),
        contentDescription = "station_bg",
        contentScale = ContentScale.FillHeight,
    )

    Column(
        modifier = Modifier
            .fillMaxSize() // Заполняет доступное пространство
            .wrapContentSize(Alignment.Center), // Центрирует содержимое
        verticalArrangement = Arrangement.Center, // Вертикальное центрирование
        horizontalAlignment = Alignment.CenterHorizontally // Горизонтальное центрирование
    ) {
        Text(
            text = "Round: ${state.round}",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


