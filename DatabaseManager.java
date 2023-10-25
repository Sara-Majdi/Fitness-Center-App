package com.example.testing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLDataException;

public class DatabaseManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManager (Context ctx){
        context = ctx;
    }

    public DatabaseManager open () throws SQLDataException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public void addNewAccount (String username, String password, String status, String registerDate, String role) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("status", status);
        contentValues.put("registerDate", registerDate);
        contentValues.put("role", role);
        database.insert(DatabaseHelper.Account_Table, null, contentValues);
    }

    public Cursor fetch() {
        String [] columns = new String [] {"account_DBID", "username", "password", "role", "status", "registerDate"};
        android.database.Cursor cursor = database.query(DatabaseHelper.Account_Table, columns, null,
                null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long account_DBID, String username, String password){
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        int ret = database.update(DatabaseHelper.Account_Table, contentValues, "account_DBID = " + account_DBID, null);
        return ret;
    }

    public void deleteAccount(long account_DBID){
        database.delete(DatabaseHelper.Account_Table, "account_DBID = " + account_DBID, null);
    }

}
