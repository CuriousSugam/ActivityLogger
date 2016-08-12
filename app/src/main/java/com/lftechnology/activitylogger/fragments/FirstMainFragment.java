package com.lftechnology.activitylogger.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lftechnology.activitylogger.R;

/**
 * This fragment displays the slider of the important information like the most used app of the day,
 * application that used the most network data and other random productivity guidelines.
 * <p/>
 * Created by DevilDewzone on 7/11/2016.
 */
public class FirstMainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.first_main_fragment_layout, container, false);
        // TODO: 8/11/2016 add a viewpager here to display the information in the slider
    }

}
