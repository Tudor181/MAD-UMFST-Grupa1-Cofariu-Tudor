package com.example.lab5intentstest

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.lab5intentstest.ui.theme.Lab5IntentsTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab5IntentsTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppWithIntents(context = this@MainActivity)
                }
            }
        }
    }
}

@Composable
fun AppWithIntents(context: Context){
    var number by remember { mutableStateOf("") }
    val ctx = LocalContext.current
    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)) {

        Button(onClick = {
            val intentToCalculator = Intent(ctx, SecondActivity::class.java)

            ctx.startActivity(intentToCalculator)
        })
            {
                    Text(text = "CALCULATOR ACTIVITY")
                }
            OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = number,
            label = { Text(text = "number to dial") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { value ->
                number = value
            }
        )

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 15.dp)
        ) {
            Button(onClick = {
                Intent(Intent.ACTION_MAIN).also {
//                it.`package`="de.dvse.nextesa"
                    it.`package` = "com.google.android.dialer"

                    try {
                        print("test")
                        startActivity(context, it, null)
                    } catch (e: ActivityNotFoundException) {
                        e.printStackTrace()
                    }
                }
            }) {
                Image(
                    painter = painterResource(id = R.drawable.dialer),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),

                    )
            }
            Button(onClick = {
                Intent(Intent.ACTION_VIEW).also {
                    val spotify = "https://open.spotify.com"
                    it.data = Uri.parse(spotify)
                    try {
                        print("test")
                        startActivity(context, it, null)
                    } catch (e: ActivityNotFoundException) {
                        e.printStackTrace()
                    }
                }
            }) {
                Image(
                    painter = painterResource(id = R.drawable.spotify),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                )
            }
            Button(onClick = {
                Intent(Intent.ACTION_DIAL).also {
//                it.`package`="com.google.android.youtube"
                    it.data = Uri.parse("tel:$number")
                    try {
                        context.startActivity(it, null)
                    } catch (e: ActivityNotFoundException) {
                        e.printStackTrace()
                    }
                }
            }) {
                Image(
                    painter = painterResource(id = R.drawable.github),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),

                    )
            }
        }
    }
}