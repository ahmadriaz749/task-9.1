package com.example.task91p;


import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class NewAdvertCreate extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int LOCATION_PICKER_REQUEST_CODE = 1;
    private RadioGroup radioGroupContainer;
    private RadioButton radioButtonLost;
    private RadioButton radioButtonFound;
    private EditText editTextName;
    private DatabaseAdvert dbHelper;
    private EditText editTextPhone;
    private EditText editTextDescription;
    private EditText editTextDate;
    private EditText editTextLocation;
    private Button buttonSave;
    private Button buttonCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_advert_create);

        dbHelper = new DatabaseAdvert(this);
        radioGroupContainer = findViewById(R.id.radioGroupLostAndFound);
        radioButtonLost = findViewById(R.id.radioLost);
        radioButtonFound = findViewById(R.id.radioFound);
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDate = findViewById(R.id.editTextDate);
        editTextLocation = findViewById(R.id.editTextLocation);
        buttonSave = findViewById(R.id.buttonSave);
        buttonCurrentLocation = findViewById(R.id.buttonCurrentLocation);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAdvert();
            }
        });
        editTextLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start an activity to select the location using Google Maps
                // Handle the result in onActivityResult() method
                Intent intent = new Intent(NewAdvertCreate.this, PickGoogleMaps.class);
                startActivityForResult(intent, LOCATION_PICKER_REQUEST_CODE);
            }
        });

        // On click listener for current location
        buttonCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
            }
        });
    }

    // Getting current location
    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            startLocationUpdates();
        }
    }

    // Saving the information or Advert to Database
    private void saveAdvert() {
        String postType = radioButtonLost.isChecked() ? "Lost" : "Found";
        String name = editTextName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseAdvert.DatabaseContract.AdvertEntry.COLUMN_POST_TYPE, postType);
        values.put(DatabaseAdvert.DatabaseContract.AdvertEntry.COLUMN_NAME, name);
        values.put(DatabaseAdvert.DatabaseContract.AdvertEntry.COLUMN_PHONE, phone);
        values.put(DatabaseAdvert.DatabaseContract.AdvertEntry.COLUMN_DESCRIPTION, description);
        values.put(DatabaseAdvert.DatabaseContract.AdvertEntry.COLUMN_DATE, date);
        values.put(DatabaseAdvert.DatabaseContract.AdvertEntry.COLUMN_LOCATION, location);

        // Insert into the database
        long newRowId = db.insert(DatabaseAdvert.DatabaseContract.AdvertEntry.TABLE_NAME, null, values);

        // Check if the insertion was successful
        if (newRowId != -1) {
            Toast.makeText(this, "Advert saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save advert", Toast.LENGTH_SHORT).show();
        }
    }

    // Updates the location
    private void startLocationUpdates() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationManager.removeUpdates(this);
                // Get the location
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String currentLocation = latitude + "," + longitude;
                editTextLocation.setText(currentLocation);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        // Update request
        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start listening for location updates
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOCATION_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            // Getting the location
            double latitude = data.getDoubleExtra("latitude", 0.0);
            double longitude = data.getDoubleExtra("longitude", 0.0);
            String selectedLocation = latitude + "," + longitude;
            editTextLocation.setText(selectedLocation);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }
}
