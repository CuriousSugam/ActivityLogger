package com.lftechnology.activitylogger.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.lftechnology.activitylogger.Interfaces.ChartsFragmentsCommunicator;
import com.lftechnology.activitylogger.R;

/**
 * Created by sparsha on 7/18/2016.
 */
public class FragmentTop5ActivitySelection extends Fragment implements View.OnClickListener {
    Button buttonPieChartSelection,buttonBarChartSelection;
    ChartsFragmentsCommunicator communicator;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_top5_selection,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        buttonPieChartSelection = (Button) getActivity().findViewById(R.id.buttonShowPieChart);
        buttonBarChartSelection = (Button) getActivity().findViewById(R.id.buttonShowBarChart);
        communicator = (ChartsFragmentsCommunicator) getActivity();
        buttonPieChartSelection.setOnClickListener(this);
        buttonBarChartSelection.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonShowBarChart){
           communicator.setBarChartImage();
//           Toast.makeText(getActivity(),"BarChart",Toast.LENGTH_SHORT).show();
        }
        else if (view.getId()==R.id.buttonShowPieChart){
            communicator.setPieChartImage();
//            Toast.makeText(getActivity(),"PieChart",Toast.LENGTH_SHORT).show();
        }
    }
}
