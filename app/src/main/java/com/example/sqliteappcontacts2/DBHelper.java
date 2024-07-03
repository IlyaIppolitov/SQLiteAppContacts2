package com.example.sqliteappcontacts2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    final String LOG_TAG = "myLogs";

    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "contacts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG,"----- onCreate database");
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT)";
        db.execSQL(createTable);

        // Insert sample data
        insertData(db, "Александр Блок", "alex.blok@ya.ru");
        insertData(db, "Саша Пушкин", "asp@ya.ru");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertData(SQLiteDatabase db, String name, String email) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        db.insert(TABLE_NAME, null, values);
    }

    public void addContact(String name, String email) {
        Log.d(LOG_TAG,"----- Insert in mytable: ---");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_EMAIL, email);
        long rowId = db.insert(TABLE_NAME, null, cv);
        Log.d(LOG_TAG,"----- row inserted, ID = " + rowId);
    }

    public void deleteContact(String name) {
        Log.d(LOG_TAG,"---- Delete row: ---");
        SQLiteDatabase db = this.getWritableDatabase();
        int clearCount = db.delete(TABLE_NAME, COLUMN_NAME + " = ?", new String[]{name});
        Log.d(LOG_TAG,"deleted rows count = " + clearCount);
    }

    public List<Contact> getAllContacts() {
        Log.d(LOG_TAG,"----- Get All Contacts: ---");
        List<Contact> contacts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idColIndex = cursor.getColumnIndex(COLUMN_ID);
                int nameColIndex = cursor.getColumnIndex(COLUMN_NAME);
                int emailColIndex = cursor.getColumnIndex(COLUMN_EMAIL);
                int id = cursor.getInt(idColIndex);
                String name = cursor.getString(nameColIndex);
                String email = cursor.getString(emailColIndex);
                contacts.add(new Contact(id, name, email));
                Log.d(LOG_TAG,"ID = " + id +
                        ", name = " + name +
                        ", email = " + email);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return contacts;
    }

    public void clearContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(LOG_TAG,"---- Clear mytable: ---");
        int clearCount = db.delete(TABLE_NAME, null, null);
        Log.d(LOG_TAG,"deleted rows count = " + clearCount);
    }
}
