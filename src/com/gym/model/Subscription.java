package com.gym.model;

import java.time.LocalDate;

public class Subscription {

    private int subscriptionId;
    private int memberId;
    private int packageId;

    private String memberName;
    private String packageName;

    private LocalDate startDate;
    private LocalDate endDate;

    private double amount;
    private String status;
    private String createdAt;

    public Subscription() {
    }

    public Subscription(
            int subscriptionId,
            int memberId,
            int packageId,
            String memberName,
            String packageName,
            LocalDate startDate,
            LocalDate endDate,
            double amount,
            String status,
            String createdAt
    ) {
        this.subscriptionId = subscriptionId;
        this.memberId = memberId;
        this.packageId = packageId;
        this.memberName = memberName;
        this.packageName = packageName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}