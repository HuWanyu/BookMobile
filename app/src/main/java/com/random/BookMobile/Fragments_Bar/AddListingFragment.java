package com.random.BookMobile.Fragments_Bar;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.random.BookMobile.R;


public class AddListingFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.add_listing_fragment,container,false);
        Resources res = getResources();

        //setting up dropdown list for book condition
        String[] conditionList = res.getStringArray(R.array.conArray);
        Spinner conditionSpinner = view.findViewById(R.id.conSpinner);
        ArrayAdapter<String> conAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, conditionList);
        conditionSpinner.setAdapter(conAdapter);

        //setting up dropdown list for am/pm
        String[] amPmList = res.getStringArray(R.array.ampmarray);
        Spinner amPmSpinner = view.findViewById(R.id.ampmSpinner);
        ArrayAdapter<String> amPmAdapter = new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_spinner_dropdown_item, amPmList);
        amPmSpinner.setAdapter(amPmAdapter);

        //setting up dropdown list for timings
        Integer[] timeList = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Spinner timeSpinner = view.findViewById(R.id.timeSpinner);
        ArrayAdapter<Integer> timeAdapter = new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_spinner_dropdown_item, timeList);
        timeSpinner.setAdapter(timeAdapter);
        return view;

    }
}