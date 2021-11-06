package com.example.gymapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gymapp.DBHelper;
import com.example.gymapp.entities.Exercise;
import com.example.gymapp.R;
import com.example.gymapp.adapters.ViewExercise;

public class AddExerciseActivity extends AppCompatActivity {

    private EditText editTextExName, editTextRepeats, editTextSets;
    private TextView textViewAddExercise;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        editTextExName = (EditText) findViewById(R.id.editTextExName);
        editTextRepeats = (EditText) findViewById(R.id.editTextRepeats);
        editTextSets = (EditText) findViewById(R.id.editTextSets);

        saveButton = (Button) findViewById(R.id.saveButton2);
    }

    public void onClick(View view) throws InterruptedException {
        DBHelper dbHelper = new DBHelper(this);
        Exercise exercise = new Exercise();
        exercise.setExName(editTextExName.getText().toString().trim());
        exercise.setRepeats(Integer.parseInt(editTextRepeats.getText().toString().trim()));
        exercise.setSets(Integer.parseInt(editTextSets.getText().toString().trim()));

        dbHelper.addExercise(exercise);
        Intent intent = new Intent(this, ViewExercise.class);
        startActivity(intent);
        finish();
    }
}