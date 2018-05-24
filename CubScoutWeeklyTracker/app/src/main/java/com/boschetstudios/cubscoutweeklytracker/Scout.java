package com.boschetstudios.cubscoutweeklytracker;


import java.lang.String;

public class Scout {

    private long scoutID;
    private String firstName;
    private String lastName;
    private String bDay;
    private int den;
    private int gender;

    public Scout () {}

    public Scout (long scoutID, String firstName, String lastName, String bDay) {
        this.scoutID = scoutID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bDay = bDay;
    }

    public Scout (long scoutID, String firstName, String lastName, String bDay, int den, int gender) {
        this.scoutID = scoutID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bDay = bDay;
        this.den = den;
        this.gender = gender;
    }

    public long getScoutID() {
        return scoutID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getbDay() {
        return bDay;
    }

    public int getDen() {
        return den;
    }

    public int getGender() {
        return gender;
    }

    public void setScoutID(long scoutID) {
        this.scoutID = scoutID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setbDay(String bDay) {
        this.bDay = bDay ;
    }

    public void setDen(int den) {
        this.den = den;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getScoutDenStringFormatted() {
        if (den == 0) {
            return "Lion Boy";
        } else if (den == 1) {
            return "Lion Girl";
        } else if (den == 2) {
            return "Tiger Boy";
        } else if (den == 3) {
            return "Tiger Girl";
        } else if (den == 4) {
            return "Wolf Boy";
        } else if (den == 5) {
            return "Wolf Girl";
        } else if (den == 6) {
            return "Bear Boy";
        } else if (den == 7) {
            return "Bear Girl";
        } else if (den == 8) {
            return "Webelos Boy";
        } else if (den == 9) {
            return "Webelos Girl";
        } else {
            return "Not reg";
        }
    }

    public String getScoutGenderStringFormatted() {
        if (gender == 0) {
            return "Boy";
        } else if (gender == 1) {
            return "Girl";
        } else {
            return "Other";
        }
    }

}
