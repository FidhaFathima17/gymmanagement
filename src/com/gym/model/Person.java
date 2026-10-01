package com.gym.model;

public abstract class Person {
    private int id;
    private String fullName;
    private String phone;

    public Person(int id, String fullName, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
    }

    public Person(String fullName, String phone) {
        this.fullName = fullName;
        this.phone = phone;
    }

    // Encapsulation via Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    // Abstract method demonstrating Abstraction & Polymorphism
    public abstract String getRoleDetails();
}