package com.mertsy.kotlinsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mertsy.capturing.CapturingType
import com.mertsy.capturing.MertsyCapturingView

class CapturingActivity: AppCompatActivity(R.layout.activity_capturing) {

    companion object {
        const val TAG = "CapturingActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type: CapturingType? = intent.getParcelableExtra(TAG)

        with(findViewById<MertsyCapturingView>(R.id.capturingView)) {

            //Or inside xml xml app:mertsyCapturingType
            type?.let { setCapturingType(it) }

            setLifecycleOwner(this@CapturingActivity)

            setFragmentManager(supportFragmentManager)

            setBackPressedDispatcher(onBackPressedDispatcher)

            setCapturingResultCallback(onComplete = { modelId: String ->
                context.copyToClipboard(modelId)
                showToast("Copied\n$modelId")
                finish()
            }, onCancel = ::finish)

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