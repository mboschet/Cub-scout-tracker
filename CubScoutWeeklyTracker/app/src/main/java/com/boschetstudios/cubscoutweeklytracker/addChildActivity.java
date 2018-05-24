package com.boschetstudios.cubscoutweeklytracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class addChildActivity extends AppCompatActivity implements View.OnClickListener {

    private ScoutDB db;

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private Spinner denPickerSpinner;
    private Spinner genderPickerSpinner;
    private Calendar birthdayCalendar;
    private int day, month, year;
    private String birthdayString;

    private ArrayAdapter<CharSequence> adapterDen, adapterGender;
    private Scout newScout = new Scout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new ScoutDB(this);

        setContentView(R.layout.activity_add_child);

        firstNameEditText = findViewById(R.id.firstNameEdit);
        lastNameEditText = findViewById(R.id.lastNameEdit);
        denPickerSpinner = findViewById(R.id.denPickSpinner);
        genderPickerSpinner = findViewById(R.id.genderPickSpinner);

        birthdayCalendar = Calendar.getInstance();
        day = birthdayCalendar.get(Calendar.DAY_OF_MONTH);
        month = birthdayCalendar.get(Calendar.MONTH);
        year = birthdayCalendar.get(Calendar.YEAR) - 6;

        month = month + 1;

        Button addChild = findViewById(R.id.newChild);
        Button exit = findViewById(R.id.exitButton);
        final Button birthdayB = findViewById(R.id.birthdayButton);

        addChild.setOnClickListener(this);
        exit.setOnClickListener(this);
        birthdayB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(addChildActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        birthdayString = (dayOfMonth + "/" + monthOfYear + "/" + year);
                        birthdayB.setText(birthdayString);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

       adapterDen = ArrayAdapter.createFromResource(this, R.array.denPick, android.R.layout.simple_spinner_item);
       adapterDen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       denPickerSpinner.setAdapter(adapterDen);
       denPickerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               newScout.setDen(i);
               /*Context toastCon = getApplicationContext();
               CharSequence text = String.valueOf(i);
               int duration = Toast.LENGTH_LONG;
               Toast.makeText(toastCon, text, duration).show(); */
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

       adapterGender = ArrayAdapter.createFromResource(this, R.array.genderPick, android.R.layout.simple_spinner_item);
       adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       genderPickerSpinner.setAdapter(adapterGender);
       genderPickerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               newScout.setGender(i);
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newChild:
                addChild();

            case R.id.exitButton:
                finish();
                break;

        }
    }




    private void addChild() {

        ArrayList<Scout> scoutList = db.getScouts();
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();;

        newScout.setScoutID(scoutList.size() + 1);
        newScout.setFirstName(firstName);
        newScout.setLastName(lastName);
        newScout.setbDay(birthdayString);

        Points points = new Points(newScout.getScoutID());


        db.insertScout(newScout);
        db.insertPoints(points);

    }

}
