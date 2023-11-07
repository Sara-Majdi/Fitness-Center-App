package com.example.testing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;

public class User implements Serializable {
    //Variables
    private String name;
    private String contactNo;
    private String email;
    private String gender;
    private int age;
    private double height;
    private double weight;
    private double BMI;

    //Default Constructor
    public User() {
        name = "";
        contactNo = "";
        email = "";
        gender = "";
        age = 0;
        height = 0.0;
        weight = 0.0;
        BMI = 0.0;
    }

    public User(String name, String contactNo, String email, String gender, int age, double height, double weight, double BMI){
        this.name = name;
        this.contactNo = contactNo;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.BMI = BMI;
    }

    //Getters
    public String getName() {
        return this.name;
    }

    public String getContactNo() {return this.contactNo;}
    public String getEmail() {return this.email;}

    public String getGender() {
        return this.gender;
    }

    public int getAge() {
        return this.age;
    }

    public double getHeight() {
        return this.height;
    }

    public double getWeight() {
        return this.weight;
    }

    public double getBMI() {return this.BMI;}

    //Setters
    public void setName(String name){
        this.name = name;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setEmail(String email){this.email = email;}
    public void setGender(String gender){
        this.gender = gender;
    }
    public void setAge (int age){
        this.age = age;
    }
    public void setWeight (double weight){
        this.weight = weight;
    }

    public void setHeight (double height){
        this.height = height;
    }
    public void setBMI() {
        double tempHeight = this.height / 100;
        this.BMI = this.weight / (tempHeight * tempHeight);
    }

    //Other functions
    @SuppressLint("Range")
    public boolean setAllDetailsFromDB(DatabaseManager dbManager, int account_DBID) {
        SQLiteDatabase db = dbManager.getReadableDatabase();
        String query = "SELECT * FROM Profiles WHERE account_DBID = ?";
        boolean isExist = false;

        try {
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(account_DBID)});

            if (cursor.moveToFirst()) {
                name = cursor.getString(cursor.getColumnIndex("name"));
                gender = cursor.getString(cursor.getColumnIndex("gender"));
                email = cursor.getString(cursor.getColumnIndex("email"));
                contactNo = cursor.getString(cursor.getColumnIndex("contact_number"));
                age = cursor.getInt(cursor.getColumnIndex("age"));
                weight = cursor.getDouble(cursor.getColumnIndex("weight"));
                height = cursor.getDouble(cursor.getColumnIndex("height"));
                BMI = cursor.getDouble(cursor.getColumnIndex("BMI"));
                isExist = true;
            }
            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        db.close();
        return isExist;
    }
}
