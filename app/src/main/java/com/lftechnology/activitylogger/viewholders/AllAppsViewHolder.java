package com.lftechnology.activitylogger.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lftechnology.activitylogger.R;

/**
 * Created by Sugam on 7/27/2016.
 */
public class AllAppsViewHolder extends RecyclerView.ViewHolder {

    private ImageView applicationIconImageView;
    private TextView applicationNameTextView;

    public AllAppsViewHolder(View itemView) {
        super(itemView);
        applicationIconImageView = (ImageView) itemView.findViewById(R.id.image_view_app_icon);
        applicationNameTextView = (TextView) itemView.findViewById(R.id.txt_app_name);
    }

    public ImageView getApplicationIconImageView() {
        return applicationIconImageView;
    }

    public TextView getApplicationNameTextView() {
        return applicationNameTextView;
    }
}