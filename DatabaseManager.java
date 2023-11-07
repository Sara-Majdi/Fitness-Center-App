package com.example.testing;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.sql.SQLDataException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

    public boolean createProfile (int account_DBID, String name, String gender, String email,
                                 String contact_number, int age, double weight, double height,
                                 double BMI) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String rawQuery = "INSERT INTO " + DatabaseHelper.Profile_Table +
                " (account_DBID, name, gender, email, contact_number, age, weight, height, BMI) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        SQLiteStatement statement = db.compileStatement(rawQuery);
        statement.bindLong(1, account_DBID);
        statement.bindString(2, name);
        statement.bindString(3, gender);
        statement.bindString(4, email);
        statement.bindString(5, contact_number);
        statement.bindLong(6, age);
        statement.bindDouble(7, weight);
        statement.bindDouble(8, height);
        statement.bindDouble(9, BMI);

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

    public boolean updateProfile(int account_DBID, String name, String gender, String email,
                                 String contact_number, int age, double weight, double height,
                                 double BMI) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String updateQuery = "UPDATE " + DatabaseHelper.Profile_Table +
                " SET name = ?, gender = ?, email = ?, contact_number = ?, age = ?, weight = ?, height = ?, BMI = ?" +
                " WHERE account_DBID = ?";

        SQLiteStatement statement = db.compileStatement(updateQuery);
        statement.bindString(1, name);
        statement.bindString(2, gender);
        statement.bindString(3, email);
        statement.bindString(4, contact_number);
        statement.bindLong(5, age);
        statement.bindDouble(6, weight);
        statement.bindDouble(7, height);
        statement.bindDouble(8, BMI);
        statement.bindLong(9, account_DBID);

        try {
            int rowCount = statement.executeUpdateDelete();
            statement.close();
            db.close();
            return rowCount > 0;
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


    @SuppressLint("Range")
    public ArrayList<MyClass> getAvailableClasses() {
        ArrayList<MyClass> availableClasses = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date currentDate = new Date();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " +
                DatabaseHelper.Class_Table + ".*, " +
                DatabaseHelper.Account_Table + ".username AS trainerName " +
                "FROM " + DatabaseHelper.Class_Table +
                " LEFT JOIN " + DatabaseHelper.Account_Table +
                " ON " + DatabaseHelper.Class_Table + ".accountDBID = " + DatabaseHelper.Account_Table + ".account_DBID " +
                " WHERE " + DatabaseHelper.Class_Table + ".classDate >= ? " +
                " AND " + DatabaseHelper.Account_Table + ".role = 'TRAINER' " +
                " ORDER BY " + DatabaseHelper.Class_Table + ".classDate, " + DatabaseHelper.Class_Table + ".classStartTime";

        try {
            Cursor cursor = db.rawQuery(query, new String[]{dateFormat.format(currentDate)});

            while (cursor.moveToNext()) {
                MyClass myClass = new MyClass();
                User trainer = new User();

                myClass.setClassDBID(cursor.getInt(cursor.getColumnIndex("classDBID")));
                myClass.setClassDescription(cursor.getString(cursor.getColumnIndex("classDescription")));

                SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm:ss", Locale.getDefault());
                myClass.setClassStartTime(timeFormat.parse(cursor.getString(cursor.getColumnIndex("classStartTime"))));
                myClass.setClassEndTime(timeFormat.parse(cursor.getString(cursor.getColumnIndex("classEndTime"))));

                myClass.setClassDuration(cursor.getInt(cursor.getColumnIndex("classDuration")));

                myClass.setClassDate(dateFormat.parse(cursor.getString(cursor.getColumnIndex("classDate"))));
                myClass.setClassVenue(cursor.getString(cursor.getColumnIndex("classVenue")));

                trainer.setName(cursor.getString(cursor.getColumnIndex("trainerName")));
                myClass.setTrainerDetails(trainer);

                availableClasses.add(myClass);
            }
            cursor.close();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        db.close();
        return availableClasses;
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
