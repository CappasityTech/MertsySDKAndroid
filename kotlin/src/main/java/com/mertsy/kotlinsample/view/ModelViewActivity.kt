package com.mertsy.kotlinsample.view

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.mertsy.common.util.exception.MertsyException
import com.mertsy.kotlinsample.R
import com.mertsy.view.MertsyModel
import com.mertsy.view.MertsyModelView
import com.mertsy.view.MertsyModelViewParams
import com.mertsy.view.MertsyViewer

class ModelViewActivity : AppCompatActivity() {

    private lateinit var btnSearch: AppCompatButton
    private lateinit var etModelIdOrUrl: AppCompatEditText
    private lateinit var pbLoading: ProgressBar
    private lateinit var inputsContainer: LinearLayout
    private lateinit var modelView: MertsyModelView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model_view)
        btnSearch = findViewById(R.id.btnSearch)
        etModelIdOrUrl = findViewById(R.id.etModelIdOrLink)
        pbLoading = findViewById(R.id.pbLoading)
        inputsContainer = findViewById(R.id.llInputsContainer)
        modelView = findViewById(R.id.mertsyModelView)
        btnSearch.setOnClickListener { v: View? -> onSearchClicked() }
    }

    private fun onSearchClicked() {
        val searchText = etModelIdOrUrl.text.toString()
        if (searchText.isEmpty()) {
            return
        }
        showLoading()
        val isUrl = searchText.contains("https://")

        if (!isUrl) {
            MertsyViewer.getModel(searchText, ::showModelView, ::showErrorToast)
        } else {
            MertsyViewer.getModelByLink(searchText, ::showModelView, ::showErrorToast)
        }
    }

    private fun showModelView(mertsyModel: MertsyModel) {
        modelView.loadModel(mertsyModel, MertsyModelViewParams(), onModelReady = {
            inputsContainer.visibility = View.GONE
        }, onLoadingFailed = { exception ->
            hideLoading()
            showErrorToast(exception)
        })
    }

    private fun showErrorToast(exception: MertsyException) {
        Toast.makeText(this@ModelViewActivity, exception.message, Toast.LENGTH_LONG).show()
    }

    private fun hideLoading() {
        pbLoading.visibility = View.GONE
        btnSearch.visibility = View.VISIBLE
    }

    private fun showLoading() {
        pbLoading.visibility = View.VISIBLE
        btnSearch.visibility = View.GONE
    }
}
