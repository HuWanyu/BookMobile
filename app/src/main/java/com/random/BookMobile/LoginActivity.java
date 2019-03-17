package com.random.BookMobile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.random.BookMobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
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

    public String username;
    public String password;
    SharedPreferences pref;
    Intent loginSuccessIntent;
    private RequestQueue mQueue;

    private static String mCurrentUsername;
    private static String mCurrentPassword;
    private static String mCurrentEmail;
    private static String mCurrentAvatar;
    private static int mCurrentAvatarChoice;

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

        loginSuccessIntent = new Intent(LoginActivity.this, MainActivity.class);
        pref = getSharedPreferences("user_details",MODE_PRIVATE);
        if(pref.contains("username") && pref.contains("password")) {
            startActivity(loginSuccessIntent);
        }

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
                username = mUsername.getText().toString().trim();
                password = mPassword.getText().toString().trim();
                if(!username.equals("") && !password.equals(""))
                    validateUser(username, password);
                else
                    Toasty.error(getApplicationContext(), "Please fill in both fields!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // sending to server for validation -> on response, transit to Explore Page
    public void validateUser(final String username, final String password){
        mQueue = Volley.newRequestQueue(LoginActivity.this);
       // String url = "https://api.myjson.com/bins/1ayd4u";
        String url = "https://private-a3ace9-bookmobile2.apiary-mock.com/user/"+username;

        final AlertDialog waitingDialog = new SpotsDialog.Builder()
                .setContext(LoginActivity.this)
                .setMessage("Validating User...")
                .setTheme(R.style.Custom)
                .setCancelable(false)
                .build();
        waitingDialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            try {
                               // JSONObject validateObj = response.getJSONObject("loginValid");
                                String user_id = response.getString("user_id");
                                Log.d("LOGIN STATUS", "Username:" + user_id);

                                if (user_id.equals("bookmobileuser")) {
                                    waitingDialog.dismiss();
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("username", username);
                                    editor.putString("password", password);
                                    editor.apply();
                                    Toasty.success(LoginActivity.this, "Welcome back, " + username, Toast.LENGTH_SHORT, true).show();
                                    //loginSuccessIntent.putExtra("loginUser", username);
                                    startActivity(loginSuccessIntent);
                                }
                                else
                                {
                                    Toasty.error(LoginActivity.this,"Wrong user!", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {

                                waitingDialog.dismiss();
                                Toasty.error(LoginActivity.this, "Unknown Error!", Toast.LENGTH_SHORT, true).show();

                                e.printStackTrace();
                            }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    waitingDialog.dismiss();
                    Toasty.error(getApplicationContext(), "Oops. Network Error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    waitingDialog.dismiss();
                    Toasty.error(getApplicationContext(), "Oops. Server Error!", Toast.LENGTH_LONG).show();
                }  else if (error instanceof NoConnectionError) {
                    waitingDialog.dismiss();
                    Toasty.error(getApplicationContext(), "Oops. No connection!", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    waitingDialog.dismiss();
                    Toasty.error(getApplicationContext(), "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                }
                error.printStackTrace();
            }
        });
        mQueue.add(request);


 /*       Log.d(TAG, "Login button is clicked"); //un-comment after database is set up
        SQLiteDatabase accountDB = mAccountHelper.getReadableDatabase();

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
  */
            }
  /*          else{
                Toast.makeText(this, "Username not found or username and password does not match.", Toast.LENGTH_SHORT).show();
            }
        }
    }
*/
    public static void UpdateUserInfo(String updateType, String updateValue){
 /*      SQLiteDatabase accountDB = mAccountHelper.getReadableDatabase();

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
*/
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

