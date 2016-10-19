package com.luseen.verticalintrolibrary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SimpleFragment extends Fragment {

    private static final String VERTICAL_INTRO_ITEM_BUNDLE_KEY = "verticalIntroItemBundleKey";

    public static SimpleFragment newInstance(VerticalIntroItem verticalIntroItem) {
        Bundle args = new Bundle();
        args.putParcelable(VERTICAL_INTRO_ITEM_BUNDLE_KEY, verticalIntroItem);
        SimpleFragment fragment = new SimpleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple, container, false);
        VerticalIntroItem verticalIntroItem = getArguments().getParcelable(VERTICAL_INTRO_ITEM_BUNDLE_KEY);
        view.setBackgroundColor(ContextCompat.getColor(getActivity(), verticalIntroItem.getColor()));
        return view;
    }

}
