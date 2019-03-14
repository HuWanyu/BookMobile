package com.random.BookMobile.MeActivity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.random.BookMobile.DetailPage;
import com.random.BookMobile.R;

import java.util.ArrayList;
import java.util.List;


public class BooksInfoListAdapter extends RecyclerView.Adapter<BooksInfoListAdapter.ViewHolder> {

    private static Context context;
    private int totalCount;
    private ArrayList<String> BookName;
    private List<Integer> BookID;

    public BooksInfoListAdapter(Context context,ArrayList<String> BookName, List<Integer> BookID) {
        this.context = context;
        // get the number of EventName when the class is constructed
        this.totalCount = BookName.size();
        this.BookName = BookName;
        this.BookID = BookID;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public Button button;
        public ViewHolder(View v) {
            super(v);
            button = v.findViewById(R.id.booklistButton);
        }
    }


    @NonNull
    @Override
    public BooksInfoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_of_books_adapter, parent, false);
        BooksInfoListAdapter.ViewHolder vh = new BooksInfoListAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull BooksInfoListAdapter.ViewHolder holder, final int position) {
        holder.button.setText(BookName.get(position));

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pass information to another activity started by click the button
                Intent startNewActivity = new Intent(context, DetailPage.class);

                startNewActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startNewActivity.putExtra("EventID", Integer.toString(BookID.get(position)));

                context.startActivity(startNewActivity);

            }
        });
    }

    @Override
    public int getItemCount() {
        return totalCount;
    }
}
