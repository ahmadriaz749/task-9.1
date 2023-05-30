package com.example.task91p;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RemoveData extends AppCompatActivity {
    private Button buttonRemove;
    private DatabaseAdvert databaseAdvert;
    private long advertId;
    private TextView textViewHeading;
    private TextView textViewDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_data);

        // Initialize views
        textViewHeading = findViewById(R.id.textViewHeading);
        textViewDetail = findViewById(R.id.textViewDetail);
        buttonRemove = findViewById(R.id.buttonRemove);

        // Making an instance of DatabaseAdvert
        databaseAdvert = new DatabaseAdvert(this);

        // Getting by ID
        advertId = getAdvertIdFromIntent();
        setAdvertDetails();

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Removing the advert from the database
                if (removeAdvertFromDatabase(advertId)) {
                    finish();
                } else {

                }
            }
        });
    }

    // Getting by ID
    private long getAdvertIdFromIntent() {
        long advertId = -1; // Adding default value

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            advertId = extras.getLong("advert_id", -1);
        }

        return advertId;
    }

    // Add text views
    private void setAdvertDetails() {
        long advertId = getAdvertIdFromIntent();

        // Getting data from database using ID
        DatabaseAdvert databaseAdvert = new DatabaseAdvert(this);
        SQLiteDatabase database = databaseAdvert.getReadableDatabase();

        String[] projection = {
                DatabaseAdvert.DatabaseContract.AdvertEntry.COLUMN_NAME,
                DatabaseAdvert.DatabaseContract.AdvertEntry.COLUMN_DESCRIPTION,
                DatabaseAdvert.DatabaseContract.AdvertEntry.COLUMN_PHONE,
                DatabaseAdvert.DatabaseContract.AdvertEntry.COLUMN_DATE,
                DatabaseAdvert.DatabaseContract.AdvertEntry.COLUMN_LOCATION
        };

        String selection = DatabaseAdvert.DatabaseContract.AdvertEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(advertId)};

        Cursor cursor = database.query(
                DatabaseAdvert.DatabaseContract.AdvertEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseAdvert.DatabaseContract.AdvertEntry.COLUMN_NAME));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseAdvert.DatabaseContract.AdvertEntry.COLUMN_DESCRIPTION));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseAdvert.DatabaseContract.AdvertEntry.COLUMN_PHONE));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseAdvert.DatabaseContract.AdvertEntry.COLUMN_DATE));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseAdvert.DatabaseContract.AdvertEntry.COLUMN_LOCATION));


            // Adding the data in views
            textViewHeading.setText(name);
            String details = "Description: " + description + "\nPhone: " + phone + "\nDate: " + date + "\nLocation: " + location;
            textViewDetail.setText(details);
            cursor.close();
        }

        database.close();
        databaseAdvert.close();
    }

    // Remove from the database
    private boolean removeAdvertFromDatabase(long advertId) {
        DatabaseAdvert databaseAdvert = new DatabaseAdvert(this);
        SQLiteDatabase database = databaseAdvert.getWritableDatabase();

        String selection = DatabaseAdvert.DatabaseContract.AdvertEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(advertId)};

        int deletedRows = database.delete(
                DatabaseAdvert.DatabaseContract.AdvertEntry.TABLE_NAME,
                selection,
                selectionArgs
        );

        database.close();
        databaseAdvert.close();

        return deletedRows > 0;
    }

}
