package com.boschetstudios.cubscoutweeklytracker;

import java.util.Date;

public class Tran {
    private long tranID;
    private long date;
    private long scoutID;
    private int result;

    public Tran(long tranID) {
        this.tranID = tranID;
    }

    public Tran (long tranID, long date, long scoutID, int result) {
        this.tranID = tranID;
        this.date = date;
        this.scoutID = scoutID;
        this.result = result;
    }

    public long getTranID() {
        return tranID;
    }

    public void setTranID(long tranID) {
        this.tranID = tranID;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getScoutID() {
        return scoutID;
    }

    public void setScoutID(long scoutID) {
        this.scoutID = scoutID;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
