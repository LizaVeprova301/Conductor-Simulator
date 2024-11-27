//package com.conductorsimulator.gamescreens
//
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material3.Button
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.conductorsimulator.gamelogic.GameState
//
//@Preview
//@Composable
//fun PlayScreenPreview(){
//    PlayScreen(rememberNavController(), GameState())
//}
//
//@Composable
//fun PlayScreen(navController: NavController, state: GameState) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//    ) {
//        Text(
//            text = "${state.score}",
//            color = Color.Black,
//            fontSize = 20.sp,
//            textAlign = TextAlign.Left,
//            fontWeight = FontWeight.Bold
//        )
//
//        Button(onClick = {state.score += 1; println("FUCK") }) { }
//
//        Button(
//            onClick = { navController.navigate("pause") }
//            )
//        {
//            Icon(
//                imageVector = Icons.Default.Menu,
//                contentDescription = null,
//                tint = Color(0xFF039be5),
//            )
//        }
//    }
//
//}
