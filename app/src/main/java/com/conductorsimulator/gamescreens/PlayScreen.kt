package com.conductorsimulator.gamescreens


import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.conductorsimulator.R
import com.conductorsimulator.gamelogic.GameEvent
import com.conductorsimulator.gamelogic.GameState
import com.conductorsimulator.gamelogic.GameViewModel
import com.conductorsimulator.gamescreens.speedvagon.SpeedvagonPart
import kotlinx.coroutines.delay


@Composable
fun PlayScreen(
    state: GameState,
    viewModel: GameViewModel,
    navController: NavController,
    onEvent: (GameEvent) -> Unit,
) {
    var timer by remember { mutableIntStateOf(state.timer) }

    LaunchedEffect(timer) {
        println(timer)
        println(state.round)
        delay(1000)
        if (state.round == 10) {
            navController.navigate("end")
        } else {
            timer -= 1
            state.timer = timer
        }
        if (timer % 10 == 0) {
            state.ChangeRound()
            navController.navigate("round")
//        if (state.round % 5 ==0){
//            state.passengers[1].removeIf { it.stations == 0 }
//            state.passengers[2].removeIf { it.stations == 0 }
//            for ( i in 1..10 - state.passengers[1].size){
//                state.passengers[1].add( element = state.addPassenger())
//            }
//
//            for (i in 1 .. state.passengers[1].count { it.stations == 0 })
////            state.addPassenger()
//        }

            """Тут нужно переключиться на сцену оставновки
                        navController.navigate("menu") и обратно """

        }
    }

    state.GenerateParts(2)

    Image(
        painter = painterResource(id = R.drawable.play_bg),
        contentDescription = "play_bg",
        contentScale = ContentScale.FillHeight,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = "SCORE: ${state.score}",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "MONEY: ${state.conductor.money}",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "TIMER: ${state.timer}",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }

    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .fillMaxHeight()
    ) {

        SpeedvagonPart(
            index = 0,
            state = state,
            viewModel = viewModel,
            onEvent = onEvent

        )

        SpeedvagonPart(
            index = 1,
            state = state,
            viewModel = viewModel,
            onEvent = onEvent
        )

    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Button(
            onClick = {
                println("Pause button clicked")
                navController.navigate("paused")
                onEvent(GameEvent.PauseGame)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                tint = Color(0xFF039be5),
            )
        }
    }


    if (state.conductor.terminal) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.terminal),
                contentDescription = "Terminal",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = {
                println("Terminal button clicked")
                onEvent(GameEvent.TerminalOn)
            }
        ) {
            Text(
                text = "Terminal"
            )
        }
    }
}

@Composable
fun Float.toDp(): Dp {
    return (this / LocalDensity.current.density).dp
}






