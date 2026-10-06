package com.gym.model;

public class WorkoutExercise {

    private int exerciseId;
    private int planId;
    private String exerciseName;
    private Integer sets;
    private Integer repetitions;
    private Integer durationMinutes;
    private String instructions;

    public WorkoutExercise() {
    }

    public WorkoutExercise(
            int exerciseId,
            int planId,
            String exerciseName,
            Integer sets,
            Integer repetitions,
            Integer durationMinutes,
            String instructions) {

        this.exerciseId = exerciseId;
        this.planId = planId;
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.repetitions = repetitions;
        this.durationMinutes = durationMinutes;
        this.instructions = instructions;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public Integer getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(Integer repetitions) {
        this.repetitions = repetitions;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}