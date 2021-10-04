package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddWorkout extends AppCompatActivity {

    private EditText editTextWorkoutName;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        editTextWorkoutName = (EditText) findViewById(R.id.editTextWorkoutName);
        saveButton = (Button) findViewById(R.id.saveButton3);
    }

    public void onClick(View view){
        DBHelper dbHelper = new DBHelper(this);
        Workout workout = new Workout();
        workout.setWorkoutName(editTextWorkoutName.getText().toString().trim());

        dbHelper.addWorkout(workout);
        Intent intent = new Intent(this, ViewWorkout.class);
        startActivity(intent);
        finish();
    }
}