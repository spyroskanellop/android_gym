package com.example.gymapp.activities;

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

import com.example.gymapp.DBHelper;
import com.example.gymapp.MainActivity;
import com.example.gymapp.R;
import com.example.gymapp.entities.Athlete;

public class UpdateAthleteActivity extends AppCompatActivity {

    private EditText editTextFirstName, editTextLastName, editTextDescription, editTextPhone;
    private TextView textView;
    private Button updateButton, deleteButton;

    private String firstName, lastName, description, id, phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_athlete);

        editTextFirstName = (EditText)findViewById(R.id.editTextFirstName2);
        editTextLastName = (EditText)findViewById(R.id.editTextLastName2);
        editTextDescription = (EditText)findViewById(R.id.editTextDescription2);
        editTextPhone = (EditText)findViewById(R.id.editTextPhone2);
        textView = (TextView) findViewById(R.id.textView2);
        updateButton = (Button) findViewById(R.id.updateButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        getIntentData();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(firstName +" "+lastName);
        }
    }

    public void onClick(View view){
        DBHelper dbHelper = new DBHelper(UpdateAthleteActivity.this);
        firstName = editTextFirstName.getText().toString();
        lastName = editTextLastName.getText().toString();
        description = editTextDescription.getText().toString();
        phone = editTextPhone.getText().toString();
        Athlete athlete = new Athlete(Integer.parseInt(id), firstName,lastName,Integer.parseInt(phone), description);

        dbHelper.updateAthlete(String.valueOf(athlete.getId()), athlete);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void getIntentData(){
        Intent intent = getIntent();
        if(intent.hasExtra("id") && intent.hasExtra("firstName")){
            id = getIntent().getStringExtra("id");
            phone = getIntent().getStringExtra("phone");
            firstName = getIntent().getStringExtra("firstName");
            lastName = getIntent().getStringExtra("lastName");
            description = getIntent().getStringExtra("description");

            editTextFirstName.setText(firstName);
            editTextLastName.setText(lastName);
            editTextPhone.setText(phone);
            editTextDescription.setText(description);
        } else {
            Toast.makeText(this, "No Data available", Toast.LENGTH_SHORT).show();
        }
    }
    public void onDelete(View view){
        confirmDialog();
    }
    private void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete athlete "+firstName+" "+lastName+"?");
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper dbHelper = new DBHelper(UpdateAthleteActivity.this);
                dbHelper.deleteAthlete(id);
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