package com.lftechnology.activitylogger.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lftechnology.activitylogger.AllAppsActivity;
import com.lftechnology.activitylogger.MainActivity;
import com.lftechnology.activitylogger.MobileDataActivity;
import com.lftechnology.activitylogger.R;
import com.lftechnology.activitylogger.TimeActivity;
import com.lftechnology.activitylogger.WifiActivity;
import com.lftechnology.activitylogger.model.AppDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * This Fragment displays the grid menu
 * <p/>
 * Created by Sugam Shakya on 7/12/2016.
 */
public class SecondMainFragment extends Fragment {

    private List<AppDetails> appDetailsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_main_fragment, container, false);
        ButterKnife.bind(this, view);
        appDetailsList = getArguments().getParcelableArrayList(MainActivity.APP_DETAILS);
        return view;
    }

    @OnClick(R.id.tv_menu_wifi)
    public void onClickWifiMenu(){
        startActivity(new Intent(getActivity(), WifiActivity.class));
    }

    @OnClick(R.id.tv_menu_all_apps)
    public void onClickAllAppsMenu(){
        Intent intent = new Intent(getActivity(), AllAppsActivity.class);
        intent.putParcelableArrayListExtra(MainActivity.APP_DETAILS, (ArrayList<? extends Parcelable>) appDetailsList);
        startActivity(intent);
    }

    @OnClick(R.id.tv_menu_mobile_data)
    public void onClickMobileDataMenu(){
        startActivity(new Intent(getActivity(), MobileDataActivity.class));
    }

    @OnClick(R.id.tv_menu_time)
    public void OnClickTimeMenu(){
        startActivity(new Intent(getActivity(), TimeActivity.class));
    }

}
