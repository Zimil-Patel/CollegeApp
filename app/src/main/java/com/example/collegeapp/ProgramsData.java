package com.example.collegeapp;

public class ProgramsData {

    String name, intake;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntake() {
        return intake;
    }

    public void setIntake(String intake) {
        this.intake = intake;
    }

    public ProgramsData(String name, String intake) {
        this.name = name;
        this.intake = intake;
    }

    public ProgramsData() {
    }

}
