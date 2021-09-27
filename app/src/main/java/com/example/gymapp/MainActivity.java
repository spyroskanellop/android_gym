package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton add_button;
    private DBHelper db;
    private ArrayList<Athlete> list;
    private ArrayList<String> listId, firstList, lastList, descList, phoneList;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.addButton);

        db = new DBHelper(this);
        list = new ArrayList<>();
        listId = new ArrayList<>();
        firstList = new ArrayList<>();
        lastList = new ArrayList<>();
        phoneList = new ArrayList<>();
        descList = new ArrayList<>();
        displayData();
        CustomAdapter customAdapter = new CustomAdapter(this, listId, firstList, lastList, phoneList, descList);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    public void onClick(View view){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    public void displayData(){
        Cursor cursor = db.getAllAthletes();
        Athlete athlete = new Athlete();
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No Data available",Toast.LENGTH_SHORT).show();
        }else {
            while(cursor.moveToNext()){
//                athlete.setId(Integer.parseInt(cursor.getString(0)));
//                athlete.setFirstName(cursor.getString(1));
//                athlete.setLastName(cursor.getString(2));
//                athlete.setDescription(cursor.getString(4));
//                athlete.setPhone(Integer.parseInt(cursor.getString(3)));
//                list.add(athlete);
                listId.add(cursor.getString(0));
                firstList.add(cursor.getString(1));
                lastList.add(cursor.getString(2));
                phoneList.add(cursor.getString(3));
                descList.add(cursor.getString(4));

            }
        }
    }
}