package com.example.lab5intentstest

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.lab5intentstest.ui.theme.Lab5IntentsTestTheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab5IntentsTestTheme {
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
    val REQUEST_CODE_MOTO_ACTIVITY = 1

    // In SecondActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_MOTO_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                // The MotoActivity has finished with the desired result
                finish() // Finish the SecondActivity
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

    var test = remember {
        mutableStateOf(1)
    }
    val ctx = LocalContext.current

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
//        mySpacer(height = true, 200f)
//        Button(
//            onClick = {
//                (ctx as Activity).finish()
//            },
//        ) {
////            Text(
////                text = "Finish this activity, go back to the main one",
////                color = Color.Black,
////                style = TextStyle(fontSize = 20.sp),
////            )
//            Image(
//                painter = painterResource(id = R.drawable.home),
//                contentDescription = null,
//                modifier = Modifier.size(40.dp),
//            )
//        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Other composable elements

            Button(
                onClick = {
                    (ctx as Activity).finish()
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                )
            }
            Button(
                onClick = {
//                    val pack = "com.example.booklab4"
//                    val intent = ctx.packageManager.getLaunchIntentForPackage(pack)
//                    try {
//                        if (intent != null) {
//                            ContextCompat.startActivity(ctx, intent, null)
//                        }
//                    } catch (e: ActivityNotFoundException) {
//                        e.printStackTrace()
//                    }
//                    Intent(Intent.ACTION_VIEW, Uri.parse("example://open")).also {
//                        it.`package` = pack
//
//                        try {
//                            ContextCompat.startActivity(ctx, it, null)
//                        } catch (e: ActivityNotFoundException) {
//                            e.printStackTrace()
//                        }
//                    }
                    val intentToMoto = Intent(ctx, MotoActivity::class.java)
                    ctx.startActivity(intentToMoto)

                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable._200px_circle_icons_motorcycle_svg),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                )
            }
        }

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


