package com.random.BookMobile.Fragments_Bar;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.random.BookMobile.GeneralBookDetailFragment;
import com.random.BookMobile.R;

public class HomePageFragment extends Fragment{
    private static final String TAG = "Home Page";
    Button lotfButton;
    TextView welcomeText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.home_fragment, container, false);
        welcomeText = v.findViewById(R.id.welcomeText);

        try{
            String newUser = getActivity().getIntent().getStringExtra("newUser");
            welcomeText.append(newUser);
        }
        catch(Exception e){
            String newUser = getActivity().getIntent().getStringExtra("loginUser");
            welcomeText.append(newUser);
        }

       // Code that adds buttons programmatically - will be used when generating new recommended books
   /*     Button myButton = new Button(getContext());
        myButton.setText("Push Me");

        LinearLayout ll = (LinearLayout) v.findViewById(R.id.buttonLayoutRec);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ll.addView(myButton, lp);*/

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
}
