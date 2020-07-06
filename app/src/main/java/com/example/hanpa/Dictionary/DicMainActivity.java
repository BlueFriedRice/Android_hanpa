package com.example.hanpa.Dictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hanpa.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DicMainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TextView toolbar_title;
    int lang;

    private TabLayout tabLayout;
    private ArrayList<String> tabNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dic_main);

        lang = getIntent().getIntExtra("language",0);

        toolbar_title = (TextView)findViewById(R.id.toolbar_title);

        viewPager = (ViewPager)findViewById(R.id.view_pager);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        //set gravity for tab bar
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        if (toolbar != null) {
//            toolbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
//        }

        loadTabName();

        setTabLayout();

        //set viewpager adapter
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        //change Tab selection when swipe ViewPager
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //change ViewPager page when tab selected
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.N)
    private void setTabLayout(){
        tabLayout = findViewById(R.id.tab_layout);
        tabNames.stream().forEach(name ->tabLayout.addTab(tabLayout.newTab().setText(name)));
    }

    private void loadTabName(){
        switch (lang) {
            case 0:
                toolbar_title.setText("사전");
                tabNames.add("줄임말");
                tabNames.add("순우리말");
                break;
            case 1:
                toolbar_title.setText("Dictionary");
                tabNames.add("abbreviation");
                tabNames.add("Pure Language");
                break;
            case 2:
                toolbar_title.setText("词典");
                tabNames.add("缩略语");
                tabNames.add("纯韩语");
                break;
            case 3:
                toolbar_title.setText("じぜん");
                tabNames.add("りゃくご");
                tabNames.add("じゅんすい かんこくご");
                break;

        }

    }

}
