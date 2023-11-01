package com.example.testing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.sql.SQLDataException;
import java.util.ArrayList;

public class DatabaseManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManager (Context ctx) {context = ctx;}

    public DatabaseManager open () throws SQLDataException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public boolean createNewAccount(String username, String password, String status, String registerDate, String role) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String rawQuery = "INSERT INTO " + DatabaseHelper.Account_Table + " (username, password, status, registerDate, role) " +
                "VALUES (?, ?, ?, ?, ?)";

        SQLiteStatement statement = db.compileStatement(rawQuery);
        statement.bindString(1, username);
        statement.bindString(2, password);
        statement.bindString(3, status);
        statement.bindString(4, registerDate);
        statement.bindString(5, role);

        try {
            long rowId = statement.executeInsert();

            statement.close();
            db.close();

            return rowId != -1;
        } catch (Exception e) {
            e.printStackTrace();
            statement.close();
            db.close();
            return false;
        }
    }

    public boolean isUsernameTaken(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String rawQuery = "SELECT username FROM Accounts WHERE username=?";
        Cursor cursor = db.rawQuery(rawQuery, new String[] { username });

        boolean isTaken = cursor.moveToFirst();
        cursor.close();
        db.close();

        return isTaken;
    }


    public Cursor fetchAccountTable() {
        String [] columns = new String [] {"account_DBID", "username", "password", "role", "status", "registerDate"};
        Cursor cursor = database.query(DatabaseHelper.Account_Table, columns, null,
                null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public ArrayList<String> fetchUsername() {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ArrayList<String> usernames = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT username FROM " + dbHelper.Account_Table, null);

        while (cursor.moveToNext()){
            String username = cursor.getString(0);
            usernames.add(username);
        }
        return usernames;
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

    public SQLiteDatabase getReadableDatabase() {
        return dbHelper.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDatabase() {
        return dbHelper.getWritableDatabase();
    }

}
