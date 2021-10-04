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

public class ViewExercise extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton add_button;
    private DBHelper db;
    private ArrayList<Exercise> list;
    private ImageView empty_image;
    private TextView empty_text;

    private GestureDetectorCompat gestureDetectorCompat;
    private static int THRESHOLD = 100;
    private static int VELOCITY_THRESHOLD = 100;

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
        gestureDetectorCompat = new GestureDetectorCompat(this, new ViewExercise.GestureListener());
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
        builder.setTitle("Delete all exercises ?");
        builder.setMessage("Are you sure you want to delete all exercises?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.deleteAllExercises();
                Intent intent = new Intent(ViewExercise.this, ViewExercise.class);
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
                            Toast.makeText(ViewExercise.this, "RIGHT SWIPE", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ViewExercise.this, "LEFT SWIPE", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ViewExercise.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        return true;
                    }
                } else {
                    if(Math.abs(yDiff) > THRESHOLD && Math.abs(velocityY) > VELOCITY_THRESHOLD) {
                        if(yDiff > 0){
                            Toast.makeText(ViewExercise.this, "DOWN SWIPE", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ViewExercise.this, "UP SWIPE", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return false;
        }

//        @Override
//        public boolean onDoubleTap(MotionEvent e) {
//            Toast.makeText(ViewExercise.this, "DOUBLE TAP", Toast.LENGTH_SHORT).show();
//            return super.onDoubleTap(e);
//        }
//
//        @Override
//        public boolean onSingleTapConfirmed(MotionEvent e) {
//            Toast.makeText(ViewExercise.this, "SINGLE TAP", Toast.LENGTH_SHORT).show();
//            return super.onSingleTapConfirmed(e);
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}