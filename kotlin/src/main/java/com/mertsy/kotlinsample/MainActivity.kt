package com.mertsy.kotlinsample

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.mertsy.capturing.CapturingType
import com.mertsy.capturing.MertsyCapturing
import com.mertsy.capturing.ModelAccessType
import com.mertsy.common.TokenStatus
import com.mertsy.sdk.MertsySDK

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val Context.hasStoragePermission: Boolean
        get() = isPermissionGranted(
            targetStoragePermission
        )
    private val Context.hasCameraPermission get() = isPermissionGranted(Manifest.permission.CAMERA)

    private val targetStoragePermission
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else Manifest.permission.WRITE_EXTERNAL_STORAGE

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Required for capturing
        requestCameraAndStoragePermissions()

        findViewById<AppCompatTextView>(R.id.tvTokenStatus).text = "Token status: Checking"

        MertsySDK.onTokenStatusChangedCallback = { status: TokenStatus ->
            val prefix = "Token status: "
            findViewById<AppCompatTextView>(R.id.tvTokenStatus).text = when (status) {
                TokenStatus.SUCCESS -> prefix + "Success"
                else -> prefix + "Error"
            }
        }

        findViewById<AppCompatTextView>(R.id.tvFrameworkVersion).text =
            "SDK Version: ${MertsySDK.getVersion()}"

        with(findViewById<SwitchCompat>(R.id.switchCacheSession)) {
            isChecked = MertsyCapturing.getCacheSession()
            setOnCheckedChangeListener { _, isEnabled ->
                MertsyCapturing.setCacheSession(isEnabled)
            }
        }

        with(findViewById<SwitchCompat>(R.id.switchModelAccess)) {
            isChecked = MertsyCapturing.modelAccess == ModelAccessType.UNLISTED
            setOnCheckedChangeListener { _, isEnabled ->
                MertsyCapturing.modelAccess =
                    isEnabled then ModelAccessType.UNLISTED ?: ModelAccessType.PUBLIC
            }
        }

        findViewById<AppCompatButton>(R.id.btnPanorama).setOnClickListener {
            startCapturingActivity(CapturingType.PANORAMA)
        }

        findViewById<AppCompatButton>(R.id.btnIndoor).setOnClickListener {
            startCapturingActivity(CapturingType.INDOOR)
        }

        findViewById<AppCompatButton>(R.id.btnCars).setOnClickListener {
            startCapturingActivity(CapturingType.CARS_AND_VEHICLES)
        }

        findViewById<AppCompatButton>(R.id.btnView).setOnClickListener {
            checkTokenAndExecute {
                startActivity(Intent(this, ModelViewActivity::class.java))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        //Ask user to restore his session
        MertsyCapturing.session?.let { previousSession ->
            val llSessionPreview = findViewById<LinearLayout>(R.id.llPreviousSession)
            llSessionPreview.isVisible = true

            findViewById<AppCompatImageView>(R.id.ivPreviousSession).setImageBitmap(previousSession.preview)

            findViewById<AppCompatButton>(R.id.btnResume).setOnClickListener {
                //We will open the last session, so type is not matter
                startCapturingActivity(null)
            }

            findViewById<AppCompatButton>(R.id.btnDelete).setOnClickListener {
                MertsyCapturing.clearSessionCaches()
                llSessionPreview.isVisible = false
            }
        }
    }

    private fun checkTokenAndExecute(action: () -> Unit) {
        when (MertsySDK.tokenStatus) {
            TokenStatus.CHECKING -> {
                val msg = "Token validation. Try again later"
                Log.e(TAG, msg)
                showToast(msg)
            }

            TokenStatus.ERROR -> {
                val msg = "Wrong token"
                Log.e(TAG, msg)
                showToast(msg)
            }

            TokenStatus.SUCCESS -> action.invoke()
        }
    }

    private fun startCapturingActivity(type: CapturingType?) {
        checkTokenAndExecute {

            //<3 gb free
            if (!MertsyCapturing.hasEnoughMemory) {
                val msg = "Not enough memory, the work might be unstable"
                Log.e(TAG, msg)
                showToast(msg)
            }

            if (!MertsyCapturing.hasRequiredSensors) {
                val msg = "No gyroscope, the capturing is impossible"
                Log.e(TAG, msg)
                showToast(msg)
                return@checkTokenAndExecute
            }

            startActivity(
                Intent(this, CapturingActivity::class.java).apply {
                    type?.let { putExtra(CapturingActivity.TAG, it as Parcelable) }
                })
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
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}