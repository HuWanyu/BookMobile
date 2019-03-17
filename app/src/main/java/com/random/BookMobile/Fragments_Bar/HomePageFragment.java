package com.random.BookMobile.Fragments_Bar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.random.BookMobile.GeneralBookDetailFragment;
import com.random.BookMobile.LoginActivity;
import com.random.BookMobile.R;
import com.random.BookMobile.SesarchResultAdapter;

import org.json.JSONArray;
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
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.home_fragment, container, false);
        welcomeText = v.findViewById(R.id.welcomeText);
        prf = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        username = prf.getString("username",null);
        welcomeText.append(username);
        loadRecommendations(username);
        SearchView simpleSearchView = (SearchView) v.findViewById(R.id.searchview1);
        simpleSearchView.setQueryHint("Search for a book...");
        int bookNamesSize = getResources().getStringArray(R.array.example_Books).length;
        for(int i=0; i<bookNamesSize; i++)
        {
            String bookName = getResources().getStringArray(R.array.example_Books)[i];
            bookNames.add(bookName);
        }
        ListView searchList = v.findViewById(R.id.searchList);
        final SesarchResultAdapter arrayAdapter = new SesarchResultAdapter(getContext(),bookNames);
        // Set The Adapter
        searchList.setAdapter(arrayAdapter);
        // perform set on query text listener event
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
            // do something on text submit
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
                arrayAdapter.filter(text);
                return false;
            }
        });


        lotfButton = v.findViewById(R.id.lotf);
        lotfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralBookDetailFragment dialog = new GeneralBookDetailFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("Book Details");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                dialog.show(getChildFragmentManager(), "Book Details");
                Log.i("TAG", "Just showed dialog");
            }
        });
        return v;
    }

    public void loadRecommendations(String username)
    {
        mQueue = Volley.newRequestQueue(getActivity());
        // String url = "https://api.myjson.com/bins/1ayd4u";
        String url = "https://api.myjson.com/bins/mubwm";

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
                            for(int i=0; i<bookArray.length();i++)
                            {
                                // Code that adds buttons programmatically - will be used when generating new recommended books
                                JSONObject book = bookArray.getJSONObject(i);
                                String title = book.getString("title");
                                Button myButton = new Button(getContext());
                                myButton.setText(title);

                                LinearLayout ll = (LinearLayout) v.findViewById(R.id.buttonLayoutRec);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                                ll.addView(myButton, lp);
                            }
                            Toasty.success(getContext(), "Loaded Recommendations", Toast.LENGTH_LONG).show();
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

                error.printStackTrace();
            }
        });
        mQueue.add(request);

    }
}
