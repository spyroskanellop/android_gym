package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddActivity extends AppCompatActivity {

    private EditText editTextFirstName, editTextLastName, editTextDescription, editTextPhone;
    private TextView textView;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTextFirstName = (EditText)findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText)findViewById(R.id.editTextLastName);
        editTextDescription = (EditText)findViewById(R.id.editTextDescription);
        editTextPhone = (EditText)findViewById(R.id.editTextPhone);
        textView = (TextView) findViewById(R.id.textView);
        saveButton = (Button) findViewById(R.id.savebutton);
    }

    public void onClick(View view){
        DBHelper dbHelper = new DBHelper(this);
        Athlete athlete = new Athlete();
        athlete.setFirstName(editTextFirstName.getText().toString().trim());
        athlete.setLastName(editTextLastName.getText().toString().trim());
        athlete.setDescription(editTextDescription.getText().toString().trim());
        athlete.setPhone(Integer.parseInt(editTextPhone.getText().toString().trim()));

        dbHelper.addAthlete(athlete);
    }
}