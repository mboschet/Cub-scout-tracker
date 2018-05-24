package com.boschetstudios.cubscoutweeklytracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class summeryCashout extends AppCompatActivity {

    private ScoutDB db;
    private ArrayList<Scout> scouts;
    private TableLayout summeryLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summery_cashout);
        
        db = new ScoutDB(this);
        scouts = db.getScouts();
        summeryLayout = findViewById(R.id.tableLayout);

        for (Scout scout: scouts) {
            displyScoutRow(scout);
        }



    }

    public void displyScoutRow(Scout scout) {
        TableRow studentRow = new TableRow(this);
        studentRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        studentRow.layout(8, 8, 8, 8);

        TextView scoutNumber = new TextView(this);
        scoutNumber.setText(String.valueOf(scout.getScoutID()));
        studentRow.addView(scoutNumber);

        TextView scoutFistName = new TextView(this);
        scoutFistName.setText(scout.getFirstName());
        studentRow.addView(scoutFistName);

        TextView scoutLastName = new TextView(this);
        scoutLastName.setText(scout.getLastName());
        studentRow.addView(scoutLastName);

        TextView scoutBDay = new TextView(this);
        scoutBDay.setText(scout.getbDay());
        studentRow.addView(scoutBDay);

        TextView scoutDen = new TextView(this);
        scoutDen.setText(String.valueOf(scout.getScoutDenStringFormatted()));
        studentRow.addView(scoutDen);

        TextView scoutGender = new TextView(this);
        scoutGender.setText(scout.getScoutGenderStringFormatted());
        studentRow.addView(scoutGender);

        Points points = db.getPoints(scout.getScoutID());

        TextView pointsAttendance = new TextView(this);
        pointsAttendance.setText(String.valueOf(points.getAttendance()));
        studentRow.addView(pointsAttendance);

        TextView pointsBook = new TextView(this);
        pointsBook.setText(String.valueOf(points.getBook()));
        studentRow.addView(pointsBook);

        TextView pointsUniform = new TextView(this);
        pointsUniform.setText(String.valueOf(points.getUniform()));
        studentRow.addView(pointsUniform);

        TextView pointsParent = new TextView(this);
        pointsParent.setText(String.valueOf(points.getParents()));
        studentRow.addView(pointsParent);

        summeryLayout.addView(studentRow);
    }
}
