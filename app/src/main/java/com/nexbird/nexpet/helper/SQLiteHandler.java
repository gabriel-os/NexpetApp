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

    private static final int DATABASE_VERSION = 1;

    // Nome do banco
    private static final String DATABASE_NAME = "android_api";

    // Nome da tabela de Login
    private static final String TABLE_USER = "user";

    // Nome da tabela de animal
    private static final String TABLE_PET = "pet";

    // Colunas da tabela de Login
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_TELEFONEUM = "telefoneUm";
    private static final String KEY_TELEFONEDOIS = "telefoneDois";
    private static final String KEY_ENDERECO = "endereco";
    private static final String KEY_NUMERO = "numero";
    private static final String KEY_CEP = "cep";
    private static final String KEY_BAIRRO = "bairro";
    private static final String KEY_COMPLEMENTO = "complemento";
    private static final String KEY_CREATED_AT = "created_at";

    // Colunas da tabela de animal
    private static final String KEY_NOME = "nome";
    private static final String KEY_PORTE = "porte";
    private static final String KEY_RACA = "raca";
    private static final String KEY_CARACTERISTICA = "caracteristica";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Criação das tabelas
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                    + KEY_ID + " INTEGER PRIMARY KEY,"
                    + KEY_UID + " TEXT,"
                    + KEY_NAME + " TEXT,"
                    + KEY_EMAIL + " TEXT UNIQUE,"
                    + KEY_TELEFONEUM + " TEXT,"
                    + KEY_TELEFONEDOIS + " TEXT,"
                    + KEY_ENDERECO + " TEXT,"
                    + KEY_NUMERO + " TEXT,"
                    + KEY_COMPLEMENTO + " TEXT,"
                    + KEY_CEP + " TEXT,"
                    + KEY_BAIRRO + " TEXT,"
                    + KEY_CREATED_AT + " TEXT)";


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

    // Atualizando a tabela
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PET);

        // Create tables again
        onCreate(db);
    }

    public void addUser(String uid, String name, String email, String telefoneUm, String telefoneDois, String endereco, String numero, String cep, String bairro, String complemento, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_UID, uid); // UID
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_TELEFONEUM, telefoneUm);
        values.put(KEY_TELEFONEDOIS, telefoneDois);
        values.put(KEY_ENDERECO, endereco);
        values.put(KEY_NUMERO, numero);
        values.put(KEY_COMPLEMENTO, complemento);
        values.put(KEY_CEP, cep);
        values.put(KEY_BAIRRO, bairro);
        values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "Novo usuario inserido no SQLite: " + id);
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

        Log.d(TAG, "Novo animal inserido no SQLite: " + id);
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("uid", cursor.getString(1));
            user.put("name", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("telefoneUm", cursor.getString(4));
            user.put("telefoneDois", cursor.getString(5));
            user.put("endereco", cursor.getString(6));
            user.put("numero", cursor.getString(7));
            user.put("complemento", cursor.getString(8));
//            user.put("cep", cursor.getString(9));
//            user.put("bairro", cursor.getString(10));
//            user.put("created_at", cursor.getString(11));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Usuario encontrado no SQLite: " + user.toString());

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
        Log.d(TAG, "Animal encontrado no SQLite: " + pet.toString());

        return pet;
    }

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.delete(TABLE_PET, null, null);
        db.close();

        Log.d(TAG, "Tabelas de usuario e animal deletados");
    }

    public void updateUsers(String uid, String name, String email, String telefoneUm, String telefoneDois, String endereco, String numero, String cep, String bairro, String complemento, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Update rows
        ContentValues values = new ContentValues();
        values.put(KEY_UID, uid); // UID
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_TELEFONEUM, telefoneUm);
        values.put(KEY_TELEFONEDOIS, telefoneDois);
        values.put(KEY_ENDERECO, endereco);
        values.put(KEY_NUMERO, numero);
        values.put(KEY_COMPLEMENTO, complemento);
        values.put(KEY_CEP, cep);
        values.put(KEY_BAIRRO, bairro);
        values.put(KEY_CREATED_AT, created_at); // Created At


        db.update(TABLE_USER, values, uid, null);
        db.close();

        Log.d(TAG, "Informações do usuario atualizado no SQLite");

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

        Log.d(TAG, "Informações do animal atualizado no SQLite");

    }

}
