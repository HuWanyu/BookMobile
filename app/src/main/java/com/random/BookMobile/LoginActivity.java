package com.random.BookMobile;

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

import com.random.BookMobile.R;
/**
 *  remember to import the database
 */

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private final String TAG = "LoginActivity";
    private Button mRegisterBtn;
    private Button mLoginBtn;
    private EditText mUsername;
    private EditText mPassword;

    private static String mCurrentUsername;
    private static String mCurrentPassword;
    private static String mCurrentEmail;
    private static String mCurrentAvatar;
    private static int mCurrentAvatarChoice;
    private static AccountDBHelper mAccountHelper;
    private static int mCurrentUserID;
    /**
     * Id to identity READ_CONTACTS permission request.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mRegisterBtn = findViewById(R.id.registerButton);
        mLoginBtn = findViewById(R.id.loginButton);
        mUsername = findViewById(R.id.usernameLogin);
        mPassword = findViewById(R.id.passwordLogin);

        mAccountHelper = new AccountDBHelper(this);
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jumpRegisterIntent = new Intent(getBaseContext(), RegistrationActivity.class);
                startActivity(jumpRegisterIntent);
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                validateUser(username, password);
            }
        });
    }
    private void validateUser(String username, String password){
        Log.d(TAG, "Login button is clicked");

            SQLiteDatabase accountDB = mAccountHelper.getReadableDatabase();
/**
 * need to use database to verify the user account
 */
            String[] accountProjection = {
                    AccountEntry._ID,
                    AccountEntry.COLUMN_USERNAME,
                    AccountEntry.COLUMN_PASSWORD,
                    AccountEntry.COLUMN_AVATAR,
                    AccountEntry.COLUMN_EMAIL
            };

            Cursor cursor = accountDB.query(
                    AccountEntry.TABLE_NAME,
                    accountProjection,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            int usernameIndex = cursor.getColumnIndex(AccountEntry.COLUMN_USERNAME);
            int passwordIndex = cursor.getColumnIndex(AccountEntry.COLUMN_PASSWORD);
            int emailIndex = cursor.getColumnIndex(AccountEntry.COLUMN_EMAIL);
            int avatarIndex = cursor.getColumnIndex(AccountEntry.COLUMN_AVATAR);
            int userIDIndex = cursor.getColumnIndex(AccountEntry._ID);

            while (cursor.moveToNext()) {
                String currentUsername = cursor.getString(usernameIndex);
                String currentPassword = cursor.getString(passwordIndex);
                int currentAvatarChoice = cursor.getInt(avatarIndex);
                String currentEmail = cursor.getString(emailIndex);
                int currentUserID = cursor.getInt(userIDIndex);
                Log.d(TAG, "current username is " + currentUsername + " current password is "+currentPassword);


                if (username.equals(currentUsername) && password.equals(currentPassword)) {
                    mCurrentUsername = currentUsername;
                    mCurrentPassword = currentPassword;
                    mCurrentAvatarChoice = currentAvatarChoice;
                    mCurrentEmail = currentEmail;
                    mCurrentUserID = currentUserID;
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent loginSuccessIntent = new Intent(this, MainActivity.class);
                    startActivity(loginSuccessIntent);
                }
                else{
                    Toast.makeText(this, "Username not found or username and password does not match.", Toast.LENGTH_SHORT).show();
                }
            }


    }

    public static void UpdateUserInfo(String updateType, String updateValue){
        SQLiteDatabase accountDB = mAccountHelper.getReadableDatabase();

        String[] accountProjection = {
                AccountEntry._ID,
                AccountEntry.COLUMN_USERNAME,
                AccountEntry.COLUMN_PASSWORD,
                AccountEntry.COLUMN_EMAIL,
                AccountEntry.COLUMN_AVATAR,
        };

        Cursor cursor = accountDB.query(
                AccountEntry.TABLE_NAME,
                accountProjection,
                null,
                null,
                null,
                null,
                null
        );
        int usernameIndex = cursor.getColumnIndex(AccountEntry.COLUMN_USERNAME);
        int idIndex = cursor.getColumnIndex(AccountEntry._ID);
        while(cursor.moveToNext()){
            if (cursor.getString(usernameIndex).equals(mCurrentUsername)){
                int id = cursor.getInt(idIndex);
                ContentValues update = new ContentValues();
                if(updateType==AccountEntry.COLUMN_AVATAR)
                    update.put(updateType, Integer.parseInt(updateValue));
                else
                    update.put(updateType,updateValue);
                accountDB.update(AccountEntry.TABLE_NAME, update, AccountEntry._ID  + " = " + String.valueOf(id), null);
            }

        }
        if (updateType == AccountEntry.COLUMN_EMAIL){
            updateEmail(updateValue);
        }
        if (updateType == AccountEntry.COLUMN_AVATAR){
            updateAvatar(Integer.parseInt(updateValue));
        }

    }

    public static String getUsername(){
        return mCurrentUsername;
    }

    public static String getPassword(){
        return mCurrentPassword;
    }

    public static int getAvatarChoice(){return mCurrentAvatarChoice;}

    public static String getEmail(){return mCurrentEmail;}

    public static int getUserID(){return mCurrentUserID;}


    private static void updateEmail(String email){
        mCurrentEmail = email;
    }
    private static void updateAvatar(int Avatar){
        mCurrentAvatarChoice = Avatar;
    }

}

