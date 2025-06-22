package com.mobile_project

import android.content.Intent
import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import android.app.NotificationManager
import android.app.NotificationChannel
import android.app.Notification
import android.util.Log
import com.facebook.react.HeadlessJsTaskService
import com.facebook.react.jstasks.HeadlessJsTaskConfig
import com.facebook.react.bridge.Arguments
import androidx.core.app.ServiceCompat

class HeadlessService : HeadlessJsTaskService() {
    override fun getTaskConfig(intent: Intent?): HeadlessJsTaskConfig? {
        
        val extras = intent?.extras
        return if (extras != null) {
            Log.d("AlarmModule", "헤드리스 JS 실행됨")
            HeadlessJsTaskConfig(
                "SomeTaskName", // JS에서 등록한 태스크명
                Arguments.fromBundle(extras),
                5000, // 타임아웃(ms)
                false // 포그라운드 실행 허용 여부
            )
        } else {
            null
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "headless_service_channel"
            val channelName = "Headless Service"
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
            notificationManager.createNotificationChannel(channel)
            val notification: Notification = Notification.Builder(this, channelId)
                .setContentTitle("Headless Service")
                .setContentText("Running...")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .build()
            // 여기서 서비스 타입 명시!
            ServiceCompat.startForeground(
                this,
                1,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC // 또는 목적에 맞는 타입
            )
        }
        return super.onStartCommand(intent, flags, startId)
    }

}
