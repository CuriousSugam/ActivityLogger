package com.lftechnology.activitylogger.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lftechnology.activitylogger.AllAppsDetailActivity;
import com.lftechnology.activitylogger.MainActivity;
import com.lftechnology.activitylogger.OnItemClickListener;
import com.lftechnology.activitylogger.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * It is a Viewholder for the RecyclerView of the AllAppsActivity
 * <p/>
 * Created by Sugam on 7/27/2016.
 */
public class AllAppsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;

    @BindView(R.id.image_view_app_icon)
    ImageView applicationIconImageView;

    @BindView(R.id.txt_app_name)
    TextView applicationNameTextView;


    public AllAppsViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }


    public ImageView getApplicationIconImageView() {
        return applicationIconImageView;
    }

    public TextView getApplicationNameTextView() {
        return applicationNameTextView;
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, AllAppsDetailActivity.class);
        context.startActivity(intent);
    }


}



