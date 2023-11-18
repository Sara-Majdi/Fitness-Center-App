package com.example.testing;

import java.io.Serializable;
import java.util.Date;

public class MyClass implements Serializable {
    private User trainerDetails;
    private int classDBID;
    private Date classStartTime;
    private Date classEndTime;
    private int classDuration; // Assuming duration is in minutes
    private Date classDate;
    private String classVenue;
    private String classDescription;
    private String classAvailability;

    //Constructor
    public MyClass() {
        trainerDetails = new User();
        classDBID = 0;
        classStartTime = new Date();
        classEndTime = new Date();
        classDuration = 0;
        classDate = new Date();
        classVenue = "";
        classDescription = "";
        classAvailability = "";
    }

    public MyClass(User trainerDetails, int classDBID, Date classStartTime,
                   Date classEndTime, int classDuration, Date classDate, String classVenue,
                   String classDescription, String classAvailability) {
        this.trainerDetails = trainerDetails;
        this.classDBID = classDBID;
        this.classStartTime = classStartTime;
        this.classEndTime = classEndTime;
        this.classDuration = classDuration;
        this.classDate = classDate;
        this.classVenue = classVenue;
        this.classDescription = classDescription;
        this.classAvailability = classAvailability;
    }

    public MyClass(User trainerDetails, Date classStartTime,
                   Date classEndTime, int classDuration, Date classDate, String classVenue,
                   String classDescription, String classAvailability) {
        this.trainerDetails = trainerDetails;
        this.classStartTime = classStartTime;
        this.classEndTime = classEndTime;
        this.classDuration = classDuration;
        this.classDate = classDate;
        this.classVenue = classVenue;
        this.classDescription = classDescription;
        this.classAvailability = classAvailability;
    }

    //Getters
    public User getTrainerDetails() {return this.trainerDetails;}
    public int getClassDBID(){return this.classDBID;}; //Modify later
    public Date getClassStartTime() {return this.classStartTime;}
    public Date getClassEndTime() {return this.classEndTime;}
    public int getClassDuration() {
        if (classStartTime != null && classEndTime != null) {
            long startTimeMillis = classStartTime.getTime();
            long endTimeMillis = classEndTime.getTime();
            long durationMillis = endTimeMillis - startTimeMillis;
            // Convert milliseconds to minutes
            classDuration = (int) (durationMillis / (1000 * 60));
            return this.classDuration;
        } else {
            return 0;
        }
    }
    public Date getClassDate(){return this.classDate;}
    public String getClassVenue() {return this.classVenue;}
    public String getClassDescription() {return this.classDescription;}
    public String getClassAvailability() {return this.classAvailability;}

    //Setters
    public void setTrainerDetails(User trainerDetails){this.trainerDetails = trainerDetails;}
    public void setClassDBID(int classDBID) {this.classDBID = classDBID;} //Modify this later
    public void setClassStartTime(Date classStartTime) {this.classStartTime = classStartTime;}
    public void setClassEndTime(Date classEndTime) {this.classEndTime = classEndTime;}
    public void setClassDuration(int classDuration) {this.classDuration = classDuration;}
    public void setClassDate (Date classDate) {this.classDate = classDate;}
    public void setClassVenue (String classVenue) {this.classVenue = classVenue;}
    public void setClassDescription (String classDescription) {this.classDescription = classDescription;}
    public void setClassAvailability (String classAvailability) {this.classAvailability = classAvailability;}


}
