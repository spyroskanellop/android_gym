package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton add_button;
    private DBHelper db;
    private ArrayList<Athlete> list;
    private CustomAdapter customAdapter;
    private ImageView empty_image;
    private TextView empty_text;

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    public void onClick(View view){
        Intent intent = new Intent(this, AddActivity.class);
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
}