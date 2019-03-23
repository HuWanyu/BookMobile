package com.random.BookMobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchResultAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<String> bookNameList = null;
    private ArrayList<String> searchBooksArrayList;

    public SearchResultAdapter(Context context, List<String> bookNameList) {
        mContext = context;
        this.bookNameList = bookNameList;
        inflater = LayoutInflater.from(mContext);
        this.searchBooksArrayList = new ArrayList<String>();
        this.searchBooksArrayList.addAll(bookNameList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return bookNameList.size();
    }

    @Override
    public String getItem(int position) {
        return bookNameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.search_item, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(bookNameList.get(position));
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        bookNameList.clear();
        if (charText.length() == 0) {
           // bookNameList.addAll(searchBooksArrayList);
        } else {
            for (String wp : searchBooksArrayList) {
                if (wp.toLowerCase(Locale.getDefault()).contains(charText)) {
                    bookNameList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
