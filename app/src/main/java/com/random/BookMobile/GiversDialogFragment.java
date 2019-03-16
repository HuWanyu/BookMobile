package com.random.BookMobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.random.BookMobile.Fragments_Bar.AddListingFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class GiversDialogFragment extends AppCompatDialogFragment {
    View customView;
    private RequestQueue mQueue;

    ListView list;
    ProgressBar progressBar;
    ArrayList<String> giversNames = new ArrayList<>();
    ArrayList<String> bookCond = new ArrayList<>();
    ArrayList<String> timings = new ArrayList<>();
    ArrayList<Integer> costs = new ArrayList<>();

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
        //get data from server
        getData();
        Log.d("givers names", "giver names"+giversNames.toString());
        //custom Dialog Title
        TextView customText = new TextView(getContext());
        customText.setTextSize(30);
        customText.setText("Pick a Giver:");
        customText.setPadding(50,50,10,10);

        return new AlertDialog.Builder(getActivity())
                .setCustomTitle(customText)
                .setView(customView)
                .create();
    }

    private void getData() {
        String url = "https://api.myjson.com/bins/ig2di";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("employees");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject employee = jsonArray.getJSONObject(i);

                                String giverName = employee.getString("name");
                                String bookCondition = employee.getString("email");
                                String timing = employee.getString("timings");
                                int cost = employee.getInt("age");

                                giversNames.add(giverName);
                                bookCond.add(bookCondition);
                                timings.add(timing);
                                costs.add(cost);

                            }
                            Log.d("givers names 2", "giver names"+giversNames.toString());
                            populateList();
                            progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
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

    private void populateList() {
        //populate List
        MyListAdapter adapter=new MyListAdapter(getActivity(), giversNames, bookCond,timings,costs);
        list=customView.findViewById(R.id.my_list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                // TODO Auto-generated method stub
                if(position == 0) {
                    //code specific to first list item
                    Toast.makeText(getContext(),"Giver selected:"+giversNames.get(0),Toast.LENGTH_SHORT).show();
                }

                else if(position == 1) {
                    //code specific to 2nd list item
                    Toast.makeText(getContext(),"Giver selected:"+giversNames.get(1),Toast.LENGTH_SHORT).show();
                }

                else if(position == 2) {

                    Toast.makeText(getContext(),"Giver selected:"+giversNames.get(2),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
