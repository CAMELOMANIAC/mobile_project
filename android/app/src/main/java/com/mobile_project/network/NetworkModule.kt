package com.mobile_project.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class NetworkModule(private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    override fun getName() = "NetworkModule"//js에서 호출할 모듈이름
    
    @ReactMethod
    fun isWifiConnected(promise: Promise) {
        val connectivityManager = reactContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return promise.reject("NO_NETWORK", "No active network")
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return promise.reject("NO_CAPABILITIES", "No network capabilities")
        if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            promise.resolve(true)
        } else {
            promise.resolve(false)
        }
    }
}