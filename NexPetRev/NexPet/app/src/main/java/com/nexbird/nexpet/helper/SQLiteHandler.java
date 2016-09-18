
package com.nexbird.nexpet.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_USER = "user";

    //Pet table name
    private static final String TABLE_PET = "pet";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_ENDERECO = "endereco";
    private static final String KEY_TELEFONE = "telefone";
    private static final String KEY_CREATED_AT = "created_at";

    //Pet Table Columns names
    private static final String KEY_NOME = "nome";
    private static final String KEY_PORTE = "porte";
    private static final String KEY_RACA = "raca";
    private static final String KEY_CARACTERISTICA = "caracteristica";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                    + KEY_ID + " INTEGER PRIMARY KEY,"
                    + KEY_NAME + " TEXT,"
                    + KEY_EMAIL + " TEXT UNIQUE,"
                    + KEY_UID + " TEXT,"
                    + KEY_CREATED_AT + " TEXT)"
                   /* + KEY_ENDERECO + "TEXT,"
                    + KEY_TELEFONE + "TEXT)"*/;

            String CREATE_PET_TABLE = "CREATE TABLE " + TABLE_PET + " ("
                    + KEY_ID + " INTEGER PRIMARY KEY,"
                    + KEY_NOME + " TEXT,"
                    + KEY_UID + " TEXT,"
                    + KEY_PORTE + " TEXT,"
                    + KEY_RACA + " TEXT,"
                    + KEY_CARACTERISTICA + " TEXT) ";

            db.execSQL(CREATE_LOGIN_TABLE);
            db.execSQL(CREATE_PET_TABLE);

            Log.d(TAG, "Database tables created");
        } catch (SQLException e) {
            Log.e("SQLErro: ", String.valueOf(e));
        }
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PET);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     */
    public void addUser(String name, String email, String uid, String endereco, String telefone, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid); // Email
       // values.put(KEY_ENDERECO, endereco);
       // values.put(KEY_TELEFONE, telefone);
        values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    public void addPet(String uid, String nome, String porte, String raca, String caracteristica) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOME, nome); // Nome
        values.put(KEY_UID, uid); // Nome
        values.put(KEY_PORTE, porte); // Porte
        values.put(KEY_RACA, raca); //raca
        values.put(KEY_CARACTERISTICA, caracteristica); //caracteristica

        // Inserting Row
        long id = db.insert(TABLE_PET, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New pet inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    public HashMap<String, String> getPetDetails() {
        HashMap<String, String> pet = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_PET;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            pet.put("nome", cursor.getString(1));
            pet.put("uid", cursor.getString(2));
            pet.put("porte", cursor.getString(3));
            pet.put("raca", cursor.getString(4));
            pet.put("caracteristica", cursor.getString(5));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching pet from Sqlite: " + pet.toString());

        return pet;
    }

    /**
     * Re crate database Delete all tables and create them again
     */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.delete(TABLE_PET, null, null);
        db.close();

        Log.d(TAG, "Deleted all user and pets info from sqlite");
    }

    public void updateUsers(String name, String email, String uid, String created_at, String endereco, String telefone) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Update rows
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_ENDERECO, endereco);
        values.put(KEY_TELEFONE, telefone);
        values.put(KEY_CREATED_AT, created_at); // Created At


        db.update(TABLE_USER, values, uid, null);
        db.close();

        Log.d(TAG, "Updated all user info from sqlite");

    }

    public void updatePets(String uid, String nome, String porte, String raca, String caracteristica) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Update rows
        ContentValues values = new ContentValues();
        values.put(KEY_NOME, nome); // Nome
        values.put(KEY_UID, uid); // Nome
        values.put(KEY_PORTE, porte); // Porte
        values.put(KEY_RACA, raca); //raca
        values.put(KEY_CARACTERISTICA, caracteristica); //caracteristica


        db.update(TABLE_PET, values, uid, null);
        db.close();

        Log.d(TAG, "Updated all pets info from sqlite");

    }

}
