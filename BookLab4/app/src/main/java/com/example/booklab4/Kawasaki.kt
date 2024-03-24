package com.example.booklab4

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Kawasaki(navController: NavController, selectedState: MutableState<Int>){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Kawasaki 650",
            fontSize = 32.sp,
            fontFamily = FontFamily.Monospace
        )
        Image(
            painter = painterResource(id = R.drawable.kawa_photo),
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Text(
            text = stringResource(id = R.string.kawaDesc),
            modifier = Modifier.padding(16.dp),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Monospace
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(onClick = {
                selectedState.value++
                navController.navigate("HondaPage")
                }
            )
            {
                Text("Next")
            }
        }
    }
}
