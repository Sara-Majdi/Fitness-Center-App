package com.example.testing;

import java.util.Date;

public class Announcement {
    private String description;
    private String announcementTitle;
    private int announcementDBID;
    private Date creationDate;
    private Date creationTime;


    //Constructor
    public Announcement(String description, String announcementTitle, int announcementDBID, Date creationDate, Date creationTime) {
        this.description = description;
        this.announcementTitle = announcementTitle;
        this.announcementDBID = announcementDBID;
        this.creationDate = creationDate;
        this.creationTime = creationTime;
    }

    public Announcement(){
        this.description = "";
        this.announcementDBID = 0;
        this.announcementTitle = "";
        this.creationDate = new Date();
        this.creationTime = new Date();
    }

    //Getters
    public String getDescription() {
        return description;
    }

    public String getAnnouncementTitle() {
        return announcementTitle;
    }

    public int getAnnouncementDBID() {
        return announcementDBID;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getCreationTime() {
        return creationTime;
    }



    //Setters
    public void setDescription(String description) {
        this.description = description;
    }

    public void setAnnouncementTitle(String announcementTitle){
        this.announcementTitle = announcementTitle;
    }

    public void setAnnouncementDBID(int announcementDBID) {
        this.announcementDBID = announcementDBID;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

}
