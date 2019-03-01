package com.random.BookMobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.random.BookMobile.Fragments_Bar.AddListingFragment;
import com.random.BookMobile.Fragments_Bar.HomePageFragment;
import com.random.BookMobile.Fragments_Bar.MainPageAdapter;

public class GeneralBookDetailFragment extends AppCompatDialogFragment {
    public static ViewPager mainViewPager;

    TextView bookTitle;
    TextView bookSummary;
    TextView copiesNo;
    TextView takersNo;
    Button lotfButton;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //create view
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.gen_book_details_layout, null);
        mainViewPager = getActivity().findViewById(R.id.main_view_pager);

        lotfButton = getActivity().findViewById(R.id.lotf);
        String buttonText = lotfButton.getText().toString();
        bookTitle = v.findViewById(R.id.bookTitleText);
        bookTitle.setText(buttonText);

        bookSummary = v.findViewById(R.id.bookSummary);
        bookSummary.setText("The story of a few boys stranded on an island.");

        copiesNo = v.findViewById(R.id.copiesNo);
        takersNo = v.findViewById(R.id.takersNo);

        //button listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int takerCount = Integer.valueOf(takersNo.getText().toString());
                switch(which)
                {
                    case DialogInterface.BUTTON_POSITIVE:
                       takerCount++;
                        Toast.makeText(getContext(), "Number of Takers increased to "+takerCount, Toast.LENGTH_SHORT).show();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dismiss();

                       /* // Create new fragment and transaction
                        Fragment newFragment = new AddListingFragment();
                        // consider using Java coding conventions (upper first char class names!!!)
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack
                        transaction.replace(R.id.main_view_pager, newFragment);
                        transaction.addToBackStack(null);

                        // Commit the transaction
                        transaction.commit();
                        BottomNavigationView mainNav=getActivity().findViewById(R.id.main_page);
                        mainNav.setSelectedItemId(R.id.mainNav_AddListing);*/
                        //mainViewPager.setCurrentItem(0);
                        break;

                }

                Log.i("Tag", "You clicked the dialog button");
            }
        };
        //build the alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("Book Details")
                .setView(v)
                .setPositiveButton("Take It!", listener)
                .setNegativeButton("List It!", listener)
                .create();
    }
}
