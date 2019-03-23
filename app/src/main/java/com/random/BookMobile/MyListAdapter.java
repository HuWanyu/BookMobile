package com.random.BookMobile;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.ExtractedTextRequest;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyListAdapter extends ArrayAdapter {

    private final Activity context;
    private final ArrayList<String> giversNames;
    private final ArrayList<String> bookCond;
    private final ArrayList<String> timings;
    private final ArrayList<Integer> costs;

    public MyListAdapter(Activity context, ArrayList<String> giversNames, ArrayList<String> bookCond, ArrayList<String> timings, ArrayList<Integer> costs) {
        super(context, R.layout.mylist, giversNames);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.giversNames=giversNames;
        this.bookCond=bookCond;
        this.timings=timings;
        this.costs=costs;
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView giversName = (TextView) rowView.findViewById(R.id.giversName);
        TextView bookCondition = (TextView) rowView.findViewById(R.id.bookCond);
        TextView prefTiming = (TextView) rowView.findViewById(R.id.timing);
        TextView creditCost = (TextView) rowView.findViewById(R.id.cost);

        giversName.setText(giversNames.get(position));
        bookCondition.setText(bookCond.get(position));
        prefTiming.setText(timings.get(position));
        creditCost.setText(String.valueOf(costs.get(position)));

        return rowView;

    }
}
