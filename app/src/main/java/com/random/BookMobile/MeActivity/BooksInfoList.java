package com.random.BookMobile.MeActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import com.random.BookMobile.LoginActivity;
import com.random.BookMobile.MainActivity;
import com.random.BookMobile.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class BooksInfoList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    SharedPreferences prf;

    ListView bookList;

    private ArrayList<String> BookName = new ArrayList<>();
    private ArrayList<String> BookDesc = new ArrayList<String>();
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

         prf = getSharedPreferences("user_details",MODE_PRIVATE);

         String listedBooks = prf.getString("listed books", null);

         if(listedBooks!=null) {
             try {
                 JSONArray books = new JSONArray(listedBooks);
                 if(books.length()==0)
                 {
                     AlertDialog alertDialog = new AlertDialog.Builder(BooksInfoList.this).create();
                     alertDialog.setTitle("No Books Listed");
                     alertDialog.setMessage("You don't have any books listed! Please add a listing.");
                     alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                             new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int which) {
                                     Intent intent = new Intent(BooksInfoList.this, MainActivity.class);
                                     intent.putExtra("id", 2);
                                     startActivity(intent);
                                     BooksInfoList.this.finish();
                                 }
                             });
                     alertDialog.show();
                 }
                 for (int i = 0; i < books.length(); i++) {
                     BookName.add(books.get(i).toString());
                 }
             } catch (JSONException e) {
                    e.printStackTrace();
             }
         }

        bookList = findViewById(R.id.listOfBooks);
        BooksInfoListAdapter adapter = new BooksInfoListAdapter(this, BookName);
        bookList.setAdapter(adapter);

    }

}
