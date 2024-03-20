package com.example.lab5intentstest.hooks

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.registerReceiver
import com.example.lab5intentstest.service.StopwatchService

@SuppressLint("UnspecifiedRegisterReceiverFlag")
@Composable
fun StopwatchBroadcastReceiver(
    onValueReceived: (Long) -> Unit,
//    onBatteryLow: () -> Unit
) {
    val context = LocalContext.current

    // Define the BroadcastReceiver
    val stopwatchReceiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.let {
                    if (it.action == StopwatchService.ACTION_UPDATE_TIME) {
                        val elapsedTime = it.getLongExtra(StopwatchService.EXTRA_ELAPSED_TIME, 0L)
                        onValueReceived(elapsedTime)
                    }
                }
            }
        }
    }

    // Register the BroadcastReceiver
    DisposableEffect(Unit) {
        val filter = IntentFilter().apply {
            addAction(StopwatchService.ACTION_UPDATE_TIME)
        }
        context.registerReceiver(stopwatchReceiver, filter)
//        registerReceiver(stopwatchReceiver, filter, RECEIVER_EXPORTED)
        
        // Unregister the BroadcastReceiver when the composable is disposed
        onDispose {
            context.unregisterReceiver(stopwatchReceiver)
        }
    }
}
