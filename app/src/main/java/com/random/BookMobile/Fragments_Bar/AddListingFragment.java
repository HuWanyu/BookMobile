package com.random.BookMobile.Fragments_Bar;

import android.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.random.BookMobile.GeneralBookDetailFragment;
import com.random.BookMobile.LoginActivity;
import com.random.BookMobile.R;

import org.json.JSONObject;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;


public class AddListingFragment extends Fragment {
    int REQUEST_CODE = 0;
    Button addListingButton;
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

        timing = time + ampm;

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

    public void addListingToServer(String title, String condition, String location, String timing, String price){

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

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // JSONObject validateObj = response.getJSONObject("loginValid");
                            String user_id = response.getString("user_id");
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

                error.printStackTrace();
            }
        });
        mQueue.add(request);

    }

}