package com.mertsy.javasample;

import android.app.Application;
import android.util.Log;

import com.mertsy.sdk.MertsySDK;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MertsySDK.init(this, "YOUR TOKEN HERE");
        MertsySDK.setOnTokenStatusChangedListener(tokenStatus -> {
            Log.e("Your SDK token status", tokenStatus.name());
        });
    }

}
