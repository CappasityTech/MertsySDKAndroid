package com.mertsy.javasample;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import com.mertsy.capturing.CapturingResultListener;
import com.mertsy.capturing.CapturingType;
import com.mertsy.capturing.MertsyCapturingView;

public class CapturingActivity extends BaseActivity {

    public static final String TAG = "CapturingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_capturing);

        CapturingType type = getIntent().getParcelableExtra(TAG);

        MertsyCapturingView capturingView = findViewById(R.id.capturingView);

        //Or inside xml app:mertsyCapturingType
        if (type != null)
            capturingView.setCapturingType(type);

        capturingView.setLifecycleOwner(this);

        capturingView.setFragmentManager(getSupportFragmentManager());

        capturingView.setBackPressedDispatcher(getOnBackPressedDispatcher());

        capturingView.setCapturingResultListener(new CapturingResultListener() {
            @Override
            public void onComplete(String modelId) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("modelId", modelId);
                clipboard.setPrimaryClip(clip);
                showToast("Copied\n" + modelId);
                finish();
            }

            @Override
            public void onCancel() {
                finish();
            }
        });

    }

}
