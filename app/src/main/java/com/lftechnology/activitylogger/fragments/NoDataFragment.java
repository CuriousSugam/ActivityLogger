package com.lftechnology.activitylogger.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lftechnology.activitylogger.R;

/**
 * This fragment displays the no data to display information to the user.
 * <p/>
 * Created by Sugam on 8/21/2016.
 */
public class NoDataFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_no_data, container, false);
    }
}
