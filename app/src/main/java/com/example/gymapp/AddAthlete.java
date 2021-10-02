package com.example.gymapp;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddAthlete extends AppCompatActivity {

    private EditText editTextFirstName, editTextLastName, editTextDescription, editTextPhone;
    private TextView textView;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_athlete);

        editTextFirstName = (EditText)findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText)findViewById(R.id.editTextLastName);
        editTextDescription = (EditText)findViewById(R.id.editTextDescription);
        editTextPhone = (EditText)findViewById(R.id.editTextPhone);
        textView = (TextView) findViewById(R.id.textView);
        saveButton = (Button) findViewById(R.id.savebutton);
    }

    public void onClick(View view) throws InterruptedException {
        DBHelper dbHelper = new DBHelper(this);
        Athlete athlete = new Athlete();
        if(editTextPhone.getText().toString().equals("") || editTextPhone.getText().toString().length() == 10){
            if(editTextPhone.getText().toString().equals("")){
                editTextPhone.setText("0");
            }
            athlete.setFirstName(editTextFirstName.getText().toString().trim());
            athlete.setLastName(editTextLastName.getText().toString().trim());
            athlete.setDescription(editTextDescription.getText().toString().trim());
            athlete.setPhone(Long.parseLong(editTextPhone.getText().toString().trim()));

            dbHelper.addAthlete(athlete);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            Toast.makeText(this, "Phone must be either empty or 10 digit", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }
    }
}