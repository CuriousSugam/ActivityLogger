package com.lftechnology.activitylogger.Fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.lftechnology.activitylogger.R;

/**
 * Created by sparsha on 7/18/2016.
 */
public class FragmentTop5ActivityCharts extends Fragment {
    ImageView imageView;
    Bitmap bitmap;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_top5_charts,container,false);
        if(savedInstanceState!=null){
            bitmap = savedInstanceState.getParcelable("image");
            ImageView savedImageView = (ImageView) view.findViewById(R.id.chartImage);
            savedImageView.setImageBitmap(bitmap);
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView = (ImageView) getActivity().findViewById(R.id.chartImage);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("image",bitmap);
    }

    public void setImage(Bitmap image){
        bitmap = image;
        imageView.setImageBitmap(image);
    }
}
