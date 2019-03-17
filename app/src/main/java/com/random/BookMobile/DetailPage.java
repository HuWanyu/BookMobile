package com.random.BookMobile;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.random.BookMobile.Fragments_Bar.ChatFragment;

public class DetailPage extends AppCompatActivity {

    private int bookID;
    private int userID;

    private TextView giverNameText;
    private TextView bookName;
    private TextView bookDescription;
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
        giverNameText = findViewById(R.id.giver_name);
        giverNameText.setText(giverName);

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
                Intent goChat = new Intent(DetailPage.this, ChatFragment.class);
                goChat.putExtra("id", 1);
                startActivity(goChat);

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
