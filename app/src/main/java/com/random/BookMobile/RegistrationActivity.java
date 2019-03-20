package com.random.BookMobile;

/**
 * user register account
 * system verify the username and password are legal
 * check database for duplicate name
 */

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.random.BookMobile.Input_Validator.InputValidator;
import com.random.BookMobile.R;

import es.dmoral.toasty.Toasty;

public class RegistrationActivity extends AppCompatActivity{
    private EditText mUsername;
    private EditText mPassword;
    private EditText mEmail;
    private Button mRegiterSubmitBtn;
    private Button mCancelBtn;
    /**
     * need to import the database and check all the database methods
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mUsername = findViewById(R.id.usernameRegInput);
        mPassword = findViewById(R.id.passwordRegInput);
        mEmail = findViewById(R.id.emailRegInput);

        mRegiterSubmitBtn = findViewById(R.id.registerButton);
        mCancelBtn = findViewById(R.id.cancelButton);

        mRegiterSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String email = mEmail.getText().toString().trim();

                if(!checkAccountInfoEligibility(username, password, email))
                    Toasty.error(getApplicationContext(), "Invalid input. Please make sure to fill in all fields correctly.", Toast.LENGTH_SHORT).show();
                else
                    createNewAccount(username, password,email);

                mUsername.setText("");
                mPassword.setText("");
                mEmail.setText("");

              /*  if(!username.equals("") && !password.equals("") && !email.equals(""))
                    createNewAccount(username, password, email);
                else
                    Toasty.error(getApplicationContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();*/

            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackToLogin = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(goBackToLogin);
                RegistrationActivity.this.finish();
            }
        });
    }

    private boolean checkUsername(String Username) {          //returns 0 if Username doesnt exist, returns 1 if it does
        if (!InputValidator.ValidateUserNameInput(Username) && Username.length() <= 15 && Username.length() >= 6) {
            Toasty.error(this, "Error: the Username is not valid, it should contain alphabets and numbers only", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    private boolean checkPassword(String password){
        if(!InputValidator.ValidatePasswordInput(password) && password.length() >= 6){
            Toasty.error(this, "Error: the password is not valid, it should contain non-space characters with length longer than 6, and it should not contain spaces", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean checkEmail(String Email){          //returns 0 if Email doesnt exist, returns 1 if it does
        if(!InputValidator.ValidateEmailInput(Email) && Email.length()<=0){
            Toasty.error(this, "Error: the email is not valid", Toast.LENGTH_LONG).show();
            return false;
        }
       return true;
    } //end

    private boolean checkAccountInfoEligibility(String username, String password, String email){
        //To be completed later (for now don't check is duplicate usernames exist)
        if (checkUsername(username) && checkPassword(password) && checkEmail(email)){
            return true;
        }
        return false;
    }

    private void createNewAccount(String username, String password, String email){
            //transfer user input to preferences activity first
            Intent toPreferences = new Intent(this, PreferencesActivity.class);
            toPreferences.putExtra("newUser", username);
            toPreferences.putExtra("Pass", password);
            toPreferences.putExtra("Email", email);
            startActivity(toPreferences);
        }
    }

