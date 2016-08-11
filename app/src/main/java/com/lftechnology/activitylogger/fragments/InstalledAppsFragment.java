package com.lftechnology.activitylogger.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lftechnology.activitylogger.adapter.AllAppsRecyclerViewAdapter;
import com.lftechnology.activitylogger.R;
import com.lftechnology.activitylogger.RawAppInfo;
import com.lftechnology.activitylogger.model.AppDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * It is a fragment which displays all the installed applications in the system
 */
public class InstalledAppsFragment extends Fragment {

    private Context context;

    @BindView(R.id.recycler_view_all_apps)
    RecyclerView recyclerView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        List<AppDetails> appDetailsList = getArguments().getParcelableArrayList(RawAppInfo.INSTALLED_APP);
        View view = inflater.inflate(R.layout.layout_all_apps_fragment, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        recyclerView.setAdapter(new AllAppsRecyclerViewAdapter(context, appDetailsList));

        return view;
    }
}
