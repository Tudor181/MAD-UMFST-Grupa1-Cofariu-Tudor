package com.example.calculatorlab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculatorlab4.ui.theme.CalculatorLab4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorLab4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorComponent()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Column(
        modifier = Modifier.fillMaxWidth(),
//            .background(Color.Red),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello $name!",
            modifier = modifier,
            color = Color.Blue
        )
    }
}

@Composable
fun mySpacer(height:Boolean, value:Float = 8f){

    Spacer(modifier = if (height) Modifier.height(value.dp) else Modifier.width(value.dp))
}
@Composable
fun CalculatorComponent() {
    var number1 by remember { mutableStateOf("") }

    var number2 by remember { mutableStateOf("") }

    var result by remember {mutableStateOf(0)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = number1,
            label = { Text(text = "1 number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { value ->
                number1 = value
            }
        )
        mySpacer(true)
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = number2,
            label = { Text(text = "2 number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { value ->
                number2 = value
            }
        )

        mySpacer(true)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        )
        {
            Button(onClick = {
            result = (number1.toIntOrNull() ?: 0) + (number2.toIntOrNull() ?: 0)
            }) {
                Text("+", fontSize = 32.sp)
            }

            Button(onClick = {
                result = (number1.toIntOrNull() ?: 0) - (number2.toIntOrNull() ?: 0)
            }) {
                Text("-", fontSize = 32.sp)
            }
        }
        mySpacer(true)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                result = (number1.toIntOrNull() ?: 0) / (number2.toIntOrNull() ?: 1) }
            ) {
                Text("/", fontSize = 32.sp, color = Color.White)
            }
            Button(onClick = {
                result = (number1.toIntOrNull() ?: 0) * (number2.toIntOrNull() ?: 0)
            }) {
                Text("*", fontSize = 32.sp)
            }
        }

        mySpacer(true, 25f)
        Text(
            text = "Result $result",
            color = Color.Green,
            style = TextStyle(fontSize = 50.sp),
        )
    }
}

@Composable
fun CustomStyledButton(
    onClick: () -> Unit,
    text: String,
    backgroundColor: Color,
    textColor: Color
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(64.dp)
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Text(
            text = text,
            fontSize = 32.sp,
            color = textColor,
            modifier = Modifier
                .background(color = backgroundColor)
                .padding(8.dp)
        )
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculatorLab4Theme {
        Greeting("Android")
    }
}

