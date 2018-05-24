package com.boschetstudios.cubscoutweeklytracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private TextView packNumberText;
    private Spinner spinnerDenText;
    private TextView childName;
    private TextView dateText;
    private CheckBox attendance;
    private CheckBox hasBook;
    private CheckBox hasUniform;
    private CheckBox parentPresent;
    private Button back;
    private Button next;
    private Button newScout;

    private ArrayAdapter<CharSequence> adapter;
    private ArrayList<Scout> scouts;
    private long totalScouts;
    private Scout currentScout = new Scout();
    private int scoutsIndex = 0;

    private ScoutDB db;

    private SharedPreferences prefs;

    private int result;
    private int denPosition = 0;
    private Scout defaultScout = new Scout(0, "Jane", "Doe", "11/11/2008", 99, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new ScoutDB(this);

        packNumberText =  findViewById(R.id.packTextView);
        spinnerDenText = findViewById(R.id.spinnerDen);
        childName = findViewById(R.id.childNameText);
        dateText = findViewById(R.id.dateTextView);
        attendance = findViewById(R.id.attendanceCheckBox);
        hasBook = findViewById(R.id.bookCheckBox);
        hasUniform = findViewById(R.id.uniformCheckBox);
        parentPresent = findViewById(R.id.parentCheckBox);

        back = findViewById(R.id.backButton);
        next = findViewById(R.id.nextButton);
        newScout = findViewById(R.id.newScoutButton);

        attendance.setOnCheckedChangeListener(this);
        hasBook.setOnCheckedChangeListener(this);
        hasUniform.setOnCheckedChangeListener(this);
        parentPresent.setOnCheckedChangeListener(this);

        back.setOnClickListener(this);
        next.setOnClickListener(this);
        newScout.setOnClickListener(this);

        totalScouts = db.getScouts().size();

        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        adapter = ArrayAdapter.createFromResource(this, R.array.denPick,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDenText.setAdapter(adapter);
        spinnerDenText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                scouts = db.getDen(i);
                if (scouts.size() > 0) {
                   /*  Context toastCon = getApplicationContext();
                    CharSequence text = String.valueOf(i);
                    int duration = Toast.LENGTH_LONG;
                    Toast.makeText(toastCon, text, duration).show(); */
                    displayScout(getCurrentScout());
                    next.setEnabled(true);
                    back.setEnabled(true);
                } else {
                    displayScout(defaultScout);
                    next.setEnabled(false);
                    back.setEnabled(false);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("den" , denPosition);
    }

    @Override
    public void onResume() {
        super.onResume();
        denPosition = prefs.getInt("den", 0);
        int pos = denPosition;
        spinnerDenText.setSelection(pos);
        String packNumber = prefs.getString("savedPackNumber", "");
        packNumberText.setText(packNumber);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton widget, boolean isChecked) {
        switch (widget.getId()) {
            case R.id.attendanceCheckBox:
            case R.id.bookCheckBox:
            case R.id.uniformCheckBox:
            case R.id.parentCheckBox:
                getResult();
        }
    }

    @Override
    public void onClick(View v) {
        Points points = new Points(currentScout.getScoutID());
        switch (v.getId()) {
            case R.id.nextButton:
                // run save code and pull up next student.
                points.upDatePoints(result);
                db.updatePoints(points);
                if (scoutsIndex >= scouts.size() - 1) {
                    denPosition++;
                    spinnerDenText.setSelection(denPosition);
                    //displayScout(getCurrentScout());
                } else {
                    scoutsIndex++;
                    displayScout(getCurrentScout());
                }
                resetChecks();
                break;

            case R.id.backButton:
                // run save code and pull up prevuse student.
                points.upDatePoints(result);
                db.updatePoints(points);
                if (scoutsIndex <= 0) {
                    denPosition--;
                    spinnerDenText.setSelection(denPosition);
                } else {
                    scoutsIndex--;
                }
                resetChecks();
                break;

            case R.id.newScoutButton:
                resetChecks();
                newScout();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;
            case R.id.scoutTable:
                startActivity(new Intent(getApplicationContext(), summeryCashout.class));
                return true;
            case R.id.About:
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Scout getCurrentScout() {

        currentScout = scouts.get(scoutsIndex);
        return currentScout;
    }


    public void displayScout(Scout scout) {
        if (currentScout != null) {

            String name = scout.getFirstName() + " " + scout.getLastName();
            childName.setText(name);
            dateText.setText(getDateString());
        }
    }

    public void resetChecks() {
        if (attendance.isChecked()) {
            attendance.toggle();
        }
        if (hasBook.isChecked()) {
            hasBook.toggle();
        }
        if (hasUniform.isChecked()) {
            hasUniform.toggle();
        }
        if (parentPresent.isChecked()) {
            parentPresent.toggle();
        }
    }

    public String getDateString () {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public void getResult() {
        if (attendance.isChecked() && hasBook.isChecked() && hasUniform.isChecked() && parentPresent.isChecked() ) {
            result = 8;
        } else if (attendance.isChecked() && hasUniform.isChecked() && parentPresent.isChecked() ) {
            result = 7;
        } else if (attendance.isChecked() && hasBook.isChecked() && parentPresent.isChecked() ) {
            result = 6;
        } else if (attendance.isChecked() && hasBook.isChecked() && hasUniform.isChecked() ) {
            result = 5;
        } else if (attendance.isChecked() && parentPresent.isChecked() ) {
            result = 4;
        } else if (attendance.isChecked() && hasUniform.isChecked() ) {
            result = 3;
        } else if (attendance.isChecked()  && hasBook.isChecked() ) {
            result = 2;
        } else if (attendance.isChecked() ) {
            result = 1;
        } else {
            result = 0;
        }

    }

    public void newScout() {

        startActivity(new Intent(MainActivity.this, addChildActivity.class));
    }
}
