package com.random.BookMobile.Fragments_Bar;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.random.BookMobile.GeneralBookDetailFragment;
import com.random.BookMobile.LoginActivity;
import com.random.BookMobile.R;
import com.random.BookMobile.SearchResultAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;

public class HomePageFragment extends Fragment{

    private static final String TAG = "Home Page";
    Button lotfButton;
    String jwt_token, preference;
    TextView welcomeText;
    SharedPreferences prf;
    private RequestQueue mQueue;
    ArrayList<String> bookNames = new ArrayList<String>();

    CardView catCardView;
    LinearLayout.LayoutParams layoutparamsWPWP, layoutparamsWPMP, layoutparamsMPWP, layoutparamsMPMP;
    LinearLayout categoryCards;
    LinearLayout recoCards;

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
        jwt_token = prf.getString("jwt_token",null);
        if(prf.contains("preference")) {
            preference = prf.getString("preference", null);
            loadRecommendations(preference);
        }
        else
            getPreferences(jwt_token);
        bundle = new Bundle();

        String[] bookList = getResources().getStringArray(R.array.example_Books);

        for (int i = 0; i < bookList.length; i++) {
           bookNames.add(bookList[i]);
        }

       // getSearchData();
        SearchView searchView = (SearchView) v.findViewById(R.id.searchview1);
        searchView.setInputType(1);

        final ListView searchList = v.findViewById(R.id.searchList);
        searchList.bringToFront();
        searchList.setVisibility(View.INVISIBLE);

