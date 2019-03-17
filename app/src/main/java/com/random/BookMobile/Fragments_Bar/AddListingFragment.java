package com.random.BookMobile.Fragments_Bar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
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
import com.random.BookMobile.GeneralBookDetailFragment;
import com.random.BookMobile.LoginActivity;
import com.random.BookMobile.R;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;


public class AddListingFragment extends Fragment {
    int REQUEST_CODE = 0;
    Button addListingButton, dateButton;
    String title, condition, location, timing, price;
    private RequestQueue mQueue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.add_listing_fragment, container, false);
        Resources res = getResources();

        // Book Title
        TextView bookTitle = view.findViewById(R.id.bookTitleText);
        title = bookTitle.getText().toString().trim();

        //setting up dropdown list for book condition
        String[] conditionList = res.getStringArray(R.array.conArray);
        Spinner conditionSpinner = view.findViewById(R.id.conSpinner);
        ArrayAdapter<String> conAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, conditionList);
        conditionSpinner.setAdapter(conAdapter);
        condition = conditionSpinner.getSelectedItem().toString().trim();

        //Location
        TextView locationText = view.findViewById(R.id.locText);
        location = locationText.getText().toString().trim();

        //setting up dropdown list for am/pm
        String[] amPmList = res.getStringArray(R.array.ampmarray);
        Spinner amPmSpinner = view.findViewById(R.id.ampmSpinner);
        ArrayAdapter<String> amPmAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, amPmList);
        amPmSpinner.setAdapter(amPmAdapter);
        String ampm = amPmSpinner.getSelectedItem().toString().trim();

        //setting up dropdown list for timings
        Integer[] timeList = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Spinner timeSpinner = view.findViewById(R.id.timeSpinner);
        ArrayAdapter<Integer> timeAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, timeList);
        timeSpinner.setAdapter(timeAdapter);
        String time = timeSpinner.getSelectedItem().toString().trim();


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

        timing = dateButton.getText().toString() + " "+ time + ampm;

        // price
        TextView pricingText = view.findViewById(R.id.priceText);
        price = pricingText.getText().toString().trim();

        //add listing button
        addListingButton = view.findViewById(R.id.listButton);
        addListingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addListingToServer(title, condition, location, timing, price);
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


    public void addListingToServer(final String title, final String condition, final String location, final String timing, final String price){

        mQueue = Volley.newRequestQueue(getActivity());
        // String url = "https://api.myjson.com/bins/1ayd4u";
        String url = "http://bookmobile.apiblueprint.org/listing/_id?gid='abc'&tid=";

        final AlertDialog waitingDialog = new SpotsDialog.Builder()
                .setContext(getActivity())
                .setMessage("Adding New Book...")
                .setTheme(R.style.Custom)
                .setCancelable(false)
                .build();
        waitingDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject validateObj = new JSONObject(response.toString());
                            String user_id = validateObj.getString("user_id");
                            Log.d("LOGIN STATUS", "Username:" + user_id);
                            if (user_id.equals("bookmobileuser")) {

                            }
                            else
                            {

                            }

                        } catch (Exception e) {

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
                    Toasty.error(getContext(), "Oops. Server Error!", Toast.LENGTH_LONG).show();
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
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("book_title", title);
                params.put("book_condition", condition);
                params.put("location", location);
                params.put("timing", timing);
                params.put("price", price);
                return params;
            }
        }

                ;
        mQueue.add(request);

    }

}