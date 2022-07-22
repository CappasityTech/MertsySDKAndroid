package com.mertsy.javasample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.mertsy.javasample.capturing.CarsAndVehiclesActivity;
import com.mertsy.javasample.capturing.IndoorActivity;
import com.mertsy.javasample.capturing.PanoramaActivity;
import com.mertsy.javasample.view.ModelViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setOnClickListeners();
        checkPermissions();
    }

    private void setOnClickListeners() {
        findViewById(R.id.btnPanorama).setOnClickListener(view -> {
            startActivity(new Intent(this, PanoramaActivity.class));
        });
        findViewById(R.id.btnCarsAndVehicles).setOnClickListener(view -> {
            startActivity(new Intent(this, CarsAndVehiclesActivity.class));
        });
        findViewById(R.id.btnIndoor).setOnClickListener(view -> {
            startActivity(new Intent(this, IndoorActivity.class));
        });
        findViewById(R.id.btnViewModel).setOnClickListener(view -> {
            startActivity(new Intent(this, ModelViewActivity.class));
        });
    }

    private void checkPermissions() {
        boolean hasPermissions = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        if (!hasPermissions) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }
    }

}