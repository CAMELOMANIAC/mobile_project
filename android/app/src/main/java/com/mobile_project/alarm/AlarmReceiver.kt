package com.mobile_project.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Build
import android.util.Log
import com.mobile_project.HeadlessService

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Headless JS 서비스 실행
        val serviceIntent = Intent(context, HeadlessService::class.java)
        Log.d("AlarmReceiver", "알람 수신됨")
        val bundle = Bundle()
        bundle.putString("foo", "bar") // JS에 전달할 데이터
        serviceIntent.putExtras(bundle)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
    }
}