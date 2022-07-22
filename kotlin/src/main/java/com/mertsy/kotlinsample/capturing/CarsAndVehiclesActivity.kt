package com.mertsy.kotlinsample.capturing

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mertsy.capturing.CapturingResultListener
import com.mertsy.capturing.MertsyCapturingView
import com.mertsy.kotlinsample.ClipboardUtil
import com.mertsy.kotlinsample.R

class CarsAndVehiclesActivity : AppCompatActivity(R.layout.activity_cars_and_vehicles) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(findViewById<MertsyCapturingView>(R.id.carsAndVehicles)) {
            setBackPressedDispatcher(onBackPressedDispatcher)
            setFragmentManager(supportFragmentManager)
            setLifecycleOwner(this@CarsAndVehiclesActivity)
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