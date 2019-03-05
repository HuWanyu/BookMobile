package com.random.BookMobile.MeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.random.BookMobile.LoginActivity;
import com.random.BookMobile.MainActivity;
import com.random.BookMobile.Input_Validator.InputValidator;
import com.random.BookMobile.MeActivity.UpdateAvatar;

import com.random.BookMobile.R;

public class MeChangePersonalInfo extends AppCompatActivity {

    private Button updateAvatar;
    private ImageView userAvatar;
    private Button Confirm;
    private EditText Password;
    private EditText Email;
    private String newPassword;
    private String newEmail;
    //private DatabaseHelp db = new DatabaseHelp(this);

    @Override
    public void onBackPressed() {
        Intent goBack = new Intent(MeChangePersonalInfo.this, MainActivity.class);
        goBack.putExtra("id", 2);
        startActivity(goBack);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_personal_info);

        userAvatar = findViewById(R.id.userAvatar);

        switch (LoginActivity.getAvatarChoice()){
            case 1:
                userAvatar.setImageResource(R.drawable.books1);
                break;
            case 2:
                userAvatar.setImageResource(R.drawable.books2);
                break;
            case 3:
                userAvatar.setImageResource(R.drawable.books3);
                break;
            case 4:
                userAvatar.setImageResource(R.drawable.books4);
                break;
        }


        updateAvatar=findViewById(R.id.changeAvatar);
        updateAvatar.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent toChangeAvatar = new Intent(MeChangePersonalInfo.this, UpdateAvatar.class);
                    startActivity(toChangeAvatar);
                }
            });

        Confirm = findViewById(R.id.ConfirmChangePersonalInfo);
        Confirm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Password = findViewById(R.id.inputPassword);
                Email  = findViewById(R.id.inputEmail);
                newPassword  =  Password.getText().toString();
                newEmail = Email.getText().toString();

                if(!newEmail.equals(""))
                    LoginActivity.UpdateUserInfo(AccountEntry.COLUMN_EMAIL,newEmail);


                if(!newPassword.equals(""))
                    LoginActivity.UpdateUserInfo(AccountEntry.COLUMN_PASSWORD,newPassword);

                if(InputValidator.ValidatePasswordInput(newPassword)) {
                    Toast.makeText(getBaseContext(), "password changed", Toast.LENGTH_LONG).show();LoginActivity.UpdateUserInfo(AccountEntry.COLUMN_PASSWORD, newPassword);
                }


                Intent goBack = new Intent(MeChangePersonalInfo.this, MainActivity.class);
                goBack.putExtra("id", 2);
                startActivity(goBack);
            }
    });


        }

    }


