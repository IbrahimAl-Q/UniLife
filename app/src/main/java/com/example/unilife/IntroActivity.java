package com.example.unilife;

import android.content.Intent;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    Button next_btn;
    Button previous_btn;
    Button getStartedbtn;
    Button skipbtn;
    int position=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make the activity full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_intro);
        //initiate Views
        tabIndicator= (TabLayout)findViewById(R.id.tab_indicator);
        next_btn= (Button) findViewById(R.id.next_button);
        previous_btn= (Button) findViewById(R.id.previous_button);
        getStartedbtn= (Button) findViewById(R.id.getstarted_btn);
        skipbtn= (Button) findViewById(R.id.skip_btn);
        previous_btn.setVisibility(View.INVISIBLE);


        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),LoginNregisterActivity.class);
                startActivity(i);
            }
        });

        getStartedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),LoginNregisterActivity.class);
                startActivity(i);
            }
        });

        //hide the action bar
        getSupportActionBar().hide();

        //fill list screen
        final List<ScreenItem> mList= new ArrayList<>();
        mList.add(new ScreenItem("Sell Books", "Sell your course literature to other students ", R.drawable.books));
        mList.add(new ScreenItem("Buy Books", "Buy course literature from other students", R.drawable.buy));
        mList.add(new ScreenItem("Forum", "Discuss your courses, exams, and course literature with other students", R.drawable.discusionforum));

        //setup viewPager
        screenPager=findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);

        //configuring tablayout with viewpager
        tabIndicator.setupWithViewPager(screenPager);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position= screenPager.getCurrentItem();
                if(position<mList.size()){
                    position++;
                    screenPager.setCurrentItem(position);
                    previous_btn.setVisibility(View.VISIBLE);
                }
                if(position==0){
                    previous_btn.setVisibility(View.INVISIBLE);
                }

                if(position == mList.size()-1){
                    loadLastScreen();
                }
            }
        });


        previous_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position=screenPager.getCurrentItem();
                    if(position<mList.size()){
                        position--;
                        screenPager.setCurrentItem(position);
                        previous_btn.setVisibility(View.VISIBLE);
                        tabIndicator.setVisibility(View.VISIBLE);
                        getStartedbtn.setVisibility(View.INVISIBLE);

                    }

                if(position==0){
                    previous_btn.setVisibility(View.INVISIBLE);
                    tabIndicator.setVisibility(View.VISIBLE);
                    next_btn.setVisibility(View.VISIBLE);
                    getStartedbtn.setVisibility(View.INVISIBLE);
                }

                if(position==mList.size()-1){
                    tabIndicator.setVisibility(View.VISIBLE);
                    getStartedbtn.setVisibility(View.INVISIBLE);
                }

            }
        });


        //tablayout add change listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                previous_btn.setVisibility(View.VISIBLE);
                if(tab.getPosition()<mList.size()){
                     next_btn.setVisibility(View.VISIBLE);
                     tabIndicator.setVisibility(View.VISIBLE);
                     getStartedbtn.setVisibility(View.INVISIBLE);
                }
                 if(tab.getPosition()== mList.size()-1){

                    loadLastScreen();
                }

                if(tab.getPosition()==0){
                    previous_btn.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    private void loadLastScreen(){
            next_btn.setVisibility(View.INVISIBLE);
            tabIndicator.setVisibility(View.INVISIBLE);
            previous_btn.setVisibility(View.VISIBLE);
            getStartedbtn.setVisibility((View.VISIBLE));


            //get started button animation
    }
}
