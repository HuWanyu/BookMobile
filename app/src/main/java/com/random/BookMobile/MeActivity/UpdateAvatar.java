package com.random.BookMobile.MeActivity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.random.BookMobile.LoginActivity;
import com.random.BookMobile.R;
import com.random.BookMobile.MeActivity.MeChangePersonalInfo;

public class UpdateAvatar extends AppCompatActivity {

    private Button Confirm;
    private ImageButton one;
    private ImageButton two;
    private ImageButton three;
    private ImageButton four;
    private String userChoiceofAvatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_avatar);

        one = findViewById(R.id.books1button);
        two = findViewById(R.id.books2button);
        three = findViewById(R.id.books3button);
        four = findViewById(R.id.books4button);

        one.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                userChoiceofAvatar = "1";
            }
        });
        two.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                userChoiceofAvatar = "2";
            }
        });
        three.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                userChoiceofAvatar = "3";
            }
        });
        four.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                userChoiceofAvatar = "4";
            }
        });



        Confirm = findViewById(R.id.ConfirmChangeAvatar);
        Confirm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
//                LoginActivity.UpdateUserInfo(AccountContract.AccountEntry.COLUMN_AVATAR,userChoiceofAvatar);
                Intent Back = new Intent(UpdateAvatar.this, MeChangePersonalInfo.class);
                startActivity(Back);
            }
        });



    }




}
