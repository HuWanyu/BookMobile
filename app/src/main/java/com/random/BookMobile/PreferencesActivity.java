package com.random.BookMobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class PreferencesActivity extends AppCompatActivity {

    private Button signUpButton;
    private Button backButton;
    private CheckBox cbFiction;
    private CheckBox cbPhil;
    private CheckBox cbSciFi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        signUpButton = findViewById(R.id.buttonSignUp);
        backButton = findViewById(R.id.buttonBack);

        cbFiction = findViewById(R.id.checkBoxFi);
        cbPhil = findViewById(R.id.checkBoxPhil);
        cbSciFi = findViewById(R.id.checkBoxSciFi);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbFiction.isChecked())
                {
                    String fiction = cbFiction.getText().toString();
                    Toast.makeText(getApplicationContext(), fiction+" selected", Toast.LENGTH_SHORT).show();
                }

                if(cbPhil.isChecked())
                {
                    String phil = cbPhil.getText().toString();
                    Toast.makeText(getApplicationContext(), phil+" selected", Toast.LENGTH_SHORT).show();
                }

                if(cbSciFi.isChecked())
                {
                    String scifi = cbSciFi.getText().toString();
                    Toast.makeText(getApplicationContext(), scifi+" selected", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(getApplicationContext(), "Account Successfully Created", Toast.LENGTH_SHORT).show();
                Intent jumpToExplore = new Intent(getBaseContext(), MainActivity.class);
                startActivity(jumpToExplore);
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
