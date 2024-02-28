package com.example.counterapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.counterapp.ui.theme.CounterAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CounterAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TheCounter()
                }
            }
        }
    }
}

@Composable
fun TheCounter(){
    var number by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            modifier = Modifier.padding(16.dp),
            text = "$number",
            fontSize = 100.sp,
            color = Color.White
        )
        Row {
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = { number++}
            ) {
                Text(text = "Increment")
            }
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = { number-- }
            ) {
                Text(text = "Decrement")
            }
        }
        Button(onClick = {
            number = 0
            Toast.makeText(context, "The counter is now set to 0", Toast.LENGTH_SHORT).show()
        }) {
            Text(text = "Reset")
        }
    }
}

@Composable
@Preview (showBackground = true)
fun CounterAppPreview(){
    TheCounter()
}