package com.random.BookMobile;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;

public class PreferencesActivity extends AppCompatActivity {

    private Button signUpButton;
    private Button backButton;
    private RadioGroup radio;
    private RadioButton rbFiction;
    private RadioButton rbPhil;
    private RadioButton rbSciFi;
    private RadioButton rbFantasy;
    private RadioButton rbHistory;
    private RequestQueue mQueue;

    SharedPreferences pref;

    String username;
    String password;
    String email;
    String preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        signUpButton = findViewById(R.id.buttonSignUp);
        backButton = findViewById(R.id.buttonBack);

        pref = getSharedPreferences("user_details",MODE_PRIVATE);

        radio = findViewById(R.id.radio);

        rbFiction = findViewById(R.id.checkBoxFi);
        rbPhil = findViewById(R.id.checkBoxPhil);
        rbSciFi = findViewById(R.id.checkBoxSciFi);
        rbFantasy = findViewById(R.id.checkBoxFant);
        rbHistory = findViewById(R.id.checkBoxHist);

        username = getIntent().getStringExtra("newUser");
        password = getIntent().getStringExtra("Pass");
        email = getIntent().getStringExtra("Email");

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.checkBoxFi:
                        preference = "Fiction";
                        break;
                    case R.id.checkBoxHist:
                        preference = "History";
                        break;
                    case R.id.checkBoxFant:
                        preference = "Fantasy";
                        break;
                    case R.id.checkBoxPhil:
                        preference = "Philosophy";
                        break;
                    case R.id.checkBoxSciFi:
                        preference = "Sci-Fi";
                        break;
                }
            }
        });


        Toasty.success(this, "Hello "+username+", please select your favorite genres", Toast.LENGTH_SHORT).show();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createUser();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void createUser()
    {
        mQueue = Volley.newRequestQueue(PreferencesActivity.this);
        // backup url
       // String url = "https://api.myjson.com/bins/13jrwu";
        String url = "https://bookmobile-backend.herokuapp.com/user/";
        final AlertDialog waitingDialog = new SpotsDialog.Builder()
                .setContext(PreferencesActivity.this)
                .setMessage("Creating User...")
                .setCancelable(false)
                .build();
            waitingDialog.show();

            try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_id", username);
            jsonBody.put("user_email", email);
            jsonBody.put("password", password);
            jsonBody.put("credits", "100");
            jsonBody.put("preference", preference);
            final String mRequestBody = jsonBody.toString();

            //POST REQUEST:
            StringRequest request = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        String jwt_token = "";
                        String error = "";
                        Log.d("RESPONSE!!!!", response.toString());
                        JSONObject validateObj = new JSONObject(response.toString());
                        if(validateObj.has("jwt_token")) {
                            jwt_token = validateObj.getString("jwt_token");
                            Log.d("REGISTRATION", "JWT:" + jwt_token);
                        }
                        if(validateObj.has("error")) {
                            waitingDialog.dismiss();
                            error = validateObj.getString("error");
                            Toasty.error(PreferencesActivity.this,  error, Toast.LENGTH_SHORT).show();
                        }

                        if (jwt_token != null) {
                            waitingDialog.dismiss();
                            Intent regSuccessIntent = new Intent(PreferencesActivity.this, MainActivity.class);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("username", username);
                            editor.putString("password", password);
                            editor.putString("preference", preference);
                            editor.putString("jwt_token", jwt_token);
                            editor.apply();
                            Toasty.success(PreferencesActivity.this, "Welcome, " + username, Toast.LENGTH_SHORT, true).show();
                            //loginSuccessIntent.putExtra("loginUser", username);
                            regSuccessIntent.putExtra("id", 1);
                            startActivity(regSuccessIntent);
                            finish();
                        }
                        else
                        {
                            Toasty.error(PreferencesActivity.this,"Error!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {

                        waitingDialog.dismiss();
                        Toasty.error(PreferencesActivity.this, "Server Error!", Toast.LENGTH_SHORT, true).show();

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
            })

            {
            @Override
            public String getBodyContentType() {
            return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
            try {
                return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
            } catch (UnsupportedEncodingException uee) {
                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                return null;
            }
            }
            };
            mQueue.add(request);
            }
            catch(JSONException e)
            {
            e.printStackTrace();
            }

    }
}
