package com.random.BookMobile.MeActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.random.BookMobile.DetailPage;
import com.random.BookMobile.R;

import java.util.ArrayList;
import java.util.List;


public class BooksInfoListAdapter extends ArrayAdapter {


    private final Activity context;
    private int totalCount;
    private ArrayList<String> BookName;
    private ArrayList<String> BookDesc;

    public BooksInfoListAdapter(Activity context, ArrayList<String> BookName) {
        super(context, R.layout.activity_list_of_books_adapter, BookName);
        this.context = context;
        // get the number of EventName when the class is constructed
        this.totalCount = BookName.size();
        this.BookName = BookName;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_list_of_books_adapter, null,true);

        TextView bookTitle = (TextView) rowView.findViewById(R.id.title);


        bookTitle.setText(BookName.get(position));


        return rowView;

    }


}