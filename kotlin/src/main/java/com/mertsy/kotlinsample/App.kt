package com.mertsy.kotlinsample

import android.app.Application
import android.util.Log
import com.mertsy.sdk.MertsySDK

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MertsySDK.init(this, "YOUR TOKEN HERE")
        MertsySDK.setOnTokenStatusChangedListener { status ->
            Log.e("Your SDK token status", status.name)
        }
    }

}