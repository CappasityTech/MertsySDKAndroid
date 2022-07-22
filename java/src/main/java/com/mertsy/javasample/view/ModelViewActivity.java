package com.mertsy.javasample.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.mertsy.common.util.exception.MertsyException;
import com.mertsy.javasample.R;
import com.mertsy.view.MertsyModel;
import com.mertsy.view.MertsyModelView;
import com.mertsy.view.MertsyModelViewParams;
import com.mertsy.view.MertsyViewer;

public class ModelViewActivity extends AppCompatActivity {

    AppCompatButton btnSearch;
    AppCompatEditText etModelIdOrUrl;
    ProgressBar pbLoading;
    LinearLayout inputsContainer;

    MertsyModelView modelView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_view);

        btnSearch = findViewById(R.id.btnSearch);
        etModelIdOrUrl = findViewById(R.id.etModelIdOrLink);
        pbLoading = findViewById(R.id.pbLoading);
        inputsContainer = findViewById(R.id.llInputsContainer);
        modelView = findViewById(R.id.mertsyModelView);

        btnSearch.setOnClickListener((v -> {
            onSearchClicked();
        }));

    }

    private void onSearchClicked() {
        String searchText = etModelIdOrUrl.getText().toString();

        if (searchText.isEmpty()) {
            return;
        }

        showLoading();

        boolean isUrl = searchText.contains("https://");

        MertsyViewer.ModelCallback resultCallback = new MertsyViewer.ModelCallback() {
            @Override
            public void onSuccess(@NonNull MertsyModel mertsyModel) {
                showModelView(mertsyModel);
            }

            @Override
            public void onFailure(@NonNull MertsyException e) {
                hideLoading();
                showErrorToast(e);
            }
        };

        if (!isUrl) {
            MertsyViewer.getModel(searchText, resultCallback);
        } else {
            MertsyViewer.getModelByLink(searchText, resultCallback);
        }

    }

    private void showModelView(MertsyModel mertsyModel) {

        MertsyModelView.OnModelLoadListener loadModelListener = new MertsyModelView.OnModelLoadListener() {
            @Override
            public void onModelReady() {
                inputsContainer.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingFailed(@NonNull MertsyException e) {
                hideLoading();
                showErrorToast(e);
            }
        };

        modelView.loadModel(mertsyModel, new MertsyModelViewParams.Builder().build(), loadModelListener);

    }


    private void showErrorToast(MertsyException exception) {
        Toast.makeText(ModelViewActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void hideLoading() {
        pbLoading.setVisibility(View.GONE);
        btnSearch.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        pbLoading.setVisibility(View.VISIBLE);
        btnSearch.setVisibility(View.GONE);
    }


}
