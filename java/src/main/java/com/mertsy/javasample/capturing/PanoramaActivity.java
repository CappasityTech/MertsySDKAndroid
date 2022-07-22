package com.mertsy.javasample.capturing;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mertsy.javasample.ClipboardUtil;
import com.mertsy.javasample.R;
import com.mertsy.capturing.CapturingResultListener;
import com.mertsy.capturing.MertsyCapturingView;


public class PanoramaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama);

        MertsyCapturingView capturingView = findViewById(R.id.panorama);

        capturingView.setBackPressedDispatcher(getOnBackPressedDispatcher());
        capturingView.setFragmentManager(getSupportFragmentManager());
        capturingView.setLifecycleOwner(this);
        capturingView.setCapturingResultListener(new CapturingResultListener() {
            @Override
            public void onComplete(@NonNull String modelId) {
                new ClipboardUtil(getApplicationContext()).copy(modelId);
                Toast.makeText(getApplicationContext(), "Copied to clipboard\n" +  modelId , Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onCancel() {
                finish();
            }
        });
    }


}
