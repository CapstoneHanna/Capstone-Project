package com.example.capstonefinaldiary;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class StatisticsActivity extends AppCompatActivity {

    private PieChart pieChart;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentAdapter adapter;
    private ArrayList <String> tabNames = new ArrayList<>();
    private MenuActivity menuActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        menuActivity = new MenuActivity(this);

        loadTabName();
        setTabLayout();
        setViewPager();

        // Firebase에서 데이터를 가져오는 함수를 호출합니다.
        // fetchEmotionData();
    }
    @TargetApi(Build.VERSION_CODES.N)
    private void setTabLayout(){
        tabLayout = findViewById(R.id.tab);
        tabNames.stream().forEach(name ->tabLayout.addTab(tabLayout.newTab().setText(name)));
    }
    private void loadTabName(){
        tabNames.add("WEEK");
        tabNames.add("MONTH");
    }
    private void setViewPager() {
        adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
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

}