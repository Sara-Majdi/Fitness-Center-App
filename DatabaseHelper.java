package com.example.testing;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    static final String DATABASE_NAME = "SH_FITNESS.DB";
    static final int DATABASE_VERSION = 1;
    static final String Account_Table = "Accounts";
    static final String Profile_Table = "Profiles";
    static final String Class_Table = "Class";
    static final String Create_Account_Table = "CREATE TABLE " + Account_Table + " " +
                                                "(account_DBID INTEGER PRIMARY KEY, " +
                                                "username VARCHAR(200) NOT NULL, " +
                                                "password VARCHAR(200) NOT NULL, " +
                                                "status VARCHAR(20) NOT NULL, " +
                                                "registerDate VARCHAR(20) NOT NULL, " +
                                                "role VARCHAR(30) NOT NULL, UNIQUE (username));";

    static final String Create_Profiles_Table = "CREATE TABLE " + Profile_Table + " (" +
            "profile_DBID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "account_DBID INTEGER, " +
            "name VARCHAR(300) NOT NULL, " +
            "gender VARCHAR(100) NOT NULL, " +
            "email VARCHAR(100) NOT NULL, " +
            "contact_number VARCHAR(30) NOT NULL, " +
            "age INTEGER NOT NULL, " +
            "weight DOUBLE NOT NULL, " +
            "height DOUBLE NOT NULL, " +
            "BMI DOUBLE NOT NULL, " +
            "FOREIGN KEY (account_DBID) REFERENCES " + Account_Table + "(account_DBID));";

    private static final String Create_Class_Table = "CREATE TABLE " + Class_Table + " (" +
            "classDBID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "accountDBID INTEGER, " +
            "classDescription VARCHAR(200) NOT NULL, " +
            "classStartTime DATE NOT NULL, " +
            "classEndTime DATE NOT NULL, " +
            "classDuration INT NOT NULL, " +
            "classDate DATE NOT NULL, " +
            "classVenue VARCHAR(200) NOT NULL, " +
            "FOREIGN KEY (accountDBID) REFERENCES Accounts(accountDBID)" +
            ");";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Account_Table);
        db.execSQL(Create_Profiles_Table);
        db.execSQL(Create_Class_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Accounts");
    }
}
