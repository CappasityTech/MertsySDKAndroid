package com.mertsy.javasample;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class ClipboardUtil {

    private final Context context;

    public ClipboardUtil(Context context) {
        this.context = context;
    }

    public void copy(String message) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("modelId", message);
        clipboard.setPrimaryClip(clip);
    }

}
