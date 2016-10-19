package com.luseen.verticalintro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleFragment extends Fragment {

    private static final String BACKGROUND_COLOR = "backgroundColor";

    public static SimpleFragment newInstance(int color) {

        Bundle args = new Bundle();
        args.putInt(BACKGROUND_COLOR,color);
        SimpleFragment fragment = new SimpleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_simple, container, false);
        int color = getArguments().getInt(BACKGROUND_COLOR);
        view.setBackgroundColor(ContextCompat.getColor(getActivity(),color));
        return view;
    }

}
