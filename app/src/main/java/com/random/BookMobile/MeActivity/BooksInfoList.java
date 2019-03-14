package com.random.BookMobile.MeActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.random.BookMobile.LoginActivity;
import com.random.BookMobile.MainActivity;
import com.random.BookMobile.R;

import java.util.ArrayList;
import java.util.List;


public class BooksInfoList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<String> EventName = new ArrayList<>();
    private List<Integer> EventID = new ArrayList<Integer>();
    @Override
    public void onBackPressed() {
        Intent goBack = new Intent(BooksInfoList.this, MainActivity.class);
        goBack.putExtra("id", 2);
        startActivity(goBack);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_books);

//uncomment after database is up
/*
        DatabaseHelp dbHelper = new DatabaseHelp(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor eventCursor = dbHelper.viewMyEvent(LoginActivity.getUserID());


        while (eventCursor.moveToNext())
        {
            int eventIndex = eventCursor.getColumnIndex("Event_Name");
            int statusIndex = eventCursor.getColumnIndex("Event_Approval_Status");
            String eventName = eventCursor.getString(eventIndex)+" ("+eventCursor.getString(statusIndex)+")";
            EventName.add(eventName);

            int IDIndex = eventCursor.getColumnIndex("Event_ID");
            int eventID = eventCursor.getInt(IDIndex);
            EventID.add(eventID);
        }
        db.close();

        mRecyclerView = (RecyclerView) findViewById(R.id.MeBookListRV);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager((mLayoutManager));

        mAdapter = new BooksInfoListAdapter(getBaseContext(), EventName, EventID);
        mRecyclerView.setAdapter(mAdapter);


*/

    }

}
