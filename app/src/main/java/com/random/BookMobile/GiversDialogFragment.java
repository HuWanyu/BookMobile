package com.random.BookMobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.random.BookMobile.Fragments_Bar.AddListingFragment;

public class GiversDialogFragment extends AppCompatDialogFragment {
    View customView;

    ListView list;

    String[] giversNames ={
            "Prash", "Yi Hao", "Wan Yu"
    };

    String[] bookCond ={
           "New", "Old", "Satisfactory"
    };

    String[] timings ={
            "12pm","2pm","4pm"
    };

    int[] costs ={
          3,4,5
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return customView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        customView = LayoutInflater.from(getActivity())
                .inflate(R.layout.givers_list, null);

        MyListAdapter adapter=new MyListAdapter(getActivity(), giversNames, bookCond,timings,costs);
        list=customView.findViewById(R.id.my_list);
        list.setAdapter(adapter);

        //custom Dialog Title
        TextView customText = new TextView(getContext());
        customText.setTextSize(30);
        customText.setText("Pick A Giver:");
        customText.setPadding(50,50,10,10);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                // TODO Auto-generated method stub
                if(position == 0) {
                    //code specific to first list item
                    Toast.makeText(getContext(),"Giver selected:"+giversNames[0],Toast.LENGTH_SHORT).show();
                }

                else if(position == 1) {
                    //code specific to 2nd list item
                    Toast.makeText(getContext(),"Giver selected:"+giversNames[1],Toast.LENGTH_SHORT).show();
                }

                else if(position == 2) {

                    Toast.makeText(getContext(),"Giver selected:"+giversNames[2],Toast.LENGTH_SHORT).show();
                }

            }
        });



        return new AlertDialog.Builder(getActivity())
                .setCustomTitle(customText)
                .setView(customView)
                .create();
    }
}
