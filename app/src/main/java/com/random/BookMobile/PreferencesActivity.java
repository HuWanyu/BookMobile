package com.random.BookMobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

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

        final String username = getIntent().getStringExtra("newUser");
        String password = getIntent().getStringExtra("Pass");
        String email = getIntent().getStringExtra("Email");

        Toasty.success(this, "Hello "+username+", please select your favorite genres", Toast.LENGTH_SHORT).show();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbFiction.isChecked())
                {
                    String fiction = cbFiction.getText().toString();
                    Toasty.info(getApplicationContext(), fiction + " selected", Toast.LENGTH_SHORT).show();
                }

                if(cbPhil.isChecked())
                {
                    String phil = cbPhil.getText().toString();
                    Toasty.info(getApplicationContext(), phil+" selected", Toast.LENGTH_SHORT).show();
                }

                if(cbSciFi.isChecked())
                {
                    // testing git merge
                    String scifi = cbSciFi.getText().toString();
                    Toasty.info(getApplicationContext(), scifi+" selected", Toast.LENGTH_SHORT).show();
                }

                Toasty.success(getApplicationContext(), "Welcome to BookMobile, "+username, Toast.LENGTH_LONG).show();
                Intent jumpToExplore = new Intent(getBaseContext(), MainActivity.class);
                jumpToExplore.putExtra("newUser", username);
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
