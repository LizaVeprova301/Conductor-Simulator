//package com.conductorsimulator.trash
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.offset
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.scale
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.conductorsimulator.R
//
//
//@Preview
//@Composable
//fun MenuScreenPreview(){
//    MenuScreen(rememberNavController(), 0)
//}
//
//@Composable
//fun MenuScreen(navController: NavController, highScore: Int?) {
//    Image(
//        painter = painterResource(id = R.drawable.menu_bg),
//        contentDescription = "menu_bg",
//        contentScale = ContentScale.FillHeight,
//    )
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//        modifier = Modifier
//            .offset(y =150.dp)
//            .fillMaxSize()
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.play_bt),
//            contentDescription = "play_button",
//            alignment = Alignment.Center,
//            modifier = Modifier
//                .clickable( onClick = { navController.navigate("game") })
//                .scale(0.5f)
//
//        )
//
//        highScore?.let {
//            Text(
//                text = "HIGH SCORE:\n${highScore.toString()}",
//                color = Color.Black,
//                fontSize = 30.sp,
//                textAlign = TextAlign.Center,
//                fontWeight = FontWeight.Bold
//            )
//
//        }
//    }
//}