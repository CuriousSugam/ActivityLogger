package com.lftechnology.activitylogger.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lftechnology.activitylogger.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * It is fragment that displays the information  about the top usage applicaiton
 * <p/>
 * Created by Sugam on 8/11/2016.
 */
public class InformationSliderFragment extends Fragment {

    @BindView(R.id.tv_info_slider)
    TextView textViewInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information_slider, container, false);
        ButterKnife.bind(this, view);

//         get the arguments from bundle and assign it to textview of the fragment
        Bundle b = getArguments();
        String info = getArguments().getString("info", "no values");
        textViewInfo.setText(info);
        return view;
    }


}
