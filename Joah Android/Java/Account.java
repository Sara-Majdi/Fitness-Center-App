package com.example.testing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.io.Serializable;

public class Account implements Serializable {
    private User user;
    private String username;
    private String password;
    private String status;
    private String registerDate;
    private String role;
    private int accountDBID;
    private boolean hasProfile;

    //Constructor
    public Account() {
        this.user = new User();
        this.username = "";
        this.password = "";
        this.status = "";
        this.registerDate = "";
        this.role = "";
        this.accountDBID = 0;
        this.hasProfile = false;
    }


    //Getters
    public User getUserDetails(){
        return this.user;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public String getStatus(){
        return this.status;
    }

    public String getRegisterDate(){
        return this.registerDate;
    }

    public String getRole(){
        return this.role;
    }

    public int getAccountDBID() {return this.accountDBID;}

    public boolean isHavingProfile() {return this.hasProfile;}


    //Setters
    public void setUser(User user){
        this.user = user;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public void setStatus (String status){
        this.status = status;
    }

    public void setRegisterDate(String registerDate){
        this.registerDate = registerDate;
    }

    public void setRole(String role){
        this.role = role;
    }

    public void setProfile(DatabaseManager dbManager) {
        if (this.user.setAllDetailsFromDB(dbManager, this.accountDBID)){
            this.hasProfile = true;
        }
    }

    public void setAccountDBID(DatabaseManager dbManager){
        SQLiteDatabase db = dbManager.getReadableDatabase();

        try {
            String usernameQuery = "SELECT account_DBID FROM Accounts WHERE username = ?";
            Cursor cursor = db.rawQuery(usernameQuery, new String[]{this.username});

            if (cursor.moveToFirst()) {
                this.accountDBID = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("Range")
    public void setRoleFromDB(DatabaseManager dbManager){
        SQLiteDatabase db = dbManager.getReadableDatabase();

        try {
            String usernameQuery = "SELECT role FROM Accounts WHERE account_DBID = ?";
            Cursor cursor = db.rawQuery(usernameQuery, new String[]{String.valueOf(this.accountDBID)});

            if (cursor.moveToFirst()) {
                this.role = cursor.getString(cursor.getColumnIndex("role"));
            }
            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Other functions
    public boolean login(DatabaseManager databaseManager) {
        SQLiteDatabase db = null;
        SQLiteStatement statement = null;

        try {
            db = databaseManager.getReadableDatabase();
            String sql = "SELECT * FROM Accounts WHERE username = ? AND password = ?";
            statement = db.compileStatement(sql);
            statement.bindString(1, username);
            statement.bindString(2, password);
            long count = statement.simpleQueryForLong();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

}
