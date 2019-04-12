package com.random.BookMobile;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.support.design.widget.TextInputLayout;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.random.BookMobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextInputLayout usernameWrapper = findViewById(R.id.usernameLogin);
        final TextInputLayout passwordWrapper = findViewById(R.id.passwordLogin);

        mRegisterBtn = findViewById(R.id.registerButton);
        mLoginBtn = findViewById(R.id.loginButton);
        mUsername = findViewById(R.id.usernameInput);
        mPassword = findViewById(R.id.passwordInput);

        loginSuccessIntent = new Intent(LoginActivity.this, MainActivity.class);
        pref = getSharedPreferences("user_details", MODE_PRIVATE);
        if (pref.contains("jwt_token")) {
            loginSuccessIntent.putExtra("id", 1);
            startActivity(loginSuccessIntent);
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jumpRegisterIntent = new Intent(getBaseContext(), RegistrationActivity.class);
                startActivity(jumpRegisterIntent);
                LoginActivity.this.finish();
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = mUsername.getText().toString().trim();
                password = mPassword.getText().toString().trim();
                if (!username.equals("") && !password.equals(""))

                    validateUser(username, password);
                else {
                    usernameWrapper.setError("This is a required field.");
                    passwordWrapper.setError("This is a required field");
                }
                mUsername.setText("");
                mPassword.setText("");
            }
        });
    }

    // sending to server for validation -> on response, transit to Explore Page
    public void validateUser(final String username, final String password) {
        mQueue = Volley.newRequestQueue(LoginActivity.this);

        //String url = "https://api.myjson.com/bins/huzyq";
        String url = "https://bookmobile-backend.herokuapp.com/user/login";
        final AlertDialog waitingDialog = new SpotsDialog.Builder()
                .setContext(LoginActivity.this)
                .setMessage("Validating User...")
                .setCancelable(false)
                .build();
        waitingDialog.show();

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_id", username);
            jsonBody.put("password", password);
            final String mRequestBody = jsonBody.toString();

            //POST REQUEST:
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                String preference = "";
                                String jwt_token = "";
                                String error = "";
                                Log.d("RESPONSE!!!!", response);
                                JSONObject validateObj = new JSONObject(response);

                                if (validateObj.has("jwt_token")) {
                                    jwt_token = validateObj.getString("jwt_token");
                                    Log.d("REGISTRATION", "JWT:" + jwt_token);
                                }

                                if (validateObj.has("error")) {
                                    waitingDialog.dismiss();
                                    error = validateObj.getString("error");
                                    Log.d("ERROR", "ERROR:" + error);
                                    Toasty.error(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                                }

                                if (jwt_token != null) {
                                    waitingDialog.dismiss();
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("username", username);
                                    editor.putString("password", password);
                                    editor.putString("jwt_token", jwt_token);
                                    editor.apply();
                                    Toasty.success(LoginActivity.this, "Welcome, " + username, Toast.LENGTH_SHORT, true).show();
                                    //loginSuccessIntent.putExtra("loginUser", username);
                                    loginSuccessIntent.putExtra("id", 1);
                                    startActivity(loginSuccessIntent);
                                    finish();
                                } else {
                                    Toasty.error(LoginActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {

                                waitingDialog.dismiss();
                                Toasty.error(LoginActivity.this, "Server Error!", Toast.LENGTH_SHORT, true).show();

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
                        Toasty.error(getApplicationContext(), "Server Error! Make sure credentials are correct!", Toast.LENGTH_LONG).show();
                    } else if (error instanceof NoConnectionError) {
                        waitingDialog.dismiss();
                        Toasty.error(getApplicationContext(), "Oops. No connection!", Toast.LENGTH_LONG).show();
                    } else if (error instanceof TimeoutError) {
                        waitingDialog.dismiss();
                        Toasty.error(getApplicationContext(), "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                    }
                    error.printStackTrace();
                }
            }) {
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
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



}

