package com.mobile_project.calculator

import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class CalculatorModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    override fun getName() = "CalculatorModule"//js에서 호출할 모듈이름
    
    @ReactMethod
    fun add(a: Double, b: Double, promise: Promise) {
        try {
            val result = a + b
            promise.resolve(result)
        } catch (e: Exception) {
            promise.reject("ADD_ERROR", e)
        }
    }
}