package com.example.lab5intentstest.service
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.lab5intentstest.R
import com.example.lab5intentstest.StopWatchActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StopwatchService : Service() {

    //Static objects
    companion object {
        const val ACTION_UPDATE_TIME = "com.example.lab5intentstest.action.UPDATE_TIME"
        const val EXTRA_ELAPSED_TIME = "com.example.lab5intentstest.extra.ELAPSED_TIME"
        const val NOTIFICATION_ID = 12345
        const val CHANNEL_ID = "stopwatch_channel"
        const val ACTION_STOP_SERVICE = "com.example.lab5intentstest.action.STOP_SERVICE"
    }

    private var running = false
    private var elapsedTime: Long = 0L
    private val binder = StopwatchBinder()

    inner class StopwatchBinder : Binder() {
        fun getService(): StopwatchService {
            return this@StopwatchService
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("StopwatchApp", "onStartCommand")
//        startForeground(NOTIFICATION_ID, createNotification())

        startStopwatch()
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }

     fun startStopwatch() {
        Log.d("StopwatchApp", "startStopWatch")

        running = true
        CoroutineScope(Dispatchers.Main).launch {
            while (running) {
//                Log.d("StopwatchApp", "while $elapsedTime")

                delay(1000)
                elapsedTime += 1000
                // Broadcast the elapsed time to the UI
                val intent = Intent(ACTION_UPDATE_TIME).apply {
                    putExtra(EXTRA_ELAPSED_TIME, elapsedTime)
                }
                sendBroadcast(intent)
            }
        }
    }

    fun stopStopwatch() {
        running = false
        stopSelf()
    }

    private fun createNotification(): Notification {
        createNotificationChannel()

        val notificationIntent = Intent(this, StopWatchActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val stopIntent = Intent(this, StopwatchService::class.java)
        stopIntent.action = ACTION_STOP_SERVICE
        val stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Stopwatch Service")
            .setContentText("Service is running")
            .setSmallIcon(R.drawable.spotify)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.yzf_r7, "Stop Service", stopPendingIntent)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Stopwatch Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }


}
