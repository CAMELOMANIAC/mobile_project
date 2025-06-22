package com.mobile_project.alarm

import android.content.Context
import android.app.AlarmManager
import android.content.Intent
import android.app.PendingIntent
import android.provider.Settings
import android.net.Uri
import android.icu.util.Calendar
import android.util.Log

import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class AlarmModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    override fun getName() = "AlarmModule"//js에서 호출할 모듈이름
    
    @ReactMethod
    fun startCron(hour: Int, minute: Int, promise: Promise) {
        try {
            Log.d("AlarmModule", "startCron 실행됨")
            val context = reactApplicationContext
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            if (!alarmManager.canScheduleExactAlarms()) {

                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                    data = Uri.parse("package:${context.packageName}")
                }
                context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }

            // 알람이 울릴 때 실행할 리시버 지정
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0, // 여러 알람 구분 시 requestCode 변경
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // 지정 시간 계산
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                // 이미 지난 시간이면 다음 날로 설정
                if (timeInMillis <= System.currentTimeMillis()) {
                    add(Calendar.DAY_OF_MONTH, 1)
                }
            }
            // 정확한 시간에 알람 예약 (도즈모드 대응)
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )

            promise.resolve(true) // 성공
        } catch (e: Exception) {
            promise.reject("ALARM_ERROR", e) // 실패
        }
    }
}