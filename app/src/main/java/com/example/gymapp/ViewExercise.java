package com.example.gymapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ViewExercise extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton add_button;
    private DBHelper db;
    private ArrayList<Exercise> list;
    private ImageView empty_image;
    private TextView empty_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exercise);

        recyclerView = findViewById(R.id.recyclerView2);
        add_button = findViewById(R.id.addButton2);
        empty_image = findViewById(R.id.imageView2);
        empty_text = findViewById(R.id.isEmpty2);

        db = new DBHelper(this);
        list = new ArrayList<>();
        displayData();
        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(this, list, ViewExercise.this);
        recyclerView.setAdapter(exerciseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewExercise.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    public void onClick(View view){
        Intent intent = new Intent(this, AddExercise.class);
        startActivity(intent);
    }

    public void displayData(){
        Cursor cursor = db.getAllExercises();
        if(cursor.getCount() == 0) {
            empty_image.setVisibility(View.VISIBLE);
            empty_text.setVisibility(View.VISIBLE);
        }else {
            while(cursor.moveToNext()){
                Exercise exercise = new Exercise();
                exercise.setId(Integer.parseInt(cursor.getString(0)));
                exercise.setExName(cursor.getString(1));
                exercise.setRepeats(Integer.parseInt(cursor.getString(2)));
                exercise.setSets(Integer.parseInt(cursor.getString(3)));
                list.add(exercise);
                empty_image.setVisibility(View.GONE);
                empty_text.setVisibility(View.GONE);
            }
        }
    }
}