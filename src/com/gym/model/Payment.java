package com.gym.model;

import java.time.LocalDateTime;

public class Payment {

    private int paymentId;
    private int subscriptionId;
    private int memberId;

    private String memberName;
    private String packageName;

    private double amount;

    private String transactionId;
    private String paymentMethod;
    private String paymentStatus;

    private LocalDateTime paymentDate;

    public Payment() {
    }

    public Payment(
            int paymentId,
            int subscriptionId,
            int memberId,
            String memberName,
            String packageName,
            double amount,
            String transactionId,
            String paymentMethod,
            String paymentStatus,
            LocalDateTime paymentDate
    ) {
        this.paymentId = paymentId;
        this.subscriptionId = subscriptionId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.packageName = packageName;
        this.amount = amount;
        this.transactionId = transactionId;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
}