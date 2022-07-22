package com.mertsy.kotlinsample

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class ClipboardUtil(private val context: Context) {
    fun copy(message: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("modelId", message)
        clipboard.setPrimaryClip(clip)
    }
}
