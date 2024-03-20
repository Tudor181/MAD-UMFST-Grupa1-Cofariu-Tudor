package com.example.lab5intentstest.hooks

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun BatteryBroadcastReceiver(
    onBatteryLevelChanged: (Int) -> Unit,
    onBatteryLow: () -> Unit
) {
    val context = LocalContext.current

    // Define the BroadcastReceiver
    val batteryReceiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.let {
                    when (it.action) {
                        Intent.ACTION_BATTERY_CHANGED -> {
                            val level: Int = it.getIntExtra("level", -1)
                            onBatteryLevelChanged(level)
                        }
                        Intent.ACTION_BATTERY_LOW -> {
                            onBatteryLow()
                        }
                    }
                }
            }
        }
    }

    // Register the BroadcastReceiver
    DisposableEffect(Unit) {
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_CHANGED)
            addAction(Intent.ACTION_BATTERY_LOW)
        }
        context.registerReceiver(batteryReceiver, filter)

        // Unregister the BroadcastReceiver when the composable is disposed
        onDispose {
            context.unregisterReceiver(batteryReceiver)
        }
    }
}
