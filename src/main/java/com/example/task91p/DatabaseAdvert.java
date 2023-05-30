package com.example.task91p;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdvert extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "lost_and_found.db";
    private static final int DATABASE_VERSION = 1;

    // SQL statement to create the advert table
    private static final String SQL_CREATE_ADVERT_TABLE =
            "CREATE TABLE " + DatabaseContract.AdvertEntry.TABLE_NAME + " (" +
                    DatabaseContract.AdvertEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DatabaseContract.AdvertEntry.COLUMN_POST_TYPE + " TEXT," +
                    DatabaseContract.AdvertEntry.COLUMN_NAME + " TEXT," +
                    DatabaseContract.AdvertEntry.COLUMN_PHONE + " TEXT," +
                    DatabaseContract.AdvertEntry.COLUMN_DESCRIPTION + " TEXT," +
                    DatabaseContract.AdvertEntry.COLUMN_DATE + " TEXT," +
                    DatabaseContract.AdvertEntry.COLUMN_LOCATION + " TEXT)";

    // Delete the table
    private static final String SQL_DELETE_ADVERT_TABLE =
            "DROP TABLE IF EXISTS " + DatabaseContract.AdvertEntry.TABLE_NAME;

    public DatabaseAdvert(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the advert table
        db.execSQL(SQL_CREATE_ADVERT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing advert table and recreate it
        db.execSQL(SQL_DELETE_ADVERT_TABLE);
        onCreate(db);
    }

    // Inserting Data
    public void insertAdvert(InformationOfAdvertData informationOfAdvertData) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.AdvertEntry.COLUMN_POST_TYPE, informationOfAdvertData.getLostOrFound());
        values.put(DatabaseContract.AdvertEntry.COLUMN_NAME, informationOfAdvertData.getName());
        values.put(DatabaseContract.AdvertEntry.COLUMN_PHONE, informationOfAdvertData.getPhone());
        values.put(DatabaseContract.AdvertEntry.COLUMN_DESCRIPTION, informationOfAdvertData.getDescription());
        values.put(DatabaseContract.AdvertEntry.COLUMN_DATE, informationOfAdvertData.getDate());
        values.put(DatabaseContract.AdvertEntry.COLUMN_LOCATION, informationOfAdvertData.getLocation());
        db.insert(DatabaseContract.AdvertEntry.TABLE_NAME, null, values);
        db.close();
    }
    // Getting form database
    public List<InformationOfAdvertData> getAllAdverts() {
        List<InformationOfAdvertData> informationOfAdvertDataList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                DatabaseContract.AdvertEntry._ID,
                DatabaseContract.AdvertEntry.COLUMN_POST_TYPE,
                DatabaseContract.AdvertEntry.COLUMN_NAME,
                DatabaseContract.AdvertEntry.COLUMN_PHONE,
                DatabaseContract.AdvertEntry.COLUMN_DESCRIPTION,
                DatabaseContract.AdvertEntry.COLUMN_DATE,
                DatabaseContract.AdvertEntry.COLUMN_LOCATION
        };
        Cursor cursor = db.query(
                DatabaseContract.AdvertEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        //create Advert objects
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseContract.AdvertEntry._ID));
                String lostOrFound = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AdvertEntry.COLUMN_POST_TYPE));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AdvertEntry.COLUMN_NAME));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AdvertEntry.COLUMN_PHONE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AdvertEntry.COLUMN_DESCRIPTION));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AdvertEntry.COLUMN_DATE));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AdvertEntry.COLUMN_LOCATION));

                InformationOfAdvertData informationOfAdvertData = new InformationOfAdvertData(id, lostOrFound, name, phone, description, date, location);
                informationOfAdvertDataList.add(informationOfAdvertData);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return informationOfAdvertDataList;
    }

    public class DatabaseContract {

        private DatabaseContract() {
            // Private constructor to prevent instantiation
        }

        public class AdvertEntry implements BaseColumns {
            public static final String TABLE_NAME = "advert";
            public static final String _ID = "_id";
            public static final String COLUMN_POST_TYPE = "post_type";
            public static final String COLUMN_NAME = "name";
            public static final String COLUMN_PHONE = "phone";
            public static final String COLUMN_DESCRIPTION = "description";
            public static final String COLUMN_DATE = "date";
            public static final String COLUMN_LOCATION = "location";
        }
    }
}
