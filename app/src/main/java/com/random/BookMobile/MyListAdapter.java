package com.random.BookMobile;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.ExtractedTextRequest;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class MyListAdapter extends ArrayAdapter {

    private final Activity context;
    private final ArrayList<String> giverNames;
    private final ArrayList<String> locations;
    private final ArrayList<String> bookCond;
    private final ArrayList<String> timings;
    private final ArrayList<Integer> costs;

    public MyListAdapter(Activity context, ArrayList<String> giverNames, ArrayList<String> locations, ArrayList<String> bookCond, ArrayList<String> timings, ArrayList<Integer> costs) {
        super(context, R.layout.mylist, locations);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.giverNames = giverNames;
        this.locations=locations;
        this.bookCond=bookCond;
        this.timings=timings;
        this.costs=costs;

    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView giverName = (TextView) rowView.findViewById(R.id.giversName);
        TextView location = (TextView) rowView.findViewById(R.id.meetLoc);
        TextView bookCondition = (TextView) rowView.findViewById(R.id.bookCond);
        TextView prefTiming = (TextView) rowView.findViewById(R.id.timing);
        TextView creditCost = (TextView) rowView.findViewById(R.id.cost);


        giverName.setText(giverNames.get(position));
        location.setText(locations.get(position));
        bookCondition.setText(bookCond.get(position));
        prefTiming.setText(timings.get(position));
        creditCost.setText(String.valueOf(costs.get(position)));

        return rowView;

    }


}
