package com.conductorsimulator.gamescreens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.conductorsimulator.R
import com.conductorsimulator.gamelogic.GameState
import kotlinx.coroutines.delay


@SuppressLint("SuspiciousIndentation")
@Composable
fun EndScreen(
    state: GameState,
    navController: NavController,
) {
    state.isOver = true
    var newtimer by remember { mutableIntStateOf(5) }
    LaunchedEffect(newtimer) {
        delay(1000)
        if (newtimer == 0) {
            navController.navigate("menu")
        } else {
            newtimer -= 1
        }
    }
    var bg_screen = R.drawable.you_lose
        if (state.conductor.money>0){
            bg_screen = R.drawable.you_won
        }
    Image(
            painter = painterResource(id =bg_screen),
            contentDescription = "end_bg",
            contentScale = ContentScale.FillHeight,
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${state.score}",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 100.dp) // Сдвиг текста вправо
        )
    }
}


