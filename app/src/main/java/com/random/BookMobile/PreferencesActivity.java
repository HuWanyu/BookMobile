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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;

public class PreferencesActivity extends AppCompatActivity {

    private Button signUpButton;
    private Button backButton;
    private CheckBox cbFiction;
    private CheckBox cbPhil;
    private CheckBox cbSciFi;
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
        cbFiction = findViewById(R.id.checkBoxFi);
        cbPhil = findViewById(R.id.checkBoxPhil);
        cbSciFi = findViewById(R.id.checkBoxSciFi);
        username = getIntent().getStringExtra("newUser");
        password = getIntent().getStringExtra("Pass");
        email = getIntent().getStringExtra("Email");


        Toasty.success(this, "Hello "+username+", please select your favorite genres", Toast.LENGTH_SHORT).show();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbFiction.isChecked())
                {
                    preference = cbFiction.getText().toString();

                    Toasty.info(getApplicationContext(), preference + " selected", Toast.LENGTH_SHORT).show();
                }

                if(cbPhil.isChecked())
                {
                   preference= cbPhil.getText().toString();
                    Toasty.info(getApplicationContext(), preference+" selected", Toast.LENGTH_SHORT).show();
                }

                if(cbSciFi.isChecked())
                {
                    // testing git merge
                   preference= cbSciFi.getText().toString();
                    Toasty.info(getApplicationContext(), preference+" selected", Toast.LENGTH_SHORT).show();
                }
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
        //String url = "https://private-a3ace9-bookmobile2.apiary-mock.com/user/"+username;
       //String url = "https://wordcount-stageee.herokuapp.com/user";
        String url = "https://api.myjson.com/bins/13jrwu";
        final AlertDialog waitingDialog = new SpotsDialog.Builder()
                .setContext(PreferencesActivity.this)
                .setMessage("Creating User...")
                .setCancelable(false)
                .build();
        waitingDialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("RESPONSE", response.toString());
                            // JSONObject validateObj = response.getJSONObject("loginValid");
                            String user_id = response.getString("user_id");
                            String user_email = response.getString("user_email");
                            String user_credits = response.getString("credits");
                            // JSONArray listedBooks = response.getJSONArray("books_listed");

                            Log.d("LOGIN STATUS", "Username:" + user_id);

                            if (user_id.equals("bookmobileuser")) {
                                waitingDialog.dismiss();
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("username", username);
                                editor.putString("password", password);
                                editor.putString("email", user_email);
                                editor.putString("credits", user_credits);
                                // editor.putString("listed books", listedBooks.toString());
                                editor.apply();
                                Toasty.success(PreferencesActivity.this, "Welcome, " + username, Toast.LENGTH_SHORT, true).show();
                                Intent regSuccessIntent = new Intent(PreferencesActivity.this, MainActivity.class);
                                regSuccessIntent.putExtra("id",1);
                                startActivity(regSuccessIntent);
                                finish();
                            }
                            else
                            {
                                Toasty.error(PreferencesActivity.this,"Error!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {

                            waitingDialog.dismiss();
                            Toasty.error(PreferencesActivity.this, "Unknown Error!", Toast.LENGTH_SHORT, true).show();

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
        /*StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RESPONSE!!!!", response.toString());
                            JSONObject validateObj = new JSONObject(response.toString());
                            String user_id = validateObj.getString("user_id");
                            Log.d("REGISTRATION", "Username:" + user_id);

                            if (user_id.equals("bookmobileuser")) {
                                waitingDialog.dismiss();
                                Intent regSuccessIntent = new Intent(PreferencesActivity.this, MainActivity.class);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("username", username);
                                editor.putString("password", password);
                                editor.apply();
                                Toasty.success(PreferencesActivity.this, "Welcome, " + username, Toast.LENGTH_SHORT, true).show();
                                //loginSuccessIntent.putExtra("loginUser", username);
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
        protected Map<String, String> getParams() throws AuthFailureError
        {
            Map<String, String> params = new HashMap<>();
            params.put("user_id", username);
            params.put("user_email", email);
            params.put("password", password);
            params.put("credits", "100");
            params.put("preference", preference);
            return params;
        }
    };*/
        mQueue.add(request);

    }
}
