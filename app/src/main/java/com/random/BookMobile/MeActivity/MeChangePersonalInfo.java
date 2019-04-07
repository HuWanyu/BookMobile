package com.random.BookMobile.MeActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.random.BookMobile.LoginActivity;
import com.random.BookMobile.MainActivity;
import com.random.BookMobile.Input_Validator.InputValidator;
import com.random.BookMobile.MeActivity.UpdateAvatar;

import com.random.BookMobile.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;

public class MeChangePersonalInfo extends AppCompatActivity {

    private Button updateAvatar;
    private ImageView userAvatar;
    private Button Confirm;
    private EditText Password;
    private EditText Email;
    private String newPassword;
    private String newEmail;
    private RequestQueue mQueue;
    SharedPreferences prf;
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

        Password = findViewById(R.id.inputPassword);
        Email  = findViewById(R.id.inputEmail);

        prf = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        String email = prf.getString("email",null);
        final String jwt_token = prf.getString("jwt_token", null);
        Email.setText(email);

        userAvatar = findViewById(R.id.userAvatar);
        int userChoiceOfAvatar = getIntent().getIntExtra("User Choice of Avatar", 0);
        switch (userChoiceOfAvatar){
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

                newPassword  =  Password.getText().toString();
                newEmail = Email.getText().toString();

                updateUserData(newEmail, newPassword, jwt_token);

                if(!newEmail.equals(""))
//                    LoginActivity.UpdateUserInfo(AccountEntry.COLUMN_EMAIL,newEmail);


                if(!newPassword.equals(""))
 //                   LoginActivity.UpdateUserInfo(AccountEntry.COLUMN_PASSWORD,newPassword);

                if(InputValidator.ValidatePasswordInput(newPassword)) {
 //                   Toast.makeText(getBaseContext(), "password changed", Toast.LENGTH_LONG).show();LoginActivity.UpdateUserInfo(AccountEntry.COLUMN_PASSWORD, newPassword);
                }


                Intent goBack = new Intent(MeChangePersonalInfo.this, MainActivity.class);
                goBack.putExtra("id", 2);
                startActivity(goBack);
            }
    });


        }

        public void updateUserData(String email, String pass, final String jwt)
        {
            mQueue = Volley.newRequestQueue(MeChangePersonalInfo.this);

            String url ="https://bookmobile-backend.herokuapp.com/user/me";
            final AlertDialog waitingDialog = new SpotsDialog.Builder()
                    .setContext(MeChangePersonalInfo.this)
                    .setMessage("retrieving user data..")
                    .setCancelable(false)
                    .build();
            waitingDialog.show();

            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("user_email", email);
                jsonBody.put("password", pass);
                final String mRequestBody = jsonBody.toString();

                //PUT REQUEST:
                StringRequest request = new StringRequest(Request.Method.PUT, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    String error = null;

                                    Log.d("Response - Update User", response);
                                    JSONObject validateObj = new JSONObject(response);
                                    if (validateObj.has("error"))
                                        error = validateObj.getString("error");
                                    else {

                                    }

                                    if (error != null) {
                                        waitingDialog.dismiss();
                                        Toasty.error(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                                    } else {
                                        waitingDialog.dismiss();
                                    }

                                } catch (Exception e) {

                                    waitingDialog.dismiss();
                                    Toasty.error(getApplicationContext(), "Server Error!", Toast.LENGTH_SHORT, true).show();

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
                    /**
                     * Passing some request headers*
                     */
                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("Content-Type", "application/json");
                        headers.put("api-token", jwt);
                        return headers;
                    }

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


