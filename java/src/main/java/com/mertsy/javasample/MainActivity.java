package com.mertsy.javasample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.mertsy.javasample.capturing.CarsAndVehiclesActivity;
import com.mertsy.javasample.capturing.IndoorActivity;
import com.mertsy.javasample.capturing.PanoramaActivity;
import com.mertsy.javasample.view.ModelViewActivity;

public class MainActivity extends AppCompatActivity {

    private boolean hasStoragePermission() {
        return isPermissionGranted(targetStoragePermission());
    }

    private boolean hasCameraPermission() {
        return isPermissionGranted(Manifest.permission.CAMERA);
    }

    private String targetStoragePermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ? Manifest.permission.READ_MEDIA_IMAGES : Manifest.permission.WRITE_EXTERNAL_STORAGE;
    }

    private boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setOnClickListeners();
        requestCameraAndStoragePermissions();
    }

    private void setOnClickListeners() {
        findViewById(R.id.btnPanorama).setOnClickListener(view -> startActivity(new Intent(this, PanoramaActivity.class)));
        findViewById(R.id.btnCarsAndVehicles).setOnClickListener(view -> startActivity(new Intent(this, CarsAndVehiclesActivity.class)));
        findViewById(R.id.btnIndoor).setOnClickListener(view -> startActivity(new Intent(this, IndoorActivity.class)));
        findViewById(R.id.btnViewModel).setOnClickListener(view -> startActivity(new Intent(this, ModelViewActivity.class)));
    }

    private void requestCameraAndStoragePermissions() {
        String[] permissions = {targetStoragePermission(), Manifest.permission.CAMERA};
        if (!hasCameraPermission() || !hasStoragePermission()) {
            requestPermissions(permissions, 123);
        }
    }

}