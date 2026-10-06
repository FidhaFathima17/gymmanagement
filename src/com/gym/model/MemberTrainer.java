package com.gym.model;

import java.time.LocalDate;

public class MemberTrainer {

    private int assignmentId;
    private int memberId;
    private int trainerId;
    private String memberName;
    private String trainerName;
    private String specialization;
    private LocalDate assignedDate;
    private String status;

    public MemberTrainer() {
    }

    public MemberTrainer(
            int assignmentId,
            int memberId,
            int trainerId,
            String memberName,
            String trainerName,
            String specialization,
            LocalDate assignedDate,
            String status
    ) {
        this.assignmentId = assignmentId;
        this.memberId = memberId;
        this.trainerId = trainerId;
        this.memberName = memberName;
        this.trainerName = trainerName;
        this.specialization = specialization;
        this.assignedDate = assignedDate;
        this.status = status;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}