package com.gym.model;

import java.time.LocalDate;

public class Member extends Person {
    private String planType;
    private LocalDate joinDate;
    private LocalDate expiryDate;

    public Member(int id, String fullName, String phone, String planType, LocalDate joinDate, LocalDate expiryDate) {
        super(id, fullName, phone);
        this.planType = planType;
        this.joinDate = joinDate;
        this.expiryDate = expiryDate;
    }

    public Member(String fullName, String phone, String planType, LocalDate joinDate, LocalDate expiryDate) {
        super(fullName, phone);
        this.planType = planType;
        this.joinDate = joinDate;
        this.expiryDate = expiryDate;
    }

    public String getPlanType() { return planType; }
    public void setPlanType(String planType) { this.planType = planType; }

    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    @Override
    public String getRoleDetails() {
        return "Gym Member [Plan: " + planType + ", Expires: " + expiryDate + "]";
    }
}