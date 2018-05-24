package com.boschetstudios.cubscoutweeklytracker;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ScoutDB {

    // database constants

    public static final String DB_NAME = "SCOUT.DB";
    public static final int    DB_VERSION = 1;

    // scout table constants
    public static final String SCOUT_TABLE = "scout";

    public static final String SCOUT_ID = "_id";
    public static final int    SCOUT_ID_COL = 0;

    public static final String SCOUT_F_NAME = "scout_first_name";
    public static final int    SCOUT_F_NAME_COL = 1;

    public static final String SCOUT_L_NAME = "scout_last_name";
    public static final int    SCOUT_L_NAME_COL = 2;

    public static final String SCOUT_DEN = "scout_den";
    public static final int    SCOUT_DEN_COL = 3;

    public static final String SCOUT_BDAY = "scout_bday";
    public static final int    SCOUT_BDAY_COL = 4;

    public static final String SCOUT_GENDER = "scout_gender";
    public static final int    SCOUT_GENDER_COL = 5;

    // points table constants
    public static final String POINTS_TABLE = "points";

    public static final String POINTS_SCOUT = "_id";
    public static final int    POINTS_SCOUT_COL = 0;

    public static final String POINTS_ATTENDANCE = "points_attendance";
    public static final int    POINTS_ATTENDANCE_COL = 1;

    public static final String POINTS_BOOK = "points_book";
    public static final int    POINTS_BOOK_COL = 2;

    public static final String POINTS_UNIFORM = "points_uniform";
    public static final int    POINTS_UNIFORM_COL = 3;

    public static final String POINTS_PARENT = "points_parent";
    public static final int    POINTS_PARENT_COL = 4;

    // transaction table constrants
    public static final String TRAN_TABLE = "tran";

    public static final String TRAN_ID = "_id";
    public static final int    TRAN_ID_COL = 0;

    public static final String TRAN_DATE = "tran_date";
    public static final int    TRAN_DATE_COL = 1;

    public static final String TRAN_SCOUT = "tran_scout";
    public static final int    TRAM_SCOUT_COL = 2;

    public static final String TRAN_RESULT = "tran_result";
    public static final int    TRAN_RESULT_COL =3;

    // CREATE and DROP TABLE statements
    public static final String CREATE_SCOUT_TABLE =
            "CREATE TABLE " + SCOUT_TABLE + " (" +
                    SCOUT_ID + " INTEGER PRIMARY KEY, " +
                    SCOUT_F_NAME + " TEXT, " +
                    SCOUT_L_NAME + " TEXT, " +
                    SCOUT_DEN + " INTEGER, " +
                    SCOUT_BDAY + " TEXT, " +
                    SCOUT_GENDER + " INTEGER) ;";

    public static final String CREATE_POINTS_TABLE =
            "CREATE TABLE " + POINTS_TABLE + " (" +
                    POINTS_SCOUT + " INTEGER PRIMARY KEY, " +
                    POINTS_ATTENDANCE + " INTEGER, " +
                    POINTS_BOOK + " INTEGER, " +
                    POINTS_UNIFORM + " INTEGER, " +
                    POINTS_PARENT + " INTEGER);";

    public static final String CREATE_TRAN_TABLE =
            "CREATE TABLE " + TRAN_TABLE + " (" +
                    TRAN_ID + " INTEGER PRIMARY KEY, " +
                    TRAN_DATE + " INTEGER, " +
                    TRAN_SCOUT + " INTEGER, " +
                    TRAN_RESULT + " INTEGER);";

    public static final String DROP_SCOUT_TABLE =
            "DROP TABLE IF EXISTS " + SCOUT_TABLE;

    public static final String DROP_POINTS_TABLE =
            "DROP TABLE IF EXISTS " + POINTS_TABLE;

    public static final String DROP_TRAN_TABLE =
            "DROP TABLE IF EXISTS " + TRAN_TABLE;

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_SCOUT_TABLE);
            db.execSQL(CREATE_POINTS_TABLE);
            db.execSQL(CREATE_TRAN_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                int oldVersion, int newVersion) {
            Log.d("Task list", "Upgrading db from version "
            + oldVersion + " to " + newVersion);
            db.execSQL(ScoutDB.DROP_SCOUT_TABLE);
            db.execSQL(ScoutDB.DROP_POINTS_TABLE);
            db.execSQL(ScoutDB.DROP_TRAN_TABLE);

            onCreate(db);
        }
    }

    private SQLiteDatabase db;
    private DBHelper dbhelper;

    // constructor
    public ScoutDB(Context context) {
        dbhelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    private void openReadableDB() {
        db = dbhelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        db = dbhelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null)
            db.close();
    }

    private void closeCursor(Cursor cursor) {
        if (cursor != null)
            cursor.close();
    }

    public ArrayList<Scout> getScouts() {
        ArrayList<Scout> scouts = new ArrayList<>();
        openReadableDB();
        Cursor cursor = db.query(SCOUT_TABLE,
                null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Scout scout = new Scout();
            scout.setScoutID(cursor.getLong(SCOUT_ID_COL));
            scout.setFirstName(cursor.getString(SCOUT_F_NAME_COL));
            scout.setLastName(cursor.getString(SCOUT_L_NAME_COL));
            scout.setDen(cursor.getInt(SCOUT_DEN_COL));
            scout.setbDay(cursor.getString(SCOUT_BDAY_COL));
            scout.setGender(cursor.getInt(SCOUT_GENDER_COL));

            scouts.add(scout);
        }

        closeCursor(cursor);
        closeDB();

        return scouts;
    }

    public ArrayList<Scout> getDen (int den) {
        ArrayList<Scout> scouts = new ArrayList<>();
        String where = SCOUT_DEN + "= ?";
        String[] denString = { String.valueOf(den) };

        openReadableDB();
        Cursor cursor = db.query(SCOUT_TABLE, null, where, denString, null, null, null);

        while (cursor.moveToNext()) {
            Scout scout = new Scout();
            scout.setScoutID(cursor.getLong(SCOUT_ID_COL));
            scout.setFirstName(cursor.getString(SCOUT_F_NAME_COL));
            scout.setLastName(cursor.getString(SCOUT_L_NAME_COL));
            scout.setDen(cursor.getInt(SCOUT_DEN_COL));
            scout.setbDay(cursor.getString(SCOUT_BDAY_COL));
            scout.setGender(cursor.getInt(SCOUT_GENDER_COL));

            scouts.add(scout);
        }

        closeCursor(cursor);
        closeDB();

        return scouts;
    }

    public Points getPoints(long studentID) {
        String where = POINTS_SCOUT + "= ?";
        String[] studentString =  { String.valueOf(studentID) };

        openReadableDB();
        Cursor cursor = db.query(POINTS_TABLE, null, where, studentString, null, null, null);
        Points points = null;
        cursor.moveToFirst();
        points = new Points(cursor.getLong(POINTS_SCOUT_COL));
        points.setAttendance(cursor.getInt(POINTS_ATTENDANCE_COL));
        points.setBook(cursor.getInt(POINTS_BOOK_COL));
        points.setUniform(cursor.getInt(POINTS_UNIFORM_COL));
        points.setParents(cursor.getInt(POINTS_PARENT_COL));

        this.closeCursor(cursor);
        this.closeDB();

        return points;
    }

    public long insertScout(Scout scout) {
        ContentValues cv = new ContentValues();
        cv.put(SCOUT_ID, scout.getScoutID());
        cv.put(SCOUT_F_NAME, scout.getFirstName());
        cv.put(SCOUT_L_NAME, scout.getLastName());
        cv.put(SCOUT_DEN, scout.getDen());
        cv.put(SCOUT_BDAY, scout.getbDay());
        cv.put(SCOUT_GENDER, scout.getGender());

        this.openWriteableDB();
        long rowID = db.insert(SCOUT_TABLE, null, cv);
        this.closeDB();

        return rowID;
    }

    public void updateScout(Scout scout) {
        ContentValues cv = new ContentValues();
        cv.put(SCOUT_ID, scout.getScoutID());
        cv.put(SCOUT_F_NAME, scout.getFirstName());
        cv.put(SCOUT_L_NAME, scout.getLastName());
        cv.put(SCOUT_DEN, scout.getDen());
        cv.put(SCOUT_BDAY, scout.getbDay());
        cv.put(SCOUT_GENDER, scout.getGender());

        String where = SCOUT_ID + "= ?";
        String[] whereArgs = { String.valueOf(scout.getScoutID()) };

        this.openWriteableDB();
        int rowCount = db.update(SCOUT_TABLE, cv, where, whereArgs);
        this.closeDB();
    }

    public long insertPoints(Points points) {
        ContentValues cv = new ContentValues();
        cv.put(POINTS_SCOUT, points.getScoutID());
        cv.put(POINTS_ATTENDANCE, points.getAttendance());
        cv.put(POINTS_BOOK, points.getBook());
        cv.put(POINTS_UNIFORM, points.getUniform());
        cv.put(POINTS_PARENT, points.getParents());

        this.openWriteableDB();
        long rowID = db.insert(POINTS_TABLE, null, cv);
        this.closeDB();

        return rowID;
    }

    public void updatePoints(Points points) {
        ContentValues cv = new ContentValues();
        cv.put(POINTS_SCOUT, points.getScoutID());
        cv.put(POINTS_ATTENDANCE, points.getAttendance());
        cv.put(POINTS_BOOK, points.getBook());
        cv.put(POINTS_UNIFORM, points.getUniform());
        cv.put(POINTS_PARENT, points.getParents());

        String where = SCOUT_ID + "= ?";
        String[] whereArgs = { String.valueOf(points.getScoutID()) };

        this.openWriteableDB();
        int rowCount = db.update(POINTS_TABLE, cv, where, whereArgs);
        this.closeDB();

        //return rowCount;
    }

    public long insertTran(Tran tran) {
        ContentValues cv = new ContentValues();
        cv.put(TRAN_ID, tran.getTranID());
        cv.put(TRAN_DATE, tran.getDate());
        cv.put(TRAN_SCOUT, tran.getScoutID());
        cv.put(TRAN_RESULT, tran.getResult());

        this.openWriteableDB();
        long rowID = db.insert(POINTS_TABLE, null, cv);
        this.closeDB();

        return rowID;
    }

    public int updateTran(Tran tran) {
        ContentValues cv = new ContentValues();
        cv.put(TRAN_ID, tran.getTranID());
        cv.put(TRAN_DATE, tran.getDate());
        cv.put(TRAN_SCOUT, tran.getScoutID());
        cv.put(TRAN_RESULT, tran.getResult());

        String where = TRAN_ID + "= ?";
        String[] whereArgs = { String.valueOf(tran.getTranID()) };

        this.openWriteableDB();
        int rowCount = db.update(POINTS_TABLE,  cv, where, whereArgs);
        this.closeDB();

        return rowCount;
    }

    public int deleteScout(long id) {
        String where = SCOUT_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        this.openWriteableDB();
        int rowCount = db.delete(SCOUT_TABLE, where, whereArgs);
        this.closeDB();

        return rowCount;
    }

    public int deletePoints(long id) {
        String where = POINTS_SCOUT + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        this.openWriteableDB();
        int rowCount = db.delete(POINTS_TABLE, where, whereArgs);
        this.closeDB();

        return rowCount;
    }

    public int deleteTran(long id) {
        String where = TRAN_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        this.openWriteableDB();
        int rowCount = db.delete(TRAN_TABLE, where, whereArgs);
        this.closeDB();

        return rowCount;
    }

    public long getProfilesCount() {
        db = dbhelper.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, SCOUT_TABLE);
        db.close();

        return count;

    }
}
