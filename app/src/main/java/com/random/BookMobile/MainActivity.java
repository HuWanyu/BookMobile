package com.random.BookMobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.random.BookMobile.Fragments_Bar.AddListingFragment;
import com.random.BookMobile.Fragments_Bar.ChatFragment;
import com.random.BookMobile.Fragments_Bar.HomePageFragment;
import com.random.BookMobile.Fragments_Bar.MainPageAdapter;
import com.random.BookMobile.Fragments_Bar.ProfileFragment;


public class MainActivity extends AppCompatActivity{
    private static final String TAG = "MainActivity";
    public static ViewPager mainViewPager;
    private BottomNavigationView mainNav;
    SharedPreferences prf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        if(!prf.contains("username") && !prf.contains("password")) {
            startActivity(logoutIntent);
        }
        mainViewPager = findViewById(R.id.main_view_pager);
        final MainPageAdapter mainPageAdapter = new MainPageAdapter(getSupportFragmentManager());
        mainViewPager.setAdapter(mainPageAdapter);

        mainNav = findViewById(R.id.main_page);
        mainNav.setSelectedItemId(R.id.mainNav_Home);
        mainNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toggleHideyBar();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.mainNav_AddListing:
                    mainViewPager.setCurrentItem(0);
                    return true;
                case R.id.mainNav_Home:
                    mainViewPager.setCurrentItem(1);
                    return true;
                case R.id.mainNav_Profile:
                    mainViewPager.setCurrentItem(2);
                    return true;
                case R.id.mainNav_Chat:
                    mainViewPager.setCurrentItem(3);
                    return true;
                default:
                    mainViewPager.setCurrentItem(1);
                    return true;
            }
        }

    };


    /**
     * Detects and toggles immersive mode (also known as "hidey bar" mode).
     */
    public void toggleHideyBar() {

        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i(TAG, "Turning immersive mode mode off. ");
        } else {
            Log.i(TAG, "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    @Override
    protected void onResume() {
        int id = getIntent().getIntExtra("id", 0);
        if (id == 0){
            Fragment fragmen = new HomePageFragment();
            FragmentManager fmanger = getSupportFragmentManager();
            FragmentTransaction transaction = fmanger.beginTransaction();
            transaction.replace(R.id.main_view_pager, fragmen);
            transaction.commit();
            mainViewPager.setCurrentItem(1);

            Intent i=new Intent();
            i.setClass(MainActivity.this,HomePageFragment.class);
            i.putExtra("id",0);
        }
        if (id == 1){
            Fragment fragmen = new AddListingFragment();
            FragmentManager fmanger = getSupportFragmentManager();
            FragmentTransaction transaction = fmanger.beginTransaction();
            transaction.replace(R.id.main_view_pager, fragmen);
            transaction.commit();
            mainViewPager.setCurrentItem(0);//

            Intent i=new Intent();
            i.setClass(MainActivity.this, AddListingFragment.class);
            i.putExtra("id",1);
        }
        if (id == 2) {
            Fragment fragmen = new ProfileFragment();
            FragmentManager fmanger = getSupportFragmentManager();
            FragmentTransaction transaction = fmanger.beginTransaction();
            transaction.replace(R.id.main_view_pager, fragmen);
            transaction.commit();
            mainViewPager.setCurrentItem(2);//

            Intent i=new Intent();
            i.setClass(MainActivity.this,ProfileFragment.class);
            i.putExtra("id",2);
        }

        if (id == 3) {
            Fragment fragmen = new ChatFragment();
            FragmentManager fmanger = getSupportFragmentManager();
            FragmentTransaction transaction = fmanger.beginTransaction();
            transaction.replace(R.id.main_view_pager, fragmen);
            transaction.commit();
            mainViewPager.setCurrentItem(3);//

            Intent i=new Intent();
            i.setClass(MainActivity.this,ChatFragment.class);
            i.putExtra("id",3);
            Log.d("Reached Chat Fragment", "Chat Frag open");
        }
        super.onResume();
    }

}



