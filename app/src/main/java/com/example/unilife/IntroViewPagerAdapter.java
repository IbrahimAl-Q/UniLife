package com.example.unilife;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {

    Context mContext;
    List<ScreenItem> mListScreen;

    public IntroViewPagerAdapter(Context mContext, List<ScreenItem> mListScreen) {
        this.mContext = mContext;
        this.mListScreen = mListScreen;
    }


    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.layout_screen,null);
        ImageView imgslide= layoutScreen.findViewById(R.id.intro_img);
        TextView introTitle= layoutScreen.findViewById(R.id.intro_title);
        TextView introDescription= layoutScreen.findViewById(R.id.intro_description);

        introTitle.setText(mListScreen.get(position).getTitle());
        introDescription.setText(mListScreen.get(position).getDescription());
        imgslide.setImageResource(mListScreen.get(position).getScreenImg());

        container.addView(layoutScreen);
        return layoutScreen;
    }
}
