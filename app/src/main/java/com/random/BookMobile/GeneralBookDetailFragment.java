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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.random.BookMobile.Fragments_Bar.AddListingFragment;
import com.random.BookMobile.Fragments_Bar.HomePageFragment;
import com.random.BookMobile.Fragments_Bar.MainPageAdapter;

public class GeneralBookDetailFragment extends AppCompatDialogFragment {
    public static ViewPager mainViewPager;
    public static int REQUEST_CODE = 0;
    TextView bookTitle;
    TextView bookSummary;
    TextView copiesNo;
    TextView takersNo;
    Button lotfButton;
    View customView;

    Bundle bundle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return customView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        customView = LayoutInflater.from(getActivity())
                .inflate(R.layout.gen_book_details_layout, null);

        bundle = this.getArguments();

        mainViewPager = getActivity().findViewById(R.id.main_view_pager);
        //lotfButton = getActivity().findViewById(R.id.lotf);
       // String buttonText = lotfButton.getText().toString();
        bookTitle = customView.findViewById(R.id.bookTitleText);
      //  bookTitle.setText(buttonText);

        bookSummary = customView.findViewById(R.id.bookSummary);
       // bookSummary.setText("The story of a few boys stranded on an island.");

        copiesNo = customView.findViewById(R.id.copiesNo);
        takersNo = customView.findViewById(R.id.takersNo);

        if(bundle != null){
            bookTitle.setText(bundle.getString("title"));
            bookSummary.setText(bundle.getString("desc"));
            copiesNo.setText(bundle.getString("available copies"));
            takersNo.setText(bundle.getString("taker count"));
        }

        //button listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int takerCount = Integer.valueOf(takersNo.getText().toString());
                switch(which)
                {
                    case DialogInterface.BUTTON_POSITIVE:
                        dismiss();
                        GiversDialogFragment giverList = new GiversDialogFragment();
                        giverList.setArguments(bundle);
                        FragmentTransaction ft = getParentFragment().getFragmentManager().beginTransaction();
                        if(giverList.getView() !=null)
                            ft.replace(R.id.main_view_pager, giverList);
                        ft.addToBackStack(null);
                        giverList.show(getParentFragment().getFragmentManager(), "Giver List");
                        /*takerCount++;
                        Toast.makeText(getContext(), "Number of Takers increased to "+takerCount, Toast.LENGTH_SHORT).show();*/
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dismiss();
                        // Create new fragment and transaction
                        AddListingFragment newFragment = new AddListingFragment();
                        // consider using Java coding conventions (upper first char class names!!!)
                        FragmentTransaction transaction = getParentFragment().getFragmentManager().beginTransaction();
                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack
                        if(newFragment.getView() !=null)
                        transaction.replace(R.id.main_view_pager, newFragment);
                        transaction.addToBackStack(null);

                        // Commit the transaction
                        transaction.commit();
                        BottomNavigationView mainNav=getActivity().findViewById(R.id.main_page);
                        mainNav.setSelectedItemId(R.id.mainNav_AddListing);
                        mainViewPager.setCurrentItem(0);
                        break;

                }

                Log.i("Tag", "You clicked the dialog button");
            }
        };

        return new AlertDialog.Builder(getActivity())
                .setTitle("Book Details")
                .setView(customView)
                .setPositiveButton("Take It!", listener)
                .setNegativeButton("List It!", listener)
                .create();
    }
}

