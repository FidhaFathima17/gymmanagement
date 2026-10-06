package com.gym.model;

public class GymPackage {

    private int packageId;
    private String packageName;
    private String description;
    private int duration;
    private String durationUnit;
    private double price;
    private String features;
    private String status;
    private String createdAt;

    public GymPackage() {
    }

    public GymPackage(
            int packageId,
            String packageName,
            String description,
            int duration,
            String durationUnit,
            double price,
            String features,
            String status,
            String createdAt
    ) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.description = description;
        this.duration = duration;
        this.durationUnit = durationUnit;
        this.price = price;
        this.features = features;
        this.status = status;
        this.createdAt = createdAt;
    }

    public GymPackage(
            String packageName,
            String description,
            int duration,
            String durationUnit,
            double price,
            String features,
            String status
    ) {
        this.packageName = packageName;
        this.description = description;
        this.duration = duration;
        this.durationUnit = durationUnit;
        this.price = price;
        this.features = features;
        this.status = status;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
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