package com.random.BookMobile.Fragments_Bar;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.random.BookMobile.MeActivity.BooksInfoList;
import com.random.BookMobile.MeActivity.MeChangePersonalInfo;
import com.random.BookMobile.R;

import com.random.BookMobile.R;
import com.random.BookMobile.LoginActivity;
import com.random.BookMobile.MainActivity;



public class ProfileFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    SharedPreferences prf;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public Button Personal;
    public Button BooksList;
    public Button IREvents;
    public Button LogOut;
    private TextView userid;
    private TextView userEmail;
    private TextView userCredits;
    private OnFragmentInteractionListener mListener;
    private ImageView Avatar;

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        Personal = view.findViewById(R.id.Personal);
        Personal.setOnClickListener(this);

        userid = view.findViewById(R.id.UserId);
        userEmail = view.findViewById(R.id.UserEmail);
        userCredits = view.findViewById(R.id.UserCredits);
        prf = getActivity().getSharedPreferences("user_details",Context.MODE_PRIVATE);
        String email = prf.getString("email",null);
        String credits = prf.getString("credits", null);
        String username = prf.getString("username",null);
        Log.v("USERNAME - PROFILE", username);
        userid.setText(username);
        userEmail.setText(email);
        userCredits.append(" "+credits);

        LogOut = view.findViewById(R.id.LogOut);
        LogOut.setOnClickListener(this);
        IREvents =  view.findViewById(R.id.BooksList);
        IREvents.setOnClickListener(this);

        Avatar=view.findViewById(R.id.UserDp);
        switch (LoginActivity.getAvatarChoice()){
            case 1:
                Avatar.setImageResource(R.drawable.books1);
                break;
            case 2:
                Avatar.setImageResource(R.drawable.books2);
                break;
            case 3:
                Avatar.setImageResource(R.drawable.books3);
                break;
            case 4:
                Avatar.setImageResource(R.drawable.books4);
                break;
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.Personal:
                Intent startNewActivity = new Intent(getContext(), MeChangePersonalInfo.class);
                startActivity(startNewActivity);
                break;

            case R.id.BooksList:
                Intent startNewActivity3 = new Intent(getContext(), BooksInfoList.class);

                startActivity(startNewActivity3);
                break;

            case R.id.LogOut:
                prf = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prf.edit();
                editor.clear();
                editor.apply();
                Intent startNewActivity4 = new Intent(getContext(),LoginActivity.class);
                startActivity(startNewActivity4);
                break;

            default:
                break;
        }


    }

    @Override
    public void onResume() {
/*        int id = getActivity().getIntent().getIntExtra("id", 0);
        if(id==2){
            MainActivity.mainViewPager.setCurrentItem(2);
        }*/
        super.onResume();
    }


}

