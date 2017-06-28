package com.luseen.verticalintrolibrary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class VerticalIntroFragment extends Fragment {

    private static final String VERTICAL_INTRO_ITEM_BUNDLE_KEY = "verticalIntroItemBundleKey";

    public static VerticalIntroFragment newInstance(VerticalIntroItem verticalIntroItem) {
        Bundle args = new Bundle();
        args.putParcelable(VERTICAL_INTRO_ITEM_BUNDLE_KEY, verticalIntroItem);
        VerticalIntroFragment fragment = new VerticalIntroFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vertical_intro_base_layout, container, false);
        VerticalIntroItem verticalIntroItem = getArguments().getParcelable(VERTICAL_INTRO_ITEM_BUNDLE_KEY);
        if (verticalIntroItem != null) {
            TextView text = (TextView) view.findViewById(R.id.text);
            TextView title = (TextView) view.findViewById(R.id.title);
            ImageView image = (ImageView) view.findViewById(R.id.image);

            text.setText(verticalIntroItem.getText());
            title.setText(verticalIntroItem.getTitle());

            text.setTextColor(ContextCompat.getColor(getActivity(), verticalIntroItem.getTextColor()));
            title.setTextColor(ContextCompat.getColor(getActivity(), verticalIntroItem.getTitleColor()));

            if (verticalIntroItem.getTextSize() != 0)
                text.setTextSize(verticalIntroItem.getTextSize());
            if (verticalIntroItem.getTitleSize() != 0)
                title.setTextSize(verticalIntroItem.getTitleSize());

            image.setImageResource(verticalIntroItem.getImage());
            view.setBackgroundColor(ContextCompat.getColor(getActivity(), verticalIntroItem.getBackgroundColor()));

            if (verticalIntroItem.getCustomTypeFace() != null) {
                text.setTypeface(verticalIntroItem.getCustomTypeFace());
                title.setTypeface(verticalIntroItem.getCustomTypeFace());
            }
        } else {
            Log.e(VerticalIntro.TAG, "Something went wrong");
        }
        return view;
    }
}
