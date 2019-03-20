package com.random.BookMobile.Fragments_Bar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.random.BookMobile.GeneralBookDetailFragment;
import com.random.BookMobile.LoginActivity;
import com.random.BookMobile.R;
import com.random.BookMobile.SearchResultAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;

public class HomePageFragment extends Fragment{

    private static final String TAG = "Home Page";
    Button lotfButton;
    String username;
    TextView welcomeText;
    SharedPreferences prf;
    private RequestQueue mQueue;
    ArrayList<String> bookNames = new ArrayList<String>();

    Bundle bundle;

    JSONArray localBookArray;

    String bookTitle, description;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.home_fragment, container, false);
        prf = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        username = prf.getString("username",null);
        bundle = new Bundle();
        //Loads recommendations by calling API
        loadRecommendations(username);

        //implementation of Search View
        SearchView simpleSearchView = (SearchView) v.findViewById(R.id.searchview1);
        simpleSearchView.setQueryHint("Search for a book...");
        int bookNamesSize = getResources().getStringArray(R.array.example_Books).length;
        for(int i=0; i<bookNamesSize; i++)
        {
            String bookName = getResources().getStringArray(R.array.example_Books)[i];
            bookNames.add(bookName);
        }
        final ListView searchList = v.findViewById(R.id.searchList);
        final SearchResultAdapter arrayAdapter = new SearchResultAdapter(getContext(),bookNames);
        // Set The Adapter
        searchList.setVisibility(View.INVISIBLE);
        searchList.setAdapter(arrayAdapter);

        // perform set on query text listener event
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Call Volley Request here
                if(query.length()==0)
                {
                    searchList.setVisibility(View.INVISIBLE);
                }
                searchList.setVisibility(View.VISIBLE);
              String text = query;
              arrayAdapter.filter(text);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
              /*  String text = newText;
                arrayAdapter.filter(text);*/
              if(newText.length()==0)
              {
                  searchList.setVisibility(View.INVISIBLE);
              }
                return false;
            }
        });


        return v;
    }

    public void loadRecommendations(String username)
    {
        mQueue = Volley.newRequestQueue(getActivity());
        String url = "https://api.myjson.com/bins/f6xjy";

        final AlertDialog waitingDialog = new SpotsDialog.Builder()
                .setContext(getActivity())
                .setMessage("loading recommendations..")
                .setTheme(R.style.Custom)
                .setCancelable(false)
                .build();
        waitingDialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray bookArray = response.getJSONArray("Books");
                            bundle.putString("JSON bookArray", bookArray.toString());
                            for(int i=0; i<bookArray.length();i++)
                            {
                                // Code that adds buttons programmatically - will be used when generating new recommended books
                                JSONObject book = bookArray.getJSONObject(i);
                                bookTitle = book.getString("title");
                                description = book.getString("desc");
                                // Put anything what you want
                                MaterialButton myButton = new MaterialButton(getContext());
                                myButton.setText(bookTitle);
                                myButton.setTag(bookTitle);
                                myButton.setOnClickListener(btnclick);
                                //CardView cardView = v.findViewById(R.id.categories_card_view);

                                LinearLayout ll = (LinearLayout) v.findViewById(R.id.buttonLayoutRec);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                                ll.addView(myButton, lp);
                            }
                            Toasty.success(getContext(), "Loaded Recommendations", Toast.LENGTH_SHORT, true).show();
                            waitingDialog.dismiss();

                        } catch (Exception e) {

                            waitingDialog.dismiss();
                            Toasty.error(getContext(), "Server Error!", Toast.LENGTH_SHORT, true).show();

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    waitingDialog.dismiss();
                    Toasty.error(getContext(), "Oops. Network Error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    waitingDialog.dismiss();
                    Toasty.error(getContext(), "Oops. Server Error!", Toast.LENGTH_LONG).show();
                }  else if (error instanceof NoConnectionError) {
                    waitingDialog.dismiss();
                    Toasty.error(getContext(), "Oops. No connection!", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    waitingDialog.dismiss();
                    Toasty.error(getContext(), "Connection Timeout!", Toast.LENGTH_LONG).show();
                }

                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    View.OnClickListener btnclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String bookTitle = view.getTag().toString();
            String bookDesc = "";
           // String noOfBooksAvail = "";
            //String noOfTakers = "";
            try {
                JSONArray books = new JSONArray(bundle.getString("JSON bookArray"));
                for(int i=0; i<=books.length();i++)
                {
                   JSONObject book = books.getJSONObject(i);
                   String titleOfBook = book.getString("title");
                   if(bookTitle.equals(titleOfBook))
                   {
                     bookDesc = book.getString("desc");
                   //  noOfBooksAvail = book.getString("number_books_available");
                   //  noOfTakers = book.getString("number_books_takers");
                   }
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            Toasty.success(getContext(), bookTitle, Toast.LENGTH_SHORT ).show();

           /* SharedPreferences.Editor editor = prf.edit();
            editor.putString("title", bookTitle);
            editor.putString("description", description);
            editor.apply();*/


            GeneralBookDetailFragment dialog = new GeneralBookDetailFragment();
            bundle.putString("title", bookTitle);
            bundle.putString("desc", bookDesc);
            dialog.setArguments(bundle);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("Book Details");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            dialog.show(getChildFragmentManager(), "Book Details");
            Log.i("TAG", "Just showed dialog for "+view.getTag().toString());
        }
    };
}
