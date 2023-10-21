package com.example.testing;

public class Account {
    private User user;
    private String username;
    private String password;
    private String status;
    private String registerDate;
    private String role;

    //Constructor
    public Account() {
        this.user = new User();
        this.username = "";
        this.password = "";
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

    public void setStatus (String status){
        this.status = status;
    }

    public void setRegisterDate(String registerDate){
        this.registerDate = registerDate;
    }

    public void setRole(String role){
        this.role = role;
    }
}
