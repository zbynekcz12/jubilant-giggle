package com.example.fingerprintapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class CNNInferenceActivity extends AppCompatActivity {
    private static final String TAG = "CNNInferenceActivity";
    private TensorFlowLiteHelper tfliteHelper;
    private Bitmap fingerprintBitmap; // Assume this bitmap is already initialized with the fingerprint image

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cnn_inference);

        try {
            tfliteHelper = new TensorFlowLiteHelper(this);
        } catch (IOException e) {
            Log.e(TAG, "Error initializing TensorFlow Lite", e);
            Toast.makeText(this, "Error initializing TensorFlow Lite", Toast.LENGTH_LONG).show();
            return;
        }

        if (fingerprintBitmap != null) {
            float[] results = tfliteHelper.runInference(fingerprintBitmap);
            displayResults(results);
        } else {
            Toast.makeText(this, "Fingerprint image not available", Toast.LENGTH_LONG).show();
        }
    }

    private void displayResults(float[] results) {
        // Display the results of the inference
        // This could involve updating the UI with the results, etc.
        for (int i = 0; i < results.length; i++) {
            Log.d(TAG, "Result " + i + ": " + results[i]);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tfliteHelper != null) {
            tfliteHelper.close();
        }
    }
}