package com.example.testing;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "SH_FITNESS.DB";
    static final int DATABASE_VERSION = 1;
    static final String Account_Table = "Accounts";
    static final String Create_Account_Table = "CREATE TABLE " + Account_Table + " ('account_DBID' INT NOT NULL AUTO_INCREMENT, " +
            "'username' VARCHAR(200) NOT NULL , 'password' VARCHAR(200) NOT NULL , 'status' VARCHAR(20) NOT NULL , " +
            "'registerDate' VARCHAR(20) NOT NULL , 'role' VARCHAR(30) NOT NULL , PRIMARY KEY ('account_DBID'), UNIQUE ('username'));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Account_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Accounts");
    }
}
