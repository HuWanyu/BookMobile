package com.random.BookMobile.Fragments_Bar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Intent;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.random.BookMobile.GeneralBookDetailFragment;
import com.random.BookMobile.LoginActivity;
import com.random.BookMobile.MainActivity;
import com.random.BookMobile.PreferencesActivity;
import com.random.BookMobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;


public class AddListingFragment extends Fragment {
    int REQUEST_CODE = 0;
    String jwt_token,  ampm, time;
    int userid;
    MaterialButton addListingButton, dateButton;
    String title, condition, location, timing;
    TextView pricingText, locationText, bookTitle;
    Spinner conditionSpinner, amPmSpinner, timeSpinner;
    int price, numBooks;
    private RequestQueue mQueue;
    SharedPreferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.add_listing_fragment, container, false);
        Resources res = getResources();

        //getting SharedPrefs
        pref = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        if(pref.contains("jwt_token")) {
           jwt_token = pref.getString("jwt_token",null);
        }
        pullUserid(jwt_token);

        // Book Title
        bookTitle = view.findViewById(R.id.bookTitleText);
        //Location
        locationText = view.findViewById(R.id.locText);

        //setting up dropdown list for book condition
        String[] conditionList = res.getStringArray(R.array.conArray);
        conditionSpinner = view.findViewById(R.id.conSpinner);
        ArrayAdapter<String> conAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, conditionList);
        conditionSpinner.setAdapter(conAdapter);

        //setting up dropdown list for am/pm
        String[] amPmList = res.getStringArray(R.array.ampmarray);
        amPmSpinner = view.findViewById(R.id.ampmSpinner);
        ArrayAdapter<String> amPmAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, amPmList);
        amPmSpinner.setAdapter(amPmAdapter);


        //setting up dropdown list for timings
        Integer[] timeList = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        timeSpinner = view.findViewById(R.id.timeSpinner);
        ArrayAdapter<Integer> timeAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, timeList);
        timeSpinner.setAdapter(timeAdapter);



       dateButton = view.findViewById(R.id.datePickerButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance(TimeZone.getDefault()); // Get current date

                // Create the DatePickerDialog instance
                DatePickerDialog datePicker = new DatePickerDialog(getContext(),
                        R.style.Theme_AppCompat_Light_Dialog, datePickerListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH));
                datePicker.setCancelable(false);
                datePicker.setTitle("Select the date");
                datePicker.show();
            }
        });

        // price
        pricingText = view.findViewById(R.id.priceText);

        //add listing button
        addListingButton = view.findViewById(R.id.listButton);
        addListingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //get all the values in input fields
                    title = bookTitle.getText().toString().trim();
                    condition = conditionSpinner.getSelectedItem().toString().trim();
                    ampm = amPmSpinner.getSelectedItem().toString().trim();
                    time = timeSpinner.getSelectedItem().toString().trim();
                    location = locationText.getText().toString().trim();
                    if(pricingText.getText().toString().length()!=0)
                    price = Integer.valueOf(pricingText.getText().toString().trim());
                    else
                        Toasty.error(getContext(), "Please set a date!", Toast.LENGTH_SHORT).show();
                    if(dateButton.getText()!="Set Date")
                    timing = dateButton.getText().toString() + " " + time + " " + ampm;
                    else
                        Toasty.error(getContext(), "Please set a date!", Toast.LENGTH_SHORT).show();

                    if(title!=null && location!=null)
                    //send them to server here
                    addListingToServer(title, condition, location, timing, price);
                    else
                        Toasty.error(getContext(), "Please remember to fill in all fields!", Toast.LENGTH_LONG).show();


            }
        });

        return view;

    }

    // Listener
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
            dateButton.setText(day1 + "/" + month1 + "/" + year1);

        }
    };

    public void setUserId(int id)
    {
        this.userid = id;
    }
    public int getUserId()
    {
        return userid;
    }

    public void pullUserid(final String jwt){
        mQueue = Volley.newRequestQueue(getActivity());
        String url ="https://bookmobile-backend.herokuapp.com/user/me";

        //POST REQUEST:
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject validateObj = new JSONObject(response);
                            int id = validateObj.getInt("id");
                            setUserId(id);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {

                    Toasty.error(getContext(), "Oops. Network Error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {

                    Toasty.error(getContext(), "Oops. Server Error!", Toast.LENGTH_LONG).show();
                }  else if (error instanceof NoConnectionError) {

                    Toasty.error(getContext(), "Oops. No connection!", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {

                    Toasty.error(getContext(), "Oops. Timeout error!", Toast.LENGTH_LONG).show();
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
                headers.put("api-token", jwt);
                return headers;
            }
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };

        mQueue.add(request);

    }
    public void addListingToServer(final String title, final String condition, final String location, final String timing, final int price){

        mQueue = Volley.newRequestQueue(getActivity());

        Log.v("USER", String.valueOf(getUserId()));
        //String url = "https://api.myjson.com/bins/192ib6";
        String url ="https://bookmobile-backend.herokuapp.com/listing/";
        final AlertDialog waitingDialog = new SpotsDialog.Builder()
                .setContext(getActivity())
                .setMessage("Adding New Book...")
                .setCancelable(false)
                .build();
        waitingDialog.show();
        int book_id = 0;

        if(title.equals("Crime & Punishment"))
            book_id=3;
        else if(title.equals("Lord of The Flies"))
            book_id=2;
        else if(title.equals("Pride and Prejudice"))
            book_id=1;
        else if(title.equals("1984"))
            book_id=4;

        try{
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("book_condition", 10);
            jsonBody.put("book_price_credit", price);
            jsonBody.put("book_id", book_id);
            jsonBody.put("giver_id", getUserId());
            jsonBody.put("meeting_location", location);
            jsonBody.put("meeting_time", timing);
            jsonBody.put("completion_status", "1");

            final String mRequestBody = jsonBody.toString();

            //POST REQUEST:
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                String error = null;
                                Log.d("RESPONSE!!!!", response);
                                JSONObject validateObj = new JSONObject(response);
                                if(validateObj.has("error"))
                                    error = validateObj.getString("error");

                                if(error!=null) {
                                    waitingDialog.dismiss();
                                    Toasty.error(getContext(), error, Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    waitingDialog.dismiss();
                                    Toasty.success(getContext(), "Listing successful!", Toast.LENGTH_SHORT).show();

                                    //set all fields to empty
                                    bookTitle.setText("");
                                    locationText.setText("");
                                    dateButton.setText("Set date");
                                    pricingText.setText("");
                                    conditionSpinner.setSelection(0);
                                    amPmSpinner.setSelection(0);
                                    timeSpinner.setSelection(0);

                                    //get copies available
                                    getBookCopies(title);
                                }

                            } catch (Exception e) {

                                waitingDialog.dismiss();
                                Toasty.error(getContext(), "Server Error!", Toast.LENGTH_SHORT, true).show();

                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NetworkError) {
                        waitingDialog.dismiss();
                        Toasty.error(getContext(), "Oops. Network Error!", Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        waitingDialog.dismiss();
                        Toasty.error(getContext(), "The server had issues processing the data. Please make sure valid details are entered.", Toast.LENGTH_LONG).show();
                        bookTitle.setText("");
                        locationText.setText("");
                        dateButton.setText("Set Date");
                        pricingText.setText("");
                        conditionSpinner.setSelection(0);
                        amPmSpinner.setSelection(0);
                        timeSpinner.setSelection(0);
                    }  else if (error instanceof NoConnectionError) {
                        waitingDialog.dismiss();
                        Toasty.error(getContext(), "Oops. No connection!", Toast.LENGTH_LONG).show();
                    } else if (error instanceof TimeoutError) {
                        waitingDialog.dismiss();
                        Toasty.error(getContext(), "Oops. Timeout error!", Toast.LENGTH_LONG).show();
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

    public void getBookCopies(final String title)
    {
        mQueue = Volley.newRequestQueue(getActivity());
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

                                    Toasty.error(getContext(), error, Toast.LENGTH_SHORT).show();
                                }
                                else {
                                 JSONArray listingArray = bookObj.getJSONArray("listings");
                                 numBooks = 0;
                                 for(int i =0; i<listingArray.length();i++)
                                 {
                                     numBooks+=1;
                                 }
                                    //increment copies available
                                    incrementBookCopies(title);
                                }

                            } catch (Exception e) {
                                Toasty.error(getContext(), "Server Error!", Toast.LENGTH_SHORT, true).show();

                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NetworkError) {
                        Toasty.error(getContext(), "Oops. Network Error!", Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toasty.error(getContext(), "Oops. Server Error!", Toast.LENGTH_LONG).show();
                    }  else if (error instanceof NoConnectionError) {
                        Toasty.error(getContext(), "Oops. No connection!", Toast.LENGTH_LONG).show();
                    } else if (error instanceof TimeoutError) {
                        Toasty.error(getContext(), "Oops. Timeout error!", Toast.LENGTH_LONG).show();
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

    public void incrementBookCopies(String title) {
        mQueue = Volley.newRequestQueue(getActivity());
        String url = "https://bookmobile-backend.herokuapp.com/book/";
        Log.v("URL", url);
        Log.v("Number of Books(increment)", String.valueOf(numBooks));

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("book_name", title);
            jsonBody.put("num_book_available", numBooks++);
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
                                    Toasty.error(getContext(), error, Toast.LENGTH_SHORT).show();
                                } else {
                                    numBooks = bookObj.getInt("num_book_available");

                                }

                            } catch (Exception e) {
                                Toasty.error(getContext(), "Server Error!", Toast.LENGTH_SHORT, true).show();

                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NetworkError) {
                        Toasty.error(getContext(), "Oops. Network Error!", Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toasty.error(getContext(), "Oops. Server Error!", Toast.LENGTH_LONG).show();
                    } else if (error instanceof NoConnectionError) {
                        Toasty.error(getContext(), "Oops. No connection!", Toast.LENGTH_LONG).show();
                    } else if (error instanceof TimeoutError) {
                        Toasty.error(getContext(), "Oops. Timeout error!", Toast.LENGTH_LONG).show();
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