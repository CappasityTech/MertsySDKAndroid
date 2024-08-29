package com.mertsy.javasample;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import com.mertsy.capturing.CapturingType;
import com.mertsy.capturing.MertsyCapturing;
import com.mertsy.capturing.MertsyCapturingSession;
import com.mertsy.capturing.ModelAccessType;
import com.mertsy.common.TokenStatus;
import com.mertsy.sdk.MertsySDK;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private boolean hasStoragePermission() {
        return isPermissionGranted(targetStoragePermission());
    }

    private boolean hasCameraPermission() {
        return isPermissionGranted(Manifest.permission.CAMERA);
    }

    private String targetStoragePermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ?
                Manifest.permission.READ_MEDIA_IMAGES :
                Manifest.permission.WRITE_EXTERNAL_STORAGE;
    }

    private boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(
                this,
                permission
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isTokenValid() {

        switch (MertsySDK.getTokenStatus()) {
            case CHECKING: {
                String msg = "Token validation. Try again later";
                Log.e(TAG, msg);
                showToast(msg);
                return false;
            }
            case ERROR: {
                String msg = "Wrong token";
                Log.e(TAG, msg);
                showToast(msg);
                return false;
            }
            default:
                return true;
        }
    }

    @Override
    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatTextView tvTokenStatus = findViewById(R.id.tvTokenStatus);
        AppCompatTextView tvFrameworkVersion = findViewById(R.id.tvFrameworkVersion);

        SwitchCompat switchCacheSession = findViewById(R.id.switchCacheSession);
        SwitchCompat switchModelAccess = findViewById(R.id.switchModelAccess);

        AppCompatButton btnPanorama = findViewById(R.id.btnPanorama);
        AppCompatButton btnIndoor = findViewById(R.id.btnIndoor);
        AppCompatButton btnCars = findViewById(R.id.btnCars);
        AppCompatButton btnView = findViewById(R.id.btnView);

        //Required for capturing
        requestCameraAndStoragePermissions();

        tvTokenStatus.setText("Token status: Checking");

        MertsySDK.setOnTokenStatusChangedListener(tokenStatus -> {
            String status = "Token status: ";
            if (tokenStatus == TokenStatus.SUCCESS) {
                status += "Success";
            } else {
                status += "Error";
            }

            tvTokenStatus.setText(status);
        });

        tvFrameworkVersion.setText("SDK Version: " + MertsySDK.getVersion());

        switchCacheSession.setChecked(MertsyCapturing.getCacheSession());
        switchCacheSession.setOnCheckedChangeListener((buttonView, isChecked) -> MertsyCapturing.setCacheSession(isChecked));

        switchModelAccess.setChecked(MertsyCapturing.getModelAccess() == ModelAccessType.UNLISTED);
        switchModelAccess.setOnCheckedChangeListener((buttonView, isChecked) -> MertsyCapturing.setModelAccess(isChecked ? ModelAccessType.UNLISTED : ModelAccessType.PUBLIC));

        btnIndoor.setOnClickListener(v -> startCapturingActivity(CapturingType.INDOOR));
        btnCars.setOnClickListener(v -> startCapturingActivity(CapturingType.CARS_AND_VEHICLES));
        btnPanorama.setOnClickListener(v -> startCapturingActivity(CapturingType.PANORAMA));

        btnView.setOnClickListener(v -> {
            if (isTokenValid()) {
                startActivity(new Intent(this, ModelViewActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //Ask user to restore his session
        MertsyCapturingSession session = MertsyCapturing.getSession();
        if (session != null) {
            LinearLayout llPreviousSession = findViewById(R.id.llPreviousSession);
            AppCompatImageView ivPreviousSession = findViewById(R.id.ivPreviousSession);
            AppCompatButton btnResume = findViewById(R.id.btnResume);
            AppCompatButton btnDelete = findViewById(R.id.btnDelete);

            llPreviousSession.setVisibility(View.VISIBLE);
            ivPreviousSession.setImageBitmap(session.getPreview());

            //We will open the last session, so type is not matter
            btnResume.setOnClickListener(v -> startCapturingActivity(null));

            btnDelete.setOnClickListener(v -> {
                MertsyCapturing.clearSessionCaches();
                llPreviousSession.setVisibility(View.GONE);
            });
        }

    }

    private void startCapturingActivity(@Nullable CapturingType type) {
        if (!isTokenValid())
            return;

        //<3 gb free
        if (!MertsyCapturing.getHasEnoughMemory()) {
            String msg = "Not enough memory, the work might be unstable";
            Log.e(TAG, msg);
            showToast(msg);
        }

        if (!MertsyCapturing.getHasRequiredSensors()) {
            String msg = "No gyroscope, the capturing is impossible";
            Log.e(TAG, msg);
            showToast(msg);
            return;
        }

        Intent capturingIntent = new Intent(this, CapturingActivity.class);
        if (type != null) {
            capturingIntent.putExtra(CapturingActivity.TAG, (Parcelable) type);
        }

        startActivity(capturingIntent);
    }

    private void requestCameraAndStoragePermissions() {
        String[] permissions = new String[]{Manifest.permission.CAMERA, targetStoragePermission()};

        if (!hasStoragePermission() || !hasCameraPermission())
            requestPermissions(permissions, 123);
    }
}