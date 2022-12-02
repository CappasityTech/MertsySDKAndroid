package com.mertsy.kotlinsample

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mertsy.kotlinsample.capturing.CarsAndVehiclesActivity
import com.mertsy.kotlinsample.capturing.IndoorActivity
import com.mertsy.kotlinsample.capturing.PanoramaActivity
import com.mertsy.kotlinsample.view.ModelViewActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val Context.hasStoragePermission: Boolean get() = isPermissionGranted(targetStoragePermission)
    private val Context.hasCameraPermission get() = isPermissionGranted(Manifest.permission.CAMERA)

    private val targetStoragePermission: String
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else Manifest.permission.WRITE_EXTERNAL_STORAGE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnClickListeners()
        requestCameraAndStoragePermissions()
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

    private fun requestCameraAndStoragePermissions() {
        val permissions = arrayOf(
            targetStoragePermission,
            Manifest.permission.CAMERA
        )
        if (!hasStoragePermission || !hasCameraPermission)
            requestPermissions(permissions, 123)
    }

    private fun Context.isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }
}