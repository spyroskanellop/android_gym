package com.example.gymapp.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gymapp.DBHelper;
import com.example.gymapp.adapters.ExerciseAdapterView;
import com.example.gymapp.R;
import com.example.gymapp.adapters.ViewWorkout;
import com.example.gymapp.entities.Exercise;
import com.example.gymapp.entities.Workout;

import java.util.ArrayList;

public class UpdateWorkoutActivity extends AppCompatActivity {

    private EditText editTextWorkoutName;
    private Button updateButton;
    private RecyclerView inContainer, outContainer;
    private ArrayList<Exercise> inList, outList;
    private String workoutName, id;
    private ExerciseAdapterView exerciseAdapter;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_workout);

        editTextWorkoutName = (EditText) findViewById(R.id.editTextWorkoutName);
        editTextWorkoutName.setTextSize(50);
        updateButton = (Button) findViewById(R.id.updateButton3);
        inContainer = (RecyclerView) findViewById(R.id.recyclerViewInside);
        outContainer = (RecyclerView) findViewById(R.id.recyclerViewOutside);
        inList = new ArrayList<>();
        outList = new ArrayList<>();
        dbHelper = new DBHelper(this);
        getIntentData();
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setTitle(workoutName);
        }
        inContainer.setScaleX(0.7f);
        inContainer.setScaleY(0.9f);
        inContainer.setX(-500);

        inList = dbHelper.findExercises(id);       // list of exercises with workoutID
        exerciseAdapter = new ExerciseAdapterView(this, inList, UpdateWorkoutActivity.this);
        exerciseAdapter.isClickable = true;
        inContainer.setAdapter(exerciseAdapter);
        inContainer.setLayoutManager(new LinearLayoutManager(UpdateWorkoutActivity.this));

    }

    public void onClick(View view){
        DBHelper dbHelper = new DBHelper(UpdateWorkoutActivity.this);
        workoutName = editTextWorkoutName.getText().toString();
        Workout workout = new Workout(Integer.parseInt(id), workoutName);
        dbHelper.updateWorkout(String.valueOf(workout.getId()), workout);
        Intent intent = new Intent(this, ViewWorkout.class);
        startActivity(intent);
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

    private ArrayList<Exercise> getRegisteredExercises(String row_id){
        inList = dbHelper.findExercises(row_id);
        return inList;
    }

}