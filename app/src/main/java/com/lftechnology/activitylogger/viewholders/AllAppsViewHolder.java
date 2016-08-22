package com.lftechnology.activitylogger.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lftechnology.activitylogger.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * It is a Viewholder for the RecyclerView of the AllAppsActivity
 * <p/>
 * Created by Sugam on 7/27/2016.
 */
public class AllAppsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_view_app_icon)
    ImageView applicationIconImageView;

    @BindView(R.id.txt_app_name)
    TextView applicationNameTextView;

    public AllAppsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public ImageView getApplicationIconImageView() {
        return applicationIconImageView;
    }

    public TextView getApplicationNameTextView() {
        return applicationNameTextView;
    }
}