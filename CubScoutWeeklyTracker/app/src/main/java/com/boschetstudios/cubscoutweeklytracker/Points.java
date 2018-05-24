package com.boschetstudios.cubscoutweeklytracker;

public class Points {
    private long scoutID;
    private long attendance;
    private long book;
    private long uniform;
    private long parents;

    public Points(long scoutID) {
        this.scoutID = scoutID;
        this.attendance = 0;
        this.book = 0;
        this.uniform = 0;
        this.parents = 0;
    }

    private void addAttendance() {
        attendance++;
    }

    private void addBook() {
        book++;
    }

    private void addUniform() {
        uniform++;
    }

    private void addParents() {
        parents++;
    }

    public void upDatePoints(int result) {
        if (result == 1) {
            addAttendance();
        } else if (result == 2) {
            addAttendance();
            addBook();;
        } else if (result == 3) {
            addAttendance();
            addUniform();
        } else if (result == 4) {
            addAttendance();
            addParents();
        } else if (result == 5) {
            addAttendance();
            addBook();
            addUniform();
        } else if (result == 6) {
            addAttendance();
            addBook();
            addParents();
        } else if (result == 7) {
            addAttendance();
            addUniform();
            addParents();
        } else if (result == 8) {
            addAttendance();
            addBook();
            addUniform();
            addParents();
        }
    }

    public long getScoutID() {
        return scoutID;
    }

    public void setScoutID(long scoutID) {
        this.scoutID = scoutID;
    }

    public long getAttendance() {
        return attendance;
    }

    public void setAttendance(long attendance) {
        this.attendance = attendance;
    }

    public long getBook() {
        return book;
    }

    public void setBook(long book) {
        this.book = book;
    }

    public long getUniform() {
        return uniform;
    }

    public void setUniform(long uniform) {
        this.uniform = uniform;
    }

    public long getParents() {
        return parents;
    }

    public void setParents(long parents) {
        this.parents = parents;
    }
}
