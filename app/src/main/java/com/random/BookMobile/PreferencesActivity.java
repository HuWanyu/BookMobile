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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

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
          signUpUser();
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void signUpUser()
    {
        mQueue = Volley.newRequestQueue(PreferencesActivity.this);
        // String url = "https://api.myjson.com/bins/1ayd4u";
        String url = "https://private-a3ace9-bookmobile2.apiary-mock.com/user/"+username;

        final AlertDialog waitingDialog = new SpotsDialog.Builder()
                .setContext(PreferencesActivity.this)
                .setMessage("Creating User...")
                .setTheme(R.style.Custom)
                .setCancelable(false)
                .build();
        waitingDialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("RESPONSE!!!!", response.toString());
                            // JSONObject validateObj = response.getJSONObject("loginValid");
                            String user_id = response.getString("user_id");
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

                error.printStackTrace();
            }
        });
        mQueue.add(request);

    }
}
