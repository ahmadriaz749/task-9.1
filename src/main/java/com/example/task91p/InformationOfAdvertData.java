package com.example.task91p;


public class InformationOfAdvertData {
    private String phone;
    private String description;
    private String date;
    private String location;
    private long id;
    private String lostOrFound;
    private String name;


    public InformationOfAdvertData(long id, String lostOrFound, String name, String phone, String description, String date, String location) {
        this.id = id;
        this.lostOrFound = lostOrFound;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
    }
    // getter setter
    public long getId() {
        return id;
    }

    public String getLostOrFound() {
        return lostOrFound;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }
}
