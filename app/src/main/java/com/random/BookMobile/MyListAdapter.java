package com.random.BookMobile;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.ExtractedTextRequest;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyListAdapter extends ArrayAdapter {

    private final Activity context;
    private final String[] giversNames;
    private final String[] bookCond;
    private final String[] timings;
    private final int[] costs;

    public MyListAdapter(Activity context, String[] giversNames,String[] bookCond, String[] timings, int[] costs) {
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

        giversName.setText(giversNames[position]);
        bookCondition.setText(bookCond[position]);
        prefTiming.setText(timings[position]);
        creditCost.setText(String.valueOf(costs[position]));

        return rowView;

    };
}
