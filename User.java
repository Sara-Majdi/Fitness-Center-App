package com.example.testing;

public class User {
    //Variables
    private String name;
    private String contactNo;
    private String email;
    private String gender;
    private int age;
    private double height;
    private double weight;

    //Default Constructor
    public User() {
        name = "";
        contactNo = "";
        email = "";
        gender = "";
        age = 0;
        height = 0.0;
        weight = 0.0;
    }

    public User(String name, String contactNo, String email, String gender, int age, double height, double weight){
        this.name = name;
        this.contactNo = contactNo;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    //Getters
    public String getName() {
        return this.name;
    }

    public String getContactNo() {
        return this.contactNo;
    }

    public String getEmail() {
        return this.email;
    }

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

    //Setters
    public void setName(String name){
        this.name = name;
    }

    public void setContactNo(String contactNo){
        this.contactNo = contactNo;
    }

    public void setEmail(String email){
        this.email = email;
    }

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
}
