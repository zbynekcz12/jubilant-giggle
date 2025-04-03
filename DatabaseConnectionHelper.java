package com.example.fingerprintapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DatabaseConnectionHelper {
    private static final String TAG = "DatabaseConnectionHelper";

    // URL endpoints for the REST API
    private static final String UDS_API_URL = "https://example.com/uds";
    private static final String EURODAC_API_URL = "https://example.com/eurodac";

    public void verifyFingerprintOnline(byte[] fingerprintData, String database) {
        new VerifyFingerprintTask(fingerprintData, database).execute();
    }

    private class VerifyFingerprintTask extends AsyncTask<Void, Void, String> {
        private byte[] fingerprintData;
        private String database;

        public VerifyFingerprintTask(byte[] fingerprintData, String database) {
            this.fingerprintData = fingerprintData;
            this.database = database;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url;
                if ("UDS".equalsIgnoreCase(database)) {
                    url = new URL(UDS_API_URL);
                } else if ("Eurodac".equalsIgnoreCase(database)) {
                    url = new URL(EURODAC_API_URL);
                } else {
                    throw new IllegalArgumentException("Unknown database: " + database);
                }

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                JSONObject jsonInput = new JSONObject();
                jsonInput.put("fingerprint", new JSONArray(fingerprintData));

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInput.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int code = conn.getResponseCode();
                if (code == 200) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        return response.toString();
                    }
                } else {
                    Log.e(TAG, "Error response code: " + code);
                    return null;
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception during fingerprint verification", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                // Process the result (e.g., parse JSON response)
                Log.d(TAG, "Fingerprint verification result: " + result);
            } else {
                Log.e(TAG, "Fingerprint verification failed");
            }
        }
    }
}