package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddWorkout extends AppCompatActivity {

    private EditText editTextWorkoutName;
    private Button saveButton;
    private RecyclerView recyclerView;
    private DBHelper db;
    private ArrayList<Exercise> list;
    private ExerciseAdapterView exerciseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        editTextWorkoutName = (EditText) findViewById(R.id.editTextWorkoutName);
        saveButton = (Button) findViewById(R.id.saveButton3);
        recyclerView = findViewById(R.id.recyclerView4);

        db = new DBHelper(this);
        list = new ArrayList<>();
        displayExerciseData();
        exerciseAdapter = new ExerciseAdapterView(this, list, AddWorkout.this);
        recyclerView.setAdapter(exerciseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddWorkout.this));
    }

    public void onClick(View view){
        DBHelper dbHelper = new DBHelper(this);
        Workout workout = new Workout();
        Exercise exercise = new Exercise();

        workout.setWorkoutName(editTextWorkoutName.getText().toString().trim());
        ArrayList<String> list = exerciseAdapter.getList();
        dbHelper.addWorkout(workout);

        if(list.size()>0){
            workout = dbHelper.searchWorkoutByName(workout.getWorkoutName());
            assosiate(list, workout);
        }
        Intent intent = new Intent(this, ViewWorkout.class);
        startActivity(intent);
        finish();
    }

    public void displayExerciseData(){
        Cursor cursor = db.getAllExercises();
            while(cursor.moveToNext()){
                Exercise exercise = new Exercise();
                exercise.setId(Integer.parseInt(cursor.getString(0)));
                exercise.setExName(cursor.getString(1));
                exercise.setRepeats(Integer.parseInt(cursor.getString(2)));
                exercise.setSets(Integer.parseInt(cursor.getString(3)));
                list.add(exercise);
        }
    }

    public void assosiate(ArrayList<String> list, Workout workout){
        Exercise exercise = new Exercise();
        DBHelper dbHelper = new DBHelper(this);
        for(String s : list){
            exercise = dbHelper.searchExerciseById(s);
            dbHelper.fillWorkout(workout, exercise);
        }
    }

}