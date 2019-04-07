package com.random.BookMobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.random.BookMobile.Fragments_Bar.AddListingFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;

public class GiversDialogFragment extends AppCompatDialogFragment {
    View customView;

    private RequestQueue mQueue;
    Bundle bundle;
    ListView list;
    ProgressBar progressBar;
    ArrayList<String> giversNames = new ArrayList<>();
    ArrayList<String> bookCond = new ArrayList<>();
    ArrayList<String> locations = new ArrayList<>();
    ArrayList<String> timings = new ArrayList<>();
    ArrayList<Integer> costs = new ArrayList<>();
    String giver_name;
    SharedPreferences prf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return customView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mQueue = Volley.newRequestQueue(getActivity());
        customView = LayoutInflater.from(getActivity())
                .inflate(R.layout.givers_list, null);

        //for loading animations
            progressBar = customView.findViewById(R.id.progressBar);
            bundle = this.getArguments();
            String bookTitle = bundle.getString("title");
            //get data from server
            prf = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
            String jwt_token = prf.getString("jwt_token", null);

            getData(bookTitle, jwt_token);

            //Log.d("givers names", "giver names"+giversNames.toString());
            //custom Dialog Title
            TextView customText = new TextView(getContext());
            customText.setTextSize(30);
            customText.setText("Pick a Giver:");
            customText.setPadding(50, 50, 10, 10);

            return new AlertDialog.Builder(getActivity())
                    .setCustomTitle(customText)
                    .setView(customView)
                    .create();

    }

    private void getData(String title, final String jwt) {

       // String url = "https://api.myjson.com/bins/85msy";
        String url = "https://bookmobile-backend.herokuapp.com/book/"+title.replaceAll(" ", "%20");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray listings = response.getJSONArray("listings");

                            for (int i = 0; i < listings.length(); i++) {
                                JSONObject listing = listings.getJSONObject(i);

                                String location = listing.getString("meeting_location");
                                String bookCondition = listing.getString("book_condition");
                                String timing = listing.getString("meeting_time");
                                int cost = listing.getInt("book_price_credit");


                                locations.add(location);
                                bookCond.add(bookCondition);
                                timings.add(timing);
                                costs.add(cost);
                            }
                            for (int i = 0; i < listings.length(); i++) {
                                JSONObject listing = listings.getJSONObject(i);
                                String giverId = listing.getString("giver_id");
                                Log.v("GIVER IDS", giverId);
                                mapToGiverName(giverId, jwt, listings.length());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    progressBar.setVisibility(View.GONE);
                    Toasty.error(getContext(), "Oops. Network Error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    progressBar.setVisibility(View.GONE);
                    Toasty.error(getContext(), "Oops. Server Error!", Toast.LENGTH_LONG).show();
                }  else if (error instanceof NoConnectionError) {
                    progressBar.setVisibility(View.GONE);
                    Toasty.error(getContext(), "Oops. No connection!", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    progressBar.setVisibility(View.GONE);
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
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
       // mQueue.add(request);

    }

    private void mapToGiverName(String id, final String jwt, final int listLength)
    {

        String url = "https://bookmobile-backend.herokuapp.com/user/id/"+id;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            giver_name = response.getString("user_id");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        giversNames.add(giver_name);
                        Log.v("GIVER NAMES", giversNames.toString());
                        if(giversNames.size()==listLength)
                        {
                            populateList();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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

    private void populateList() {
        //populate List
        MyListAdapter adapter=new MyListAdapter(getActivity(), giversNames, locations, bookCond,timings,costs);
        list=customView.findViewById(R.id.my_list);
        list.setAdapter(adapter);
        final Intent individualBook = new Intent(getActivity(), DetailPage.class);
        individualBook.putExtras(bundle);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                // TODO Auto-generated method stub

                for(int i = 0; i<list.getAdapter().getCount();i++){
                    if(position == i)
                    {
                        individualBook.putExtra("Giver Name", giversNames.get(i));
                        individualBook.putExtra("Location", locations.get(i));
                        individualBook.putExtra("Timing", timings.get(i));
                        individualBook.putExtra("Cost", costs.get(i));
                        individualBook.putExtra("Contact Number", "98786789");
                        //code specific to list item
                        startActivity(individualBook);
                    }
                }
            }
        });
    }
}
