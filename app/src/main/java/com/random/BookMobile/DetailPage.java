package com.random.BookMobile;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.random.BookMobile.Fragments_Bar.ChatFragment;

public class DetailPage extends AppCompatActivity {

    private int bookID;
    private int userID;

    private TextView giverNameText, bookName, bookDescription, timeToMeet, costOfBook;
    private ImageView image;

    private Button eventdetailchat;

    @Override
    public void onBackPressed() {
        Intent goBack = new Intent(DetailPage.this, MainActivity.class);
        goBack.putExtra("id", 1);
        startActivity(goBack);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        Intent intent = getIntent();
        String giverName = intent.getStringExtra("Giver Name");
        final String phoneNumber = intent.getStringExtra("Contact Number");
        String cost = String.valueOf(intent.getIntExtra("Cost",0));
        Log.v("COst", cost);
        String timing = intent.getStringExtra("Timing");
        String title = intent.getExtras().getString("title");
        String summary = intent.getExtras().getString("desc");
        giverNameText = findViewById(R.id.giver_name);
        bookName = findViewById(R.id.detailpage_name);
        bookDescription = findViewById(R.id.detailpage_description);
        timeToMeet = findViewById(R.id.timeToMeet);
        costOfBook = findViewById(R.id.costOfBook);

        giverNameText.setText(giverName);
        bookName.setText(title);
        bookDescription.setText(summary);
        timeToMeet.append(timing);
        costOfBook.append(cost);



        image = findViewById(R.id.detailpage_imageBook);


            image.setImageResource(R.mipmap.closed_book);



       // bookID = Integer.parseInt(intent.getStringExtra("BookID"));
       // userID = LoginActivity.getUserID();

//uncomment this part after database has set up

 /*       DatabaseHelp dbHelper = new DatabaseHelp(this);
        Cursor eventCursor = dbHelper.viewEvent(bookID);

        eventCursor.moveToNext();

        int bookIndex = eventCursor.getColumnIndex("Book_Name");
        String name = eventCursor.getString(bookIndex);

        bookName = (TextView) findViewById(R.id.name);
        bookName.setText(name);

        int descriptionIndex = eventCursor.getColumnIndex("Book_Description");
        String description = eventCursor.getString(descriptionIndex);

        bookDescription= (TextView) findViewById(R.id.detailpage_description);
        bookDescription.setText();
*/

        eventdetailchat = (Button) findViewById(R.id.detailpage_chat);
        eventdetailchat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"+phoneNumber));
                startActivity(sendIntent);

            }
        });

/*

        image = (ImageView) findViewById(R.id.detailpage_imageBook);
        int typeIndex = eventCursor.getColumnIndex("Event_Category_ID");
        int typeID = eventCursor.getInt(typeIndex);

        switch (typeID){
            case 1:
                image.setImageResource(R.drawable.fiction);
                break;
            case 2:
                image.setImageResource(R.drawable.scifi);
                break;
            case 3:
                image.setImageResource(R.drawable.philosophy);
                break;
            default:
                image.setImageResource(R.drawable.books);
                break;
        }
        */
    }



}
