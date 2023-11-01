package com.example.testing;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class Account {
    private User user;
    private String username;
    private String password;
    private String email;
    private String status;
    private String registerDate;
    private String role;

    //Constructor
    public Account() {
        this.user = new User();
        this.username = "";
        this.password = "";
        this.email = "";
        this.status = "";
        this.registerDate = "";
        this.role = "";
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
    public String getEmail() {return this.email;}

    public String getStatus(){
        return this.status;
    }

    public String getRegisterDate(){
        return this.registerDate;
    }

    public String getRole(){
        return this.role;
    }

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
    public void setEmail(String email) {this.email = email;}

    public void setStatus (String status){
        this.status = status;
    }

    public void setRegisterDate(String registerDate){
        this.registerDate = registerDate;
    }

    public void setRole(String role){
        this.role = role;
    }

    //Other functions
    public boolean login(DatabaseManager databaseManager) {
        SQLiteDatabase db = databaseManager.getReadableDatabase();
        String sql = "SELECT * FROM Accounts WHERE username = ? AND password = ?";

        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindString(1, username);
        statement.bindString(2, password);

        long count = statement.simpleQueryForLong();

        statement.close();
        db.close();

        return count > 0;
    }

}
