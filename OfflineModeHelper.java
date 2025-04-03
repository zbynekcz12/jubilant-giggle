package com.example.fingerprintapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class OfflineModeHelper extends SQLiteOpenHelper {
    private static final String TAG = "OfflineModeHelper";
    private static final String DATABASE_NAME = "fingerprints.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_FINGERPRINTS = "fingerprints";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_FEATURES = "features";

    public OfflineModeHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_FINGERPRINTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IMAGE + " BLOB, " +
                COLUMN_FEATURES + " TEXT)";
        db.execSQL(createTable);
        Log.d(TAG, "Database created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FINGERPRINTS);
        onCreate(db);
    }

    public void saveFingerprint(Bitmap bitmap, String features) {
        SQLiteDatabase db = this.getWritableDatabase();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE, imageBytes);
        values.put(COLUMN_FEATURES, features);

        long id = db.insert(TABLE_FINGERPRINTS, null, values);
        if (id == -1) {
            Log.e(TAG, "Failed to insert fingerprint");
        } else {
            Log.d(TAG, "Fingerprint saved with ID: " + id);
        }
    }

    public Cursor getFingerprintById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_FINGERPRINTS + " WHERE " + COLUMN_ID + " = ?";
        return db.rawQuery(query, new String[]{String.valueOf(id)});
    }
}