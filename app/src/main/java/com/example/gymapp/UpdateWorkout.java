package com.example.gymapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateWorkout extends AppCompatActivity {

    private EditText editTextWorkoutName;
    private Button updateButton;

    private String workoutName, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_workout);

        editTextWorkoutName = (EditText) findViewById(R.id.editTextWorkoutName);
        updateButton = (Button) findViewById(R.id.updateButton3);
        getIntentData();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(workoutName);
        }
    }

    public void onClick(View view){
        DBHelper dbHelper = new DBHelper(UpdateWorkout.this);
        workoutName = editTextWorkoutName.getText().toString();
        Workout workout = new Workout(Integer.parseInt(id), workoutName);

        dbHelper.updateWorkout(String.valueOf(workout.getId()), workout);
    }

    private void getIntentData(){
        Intent intent = getIntent();
        if(intent.hasExtra("id") && intent.hasExtra("workoutName")){
            id = getIntent().getStringExtra("id");
            workoutName = getIntent().getStringExtra("workoutName");

            editTextWorkoutName.setText(workoutName);
        } else {
            Toast.makeText(this, "No Data available", Toast.LENGTH_SHORT).show();
        }
    }

    public void onDelete(View view){
        confirmDialog();
    }
    private void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete workout "+workoutName+"?");
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper dbHelper = new DBHelper(UpdateWorkout.this);
                dbHelper.deleteWorkout(id);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}