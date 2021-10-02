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
import android.widget.TextView;
import android.widget.Toast;

public class UpdateExercise extends AppCompatActivity {

    private EditText editTextExName, editTextRepeats, editTextSets;
    private TextView textView;
    private Button updateButton;

    private String exName, repeats, sets, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_exercise);

        editTextExName = (EditText)findViewById(R.id.editTextExName2);
        editTextRepeats = (EditText)findViewById(R.id.editTextRepeats2);
        editTextSets = (EditText)findViewById(R.id.editTextSets2);
        textView = (TextView) findViewById(R.id.textView2);
        updateButton = (Button) findViewById(R.id.updateButton2);
        getIntentData();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(exName);
        }
    }

    public void onClick(View view){
        DBHelper dbHelper = new DBHelper(UpdateExercise.this);
        exName = editTextExName.getText().toString();
        repeats = editTextRepeats.getText().toString();
        sets = editTextSets.getText().toString();
        Exercise exercice = new Exercise(Integer.parseInt(id), exName, Integer.parseInt(repeats), Integer.parseInt(sets));

        dbHelper.updateExercise(String.valueOf(exercice.getId()), exercice);
    }

    private void getIntentData(){
        Intent intent = getIntent();
        if(intent.hasExtra("id") && intent.hasExtra("exName")){
            id = getIntent().getStringExtra("id");
            exName = getIntent().getStringExtra("exName");
            repeats = getIntent().getStringExtra("repeats");
            sets = getIntent().getStringExtra("sets");

            editTextExName.setText(exName);
            editTextRepeats.setText(repeats);
            editTextSets.setText(sets);
        } else {
            Toast.makeText(this, "No Data available", Toast.LENGTH_SHORT).show();
        }
    }

    public void onDelete(View view){
        confirmDialog();
    }
    private void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete exercise "+exName+"?");
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper dbHelper = new DBHelper(UpdateExercise.this);
                dbHelper.deleteExercise(id);
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