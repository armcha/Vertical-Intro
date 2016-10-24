package com.luseen.verticalintrolibrary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Chatikyan on 18.10.2016.
 */

class VerticalIntroPagerAdapter extends FragmentPagerAdapter {

    private List<VerticalIntroItem> verticalIntroItemList;

    VerticalIntroPagerAdapter(FragmentManager fm, List<VerticalIntroItem> verticalIntroItemList) {
        super(fm);
        this.verticalIntroItemList = verticalIntroItemList;
    }

    @Override
    public Fragment getItem(int position) {
        VerticalIntroItem verticalIntroItem = verticalIntroItemList.get(position);
        return VerticalIntroFragment.newInstance(verticalIntroItem);
    }

    @Override
    public int getCount() {
        return verticalIntroItemList.size();
    }
}
