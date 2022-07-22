package com.mertsy.kotlinsample

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.mertsy.kotlinsample.capturing.CarsAndVehiclesActivity
import com.mertsy.kotlinsample.capturing.IndoorActivity
import com.mertsy.kotlinsample.capturing.PanoramaActivity
import com.mertsy.kotlinsample.view.ModelViewActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnClickListeners()
        checkPermissions()
    }

    private fun setOnClickListeners() {
        findViewById<View>(R.id.btnPanorama).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    PanoramaActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.btnCarsAndVehicles).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    CarsAndVehiclesActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.btnIndoor).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    IndoorActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.btnViewModel).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ModelViewActivity::class.java
                )
            )
        }
    }

    private fun checkPermissions() {
        val hasPermissions = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasPermissions) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 100)
        }
    }

}