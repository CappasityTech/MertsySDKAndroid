package com.mertsy.kotlinsample.capturing

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mertsy.kotlinsample.R
import com.mertsy.capturing.CapturingResultListener
import com.mertsy.capturing.MertsyCapturingView
import com.mertsy.kotlinsample.ClipboardUtil

class IndoorActivity: AppCompatActivity(R.layout.activity_indoor) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(findViewById<MertsyCapturingView>(R.id.indoor)) {
            setBackPressedDispatcher(onBackPressedDispatcher)
            setFragmentManager(supportFragmentManager)
            setLifecycleOwner(this@IndoorActivity)
            setCapturingResultListener(object : CapturingResultListener {
                override fun onComplete(modelId: String) {
                    ClipboardUtil(applicationContext).copy(modelId)
                    Toast.makeText(
                        applicationContext,
                        "Copied to clipboard\n$modelId",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }

                override fun onCancel() {
                    finish()
                }
            })
            /*
            *
            * OR
            *
            * setCapturingResultCallback(onComplete = {}, onCancel = {})
            *
            * */
        }

    }

}