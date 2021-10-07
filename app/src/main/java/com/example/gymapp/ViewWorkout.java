package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ViewWorkout extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton add_button;
    private DBHelper db;
    private ArrayList<Exercise> exercises;
    private ArrayList<Workout> workouts;
    private List list;
    private ImageView empty_image;
    private TextView empty_text;

    private GestureDetectorCompat gestureDetectorCompat;
    private float x1, x2, y1, y2;
    private static int THRESHOLD = 100;
    private static int VELOCITY_THRESHOLD = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workout);

        recyclerView = findViewById(R.id.recyclerView3);
        add_button = findViewById(R.id.addButton3);
        empty_image = findViewById(R.id.imageView3);
        empty_text = findViewById(R.id.isEmpty3);
        db = new DBHelper(this);
        exercises = new ArrayList<>();
        workouts = new ArrayList<>();
        displayData();

        WorkoutAdapter workoutAdapter = new WorkoutAdapter(this, workouts, ViewWorkout.this);
        recyclerView.setAdapter(workoutAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewWorkout.this));
        gestureDetectorCompat = new GestureDetectorCompat(this, new ViewWorkout.GestureListener());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    public void onClick(View view){
        Intent intent = new Intent(this, AddWorkout.class);
        startActivity(intent);
    }

    public void displayData(){
        Cursor cursor = db.getAllWorkouts();
        if(cursor.getCount() == 0) {
            empty_image.setVisibility(View.VISIBLE);
            empty_text.setVisibility(View.VISIBLE);
        }else {
            while(cursor.moveToNext()){
                Workout workout = new Workout();
                workout.setId(Integer.parseInt(cursor.getString(0)));
                workout.setWorkoutName(cursor.getString(1));
                workouts.add(workout);
                empty_image.setVisibility(View.GONE);
                empty_text.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    private void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all workouts ?");
        builder.setMessage("Are you sure you want to delete all workouts?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.deleteAllWorkouts();
                Intent intent = new Intent(ViewWorkout.this, ViewWorkout.class);
                startActivity(intent);
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
    private class GestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float xDiff = e2.getX() - e1.getX();
            float yDiff = e2.getY() - e1.getY();
            try{
                if(Math.abs(xDiff) > Math.abs(yDiff)){
                    if(Math.abs(xDiff) > THRESHOLD && Math.abs(velocityX) > VELOCITY_THRESHOLD) {
                        if(xDiff > 0){
                            Toast.makeText(ViewWorkout.this, "RIGHT SWIPE", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ViewWorkout.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(ViewWorkout.this, "LEFT SWIPE", Toast.LENGTH_SHORT).show();

                        }
                        return true;
                    }
                } else {
//                    if(Math.abs(yDiff) > THRESHOLD && Math.abs(velocityY) > VELOCITY_THRESHOLD) {
//                        if(yDiff > 0){
//                            Toast.makeText(ViewWorkout.this, "DOWN SWIPE", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(ViewWorkout.this, "UP SWIPE", Toast.LENGTH_SHORT).show();
//                        }
//                        return true;
//                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}