        //implementation of Search View
        final SearchResultAdapter arrayAdapter = new SearchResultAdapter(getContext(),bookNames);
        // Set The Adapter
        searchList.setAdapter(arrayAdapter);

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String desc, name;
                name="";
                desc = "";
                if(position==0)
                {
                    name = bookNames.get(0);
                    if(name.equals("Crime & Punishment"))
                        desc = "Crime & Punishment description string";
                    if(name.equals("Harry Potter"))
                        desc = "Harry Potter description string";
                    if(name.equals("Snow Crash"))
                        desc = "Snow Crash description string";
                    if(name.equals("No Easy Day"))
                        desc = "No Easy Day description string";
                    if(name.equals("Cooking Recipes"))
                        desc = "Cooking Recipes description string";
                    Log.v("Book Name", bookNames.get(0));
                }
                if(position==1)
                {
                    name = bookNames.get(1);
                    if(name.equals("Crime & Punishment"))
                        desc = "Crime & Punishment description string";
                    if(name.equals("Harry Potter"))
                        desc = "Harry Potter description string";
                    if(name.equals("Snow Crash"))
                        desc = "Snow Crash description string";
                    if(name.equals("No Easy Day"))
                        desc = "No Easy Day description string";
                    if(name.equals("Cooking Recipes"))
                        desc = "Cooking Recipes description string";
                    Log.v("Book Name", bookNames.get(1));
                }
                if(position==2)
                {
                    name = bookNames.get(2);
                    if(name.equals("Crime & Punishment"))
                        desc = "Crime & Punishment description string";
                    if(name.equals("Harry Potter"))
                        desc = "Harry Potter description string";
                    if(name.equals("Snow Crash"))
                        desc = "Snow Crash description string";
                    if(name.equals("No Easy Day"))
                        desc = "No Easy Day description string";
                    if(name.equals("Cooking Recipes"))
                        desc = "Cooking Recipes description string";
                    Log.v("Book Name", bookNames.get(2));
                }
                if(position==3)
                {
                    name = bookNames.get(3);
                    if(name.equals("Crime & Punishment"))
                        desc = "Crime & Punishment description string";
                    if(name.equals("Harry Potter"))
                        desc = "Harry Potter description string";
                    if(name.equals("Snow Crash"))
                        desc = "Snow Crash description string";
                    if(name.equals("No Easy Day"))
                        desc = "No Easy Day description string";
                    if(name.equals("Cooking Recipes"))
                        desc = "Cooking Recipes description string";
                    Log.v("Book Name", bookNames.get(3));
                }
                GeneralBookDetailFragment dialog = new GeneralBookDetailFragment();
                bundle.putString("title", name);
                bundle.putString("desc", desc);
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
        });

        // perform set on query text listener event
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList.setVisibility(View.VISIBLE);
                String text = newText;
                arrayAdapter.filter(text);
                if(newText.length() == 0)
                {
                    searchList.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });
        categoryCards = v.findViewById(R.id.category_cards);
        recoCards = v.findViewById(R.id.recommendation_cards);

        //display categories
        createNewCategoryCard("Philosophy", R.mipmap.yin_yang);
        createNewCategoryCard("Sci-Fi", R.mipmap.cyborg);
        createNewCategoryCard("Fantasy", R.mipmap.frog_prince);
        createNewCategoryCard("History", R.mipmap.hourglass);

        return v;
    }

    public void getSearchData() {
        mQueue = Volley.newRequestQueue(getActivity());
        String url ="https://api.myjson.com/bins/10dtfy";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("search_books");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                String bookName = jsonArray.getString(i);
                                bookNames.add(bookName);
                                Log.v("Book Name " + i, bookName);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {

                    Toasty.error(getContext(), "Oops. Network Error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {

                    Toasty.error(getContext(), "Oops. Server Error!", Toast.LENGTH_LONG).show();
                }  else if (error instanceof NoConnectionError) {

                    Toasty.error(getContext(), "Oops. No connection!", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {

                    Toasty.error(getContext(), "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                }
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    public void createNewCategoryCard(String title, int id){

        catCardView = new CardView(getContext());
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        layoutparamsWPWP = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        layoutparamsWPMP = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );

        layoutparamsMPWP = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutparamsMPMP = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        LinearLayout container = new LinearLayout(getContext());
        container.setLayoutParams(layoutparamsWPWP);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(padding, 0,0,0);

        catCardView.setPreventCornerOverlap(false);
        catCardView.setLayoutParams(layoutparamsWPWP);
        catCardView.setRadius(50);
        catCardView.setCardBackgroundColor(Color.rgb(220, 220, 220));
        catCardView.setCardElevation(40);

        ViewGroup.MarginLayoutParams cardViewMarginParams = (ViewGroup.MarginLayoutParams) catCardView.getLayoutParams();
        cardViewMarginParams.setMargins(0, 0, padding, 0);
        catCardView.requestLayout();  //Dont forget this line

        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(id);
        imageView.setLayoutParams(layoutparamsWPWP);
        imageView.setAdjustViewBounds(true);
        imageView.setPadding(padding, padding,0,padding);

        TextView textview = new TextView(getContext());
        textview.setLayoutParams(layoutparamsMPWP);
        textview.setText(title);
        textview.setGravity(Gravity.CENTER_HORIZONTAL);
        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        textview.setTextColor(Color.rgb(0,0,0));

        container.addView(textview);
        container.addView(imageView);

        catCardView.addView(container);

        categoryCards.addView(catCardView);

    }

    public void createNewRecoCard(String title){

        catCardView = new CardView(getContext());
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        layoutparamsWPWP = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        layoutparamsWPMP = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        layoutparamsMPWP = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutparamsMPMP = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        LinearLayout container = new LinearLayout(getContext());
        container.setLayoutParams(layoutparamsWPWP);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(padding, 0,0,0);

        catCardView.setPreventCornerOverlap(false);
        catCardView.setLayoutParams(layoutparamsWPWP);
        catCardView.setRadius(50);
        catCardView.setCardBackgroundColor(Color.rgb(220, 220, 220));
        catCardView.setCardElevation(40);

        ViewGroup.MarginLayoutParams cardViewMarginParams = (ViewGroup.MarginLayoutParams) catCardView.getLayoutParams();
        cardViewMarginParams.setMargins(0, 0, padding, 0);
        catCardView.requestLayout();  //Dont forget this line

        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.mipmap.closed_book);
        imageView.setLayoutParams(layoutparamsWPWP);
        imageView.setAdjustViewBounds(true);
        imageView.setPadding(padding, padding,0,padding);

        TextView textview = new TextView(getContext());
        textview.setLayoutParams(layoutparamsMPWP);
        textview.setText(title);
        textview.setGravity(Gravity.CENTER_HORIZONTAL);
        if(textview.length()>10)
            textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        else
            textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        textview.setTextColor(Color.rgb(0,0,0));

        container.addView(textview);
        container.addView(imageView);

        catCardView.addView(container);
        catCardView.setTag(title);
        catCardView.setOnClickListener(btnclick);

        recoCards.addView(catCardView);

    }

    public void loadRecommendations(String book_cat)
    {
        mQueue = Volley.newRequestQueue(getActivity());
       // String url = "https://api.myjson.com/bins/f6xjy";
        String url = "https://bookmobile-backend.herokuapp.com/book/cat/"+book_cat.replaceAll(" ", "%20");
        final AlertDialog waitingDialog = new SpotsDialog.Builder()
                .setContext(getActivity())
                .setMessage("loading recommendations..")
                .setCancelable(false)
                .build();
        waitingDialog.show();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            bundle.putString("JSON bookArray", response.toString());
                            for(int i=0; i<response.length();i++)
                            {
                                JSONObject book = response.getJSONObject(i);
                                bookTitle = book.getString("book_name");
                                description = book.getString("book_desc");

                                createNewRecoCard(bookTitle);
                            }
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

    public void getPreferences(final String jwt)
    {
        mQueue = Volley.newRequestQueue(getActivity());

        String url ="https://bookmobile-backend.herokuapp.com/user/me";

        //POST REQUEST:
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String error = null;

                            Log.d("Response - Home FRAG", response);
                            JSONObject validateObj = new JSONObject(response);
                            if(validateObj.has("error"))
                                error = validateObj.getString("error");

                            if(validateObj.has("preference")) {
                                preference = validateObj.getString("preference");
                                loadRecommendations(preference);

                            }
                            if(error!=null) {
                                Toasty.error(getContext(), error, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toasty.error(getContext(), "Server Error!", Toast.LENGTH_SHORT, true).show();

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {

                    Toasty.error(getContext(), "Oops. Network Error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {

                    Toasty.error(getContext(), "Oops. Server Error!", Toast.LENGTH_LONG).show();
                }  else if (error instanceof NoConnectionError) {

                    Toasty.error(getContext(), "Oops. No connection!", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {

                    Toasty.error(getContext(), "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                }
                error.printStackTrace();
            }
        })
        {
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("api-token", jwt);
                return headers;
            }
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };

        mQueue.add(request);

    }
    View.OnClickListener btnclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String bookTitle = view.getTag().toString();
            String bookDesc = "";
            String noOfBooksAvail = "";
            String noOfTakers = "";
            try {
                JSONArray books = new JSONArray(bundle.getString("JSON bookArray"));
                for(int i=0; i<=books.length();i++)
                {
                   JSONObject book = books.getJSONObject(i);
                   String titleOfBook = book.getString("book_name");
                   if(bookTitle.equals(titleOfBook))
                   {
                     bookDesc = book.getString("book_desc");
                     noOfBooksAvail = book.getString("num_book_available");
                     noOfTakers = book.getString("num_book_takers");
                   }
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            Toasty.success(getContext(), bookTitle, Toast.LENGTH_SHORT ).show();

            GeneralBookDetailFragment dialog = new GeneralBookDetailFragment();
            bundle.putString("title", bookTitle);
            bundle.putString("desc", bookDesc);
            bundle.putString("available copies", noOfBooksAvail);
            bundle.putString("taker count", noOfTakers);
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
