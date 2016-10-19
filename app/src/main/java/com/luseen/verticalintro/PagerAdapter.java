package com.luseen.verticalintro;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Chatikyan on 18.10.2016.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private int[] colors;

    public PagerAdapter(FragmentManager fm, int[] colors) {
        super(fm);
        this.colors = colors;
    }

    @Override
    public Fragment getItem(int position) {
        return SimpleFragment.newInstance(colors[position]);
    }

    @Override
    public int getCount() {
        return colors.length -1;
    }
}
