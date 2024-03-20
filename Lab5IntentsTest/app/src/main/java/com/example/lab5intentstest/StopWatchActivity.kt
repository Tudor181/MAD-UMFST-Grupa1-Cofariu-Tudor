package com.example.lab5intentstest

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.text.format.DateUtils.formatElapsedTime
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab5intentstest.hooks.StopwatchBroadcastReceiver
import com.example.lab5intentstest.service.StopwatchService
import com.example.lab5intentstest.ui.theme.Lab5IntentsTestTheme

class StopWatchActivity : ComponentActivity() {

    private var stopwatchService: StopwatchService? = null
    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as StopwatchService.StopwatchBinder
            stopwatchService = binder.getService()
            isBound = true
            Log.d("StopwatchApp", "Service connected")

        }

        override fun onServiceDisconnected(name: ComponentName?) {
            stopwatchService = null
            isBound = false
            Log.d("StopwatchApp", "Service disconnected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindService(Intent(this, StopwatchService::class.java), connection, BIND_AUTO_CREATE)
        startService(Intent(this,StopwatchService::class.java))
        setContent {
            Lab5IntentsTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StopwatchApp()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }

    private var elapsedTime by mutableStateOf(0L)

//    @SuppressLint("UnspecifiedRegisterReceiverFlag")
//    @android.support.annotation.RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Composable
    fun StopwatchApp() {
        val context = LocalContext.current
        Log.d("StopwatchApp", "Elapsed time on UI: $elapsedTime")

        DisposableEffect(Unit) {
            val intentFilter = IntentFilter(StopwatchService.ACTION_UPDATE_TIME)
            registerReceiver(receiver, intentFilter, RECEIVER_EXPORTED)
//
            onDispose {
                context.unregisterReceiver(receiver)
            }
        }

        var elap by remember {
            mutableLongStateOf(0L)
        }

//    StopwatchBroadcastReceiver(onValueReceived = { value -> elap = value})



        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Stopwatch", modifier = Modifier.padding(16.dp), fontSize = 32.sp)
            Text(text = formatElapsedTime(elapsedTime), modifier = Modifier.padding(16.dp))
            Button(
                onClick = {
                    if (isBound) {
                        stopwatchService?.stopStopwatch()
                        isBound=false
                    }else{
                        stopwatchService?.startStopwatch()
                        isBound=true
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Stop")
            }
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("StopwatchApp", "on receive called")

            intent?.let {
                if (it.action == StopwatchService.ACTION_UPDATE_TIME) {
                    // Update the elapsedTime MutableState variable
                    elapsedTime = it.getLongExtra(StopwatchService.EXTRA_ELAPSED_TIME, 0L)
                    Log.d("StopwatchApp", "Received broadcast: elapsedTime=$elapsedTime")

                }
            }
        }
    }

}
