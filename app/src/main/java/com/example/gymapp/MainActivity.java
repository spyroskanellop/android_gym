package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton add_button;
    private DBHelper db;
    private ArrayList<Athlete> list;
    private ImageView empty_image;
    private TextView empty_text;

    private GestureDetectorCompat gestureDetectorCompat;
    private float x1, x2, y1, y2;
    private static int THRESHOLD = 100;
    private static int VELOCITY_THRESHOLD = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.addButton);
        empty_image = findViewById(R.id.imageView);
        empty_text = findViewById(R.id.isEmpty);

        db = new DBHelper(this);
        list = new ArrayList<>();
        displayData();
        CustomAdapter customAdapter = new CustomAdapter(this, list, MainActivity.this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        gestureDetectorCompat = new GestureDetectorCompat(this, new GestureListener());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    public void onClick(View view){
        Intent intent = new Intent(this, AddAthlete.class);
        startActivity(intent);
    }

    public void displayData(){
        Cursor cursor = db.getAllAthletes();
        if(cursor.getCount() == 0) {
            empty_image.setVisibility(View.VISIBLE);
            empty_text.setVisibility(View.VISIBLE);
        }else {
            while(cursor.moveToNext()){
                Athlete athlete = new Athlete();
                athlete.setId(Integer.parseInt(cursor.getString(0)));
                athlete.setFirstName(cursor.getString(1));
                athlete.setLastName(cursor.getString(2));
                athlete.setPhone(Long.parseLong(cursor.getString(3)));
                athlete.setDescription(cursor.getString(4));
                list.add(athlete);
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
        builder.setTitle("Delete all athletes ?");
        builder.setMessage("Are you sure you want to delete all athletes?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.deleteAllAthletes();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
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
                            Toast.makeText(MainActivity.this, "RIGHT SWIPE", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, ViewExercise.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "LEFT SWIPE", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                } else {
                    if(Math.abs(yDiff) > THRESHOLD && Math.abs(velocityY) > VELOCITY_THRESHOLD) {
                        if(yDiff > 0){
                            Toast.makeText(MainActivity.this, "DOWN SWIPE", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "UP SWIPE", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Toast.makeText(MainActivity.this, "DOUBLE TAP", Toast.LENGTH_SHORT).show();
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Toast.makeText(MainActivity.this, "SINGLE TAP", Toast.LENGTH_SHORT).show();
            return super.onSingleTapConfirmed(e);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}