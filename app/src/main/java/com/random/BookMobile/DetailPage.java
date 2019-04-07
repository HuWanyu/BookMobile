package com.random.BookMobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.random.BookMobile.Fragments_Bar.ChatFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class DetailPage extends AppCompatActivity {

    private TextView giverNameText, bookName, bookDescription, timeToMeet, costOfBook;
    private ImageView image;
    int numTakers;
    private RequestQueue mQueue;
    private Button eventdetailchat;
    SharedPreferences prf;
    String jwt_token;

    @Override
    public void onBackPressed() {
        Intent goBack = new Intent(DetailPage.this, MainActivity.class);
        goBack.putExtra("id", 1);
        startActivity(goBack);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        prf = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        jwt_token = prf.getString("jwt_token",null);

        Intent intent = getIntent();
        String giverName = intent.getStringExtra("Giver Name");
        final String phoneNumber = intent.getStringExtra("Contact Number");
        String cost = String.valueOf(intent.getIntExtra("Cost",0));
        Log.v("COst", cost);
        String timing = intent.getStringExtra("Timing");
        final String title = intent.getExtras().getString("title");
        String summary = intent.getExtras().getString("desc");
        giverNameText = findViewById(R.id.giver_name);
        bookName = findViewById(R.id.detailpage_name);
        bookDescription = findViewById(R.id.detailpage_description);
        timeToMeet = findViewById(R.id.timeToMeet);
        costOfBook = findViewById(R.id.costOfBook);

        giverNameText.setText(giverName);
        bookName.setText(title);
        bookDescription.setText(summary);
        timeToMeet.append(timing);
        costOfBook.append(cost);

        image = findViewById(R.id.detailpage_imageBook);
        image.setImageResource(R.mipmap.closed_book);


        eventdetailchat = (Button) findViewById(R.id.detailpage_chat);
        eventdetailchat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getTakerCount(title);
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"+phoneNumber));
                startActivity(sendIntent);

            }
        });

    }

    public void getTakerCount(final String title)
    {
        mQueue = Volley.newRequestQueue(this);
        String url ="";
        //String url = "https://api.myjson.com/bins/192ib6";

        url = "https://bookmobile-backend.herokuapp.com/book/"+title.replaceAll(" ","%20");
        Log.v("URL", url);


        //GET REQUEST:
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String error = null;
                            Log.d("RESPONSE!!!!", response);
                            JSONObject bookObj = new JSONObject(response);
                            if(bookObj.has("error"))
                                error = bookObj.getString("error");
                            if(error!=null) {

                                Toasty.error(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                numTakers = bookObj.getInt("num_book_takers");
                                //increment takers
                                incrementTakerCount(title);
                            }

                        } catch (Exception e) {
                            Toasty.error(getApplicationContext(), "Server Error!", Toast.LENGTH_SHORT, true).show();

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toasty.error(getApplicationContext(), "Oops. Network Error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toasty.error(getApplicationContext(), "Oops. Server Error!", Toast.LENGTH_LONG).show();
                }  else if (error instanceof NoConnectionError) {
                    Toasty.error(getApplicationContext(), "Oops. No connection!", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toasty.error(getApplicationContext(), "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                }
                error.printStackTrace();
            }
        })
        {
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("jwt_token", jwt_token);
                return headers;
            }
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };
        mQueue.add(request);
    }

    public void incrementTakerCount(String title) {
        mQueue = Volley.newRequestQueue(this);
        //numTakers = 0;
        numTakers++;
        String url = "https://bookmobile-backend.herokuapp.com/book/";
        Log.v("URL", url);
        Log.v("Number of Takers(increment)", String.valueOf(numTakers));
        Log.v("Increment test", String.valueOf(numTakers));
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("book_name", title);
            jsonBody.put("num_book_takers", numTakers);
            final String mRequestBody = jsonBody.toString();
            //GET REQUEST:
            StringRequest request = new StringRequest(Request.Method.PUT, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                String error = null;
                                Log.d("RESPONSE!!!!", response);
                                JSONObject bookObj = new JSONObject(response);
                                if (bookObj.has("error"))
                                    error = bookObj.getString("error");
                                if (error != null) {
                                    Toasty.error(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                                } else {
                                    numTakers = bookObj.getInt("num_book_takers");
                                    Log.v("NUMBER OF TAKERS", String.valueOf(numTakers));

                                }

                            } catch (Exception e) {
                                Toasty.error(getApplicationContext(), "Server Error!", Toast.LENGTH_SHORT, true).show();

                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NetworkError) {
                        Toasty.error(getApplicationContext(), "Oops. Network Error!", Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toasty.error(getApplicationContext(), "Oops. Server Error!", Toast.LENGTH_LONG).show();
                    } else if (error instanceof NoConnectionError) {
                        Toasty.error(getApplicationContext(), "Oops. No connection!", Toast.LENGTH_LONG).show();
                    } else if (error instanceof TimeoutError) {
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
                    headers.put("jwt_token", jwt_token);
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
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }



}
