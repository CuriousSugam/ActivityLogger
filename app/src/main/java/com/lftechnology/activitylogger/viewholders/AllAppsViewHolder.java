package com.lftechnology.activitylogger.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lftechnology.activitylogger.AllAppsDetailActivity;
import com.lftechnology.activitylogger.MainActivity;
import com.lftechnology.activitylogger.OnItemClickListener;
import com.lftechnology.activitylogger.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * It is a Viewholder for the RecyclerView of the AllAppsActivity
 * <p/>
 * Created by Sugam on 7/27/2016.
 */
public class AllAppsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    Context context;
    OnItemClickListener mItemClickListener;
    Adapter adapter;

    @BindView(R.id.image_view_app_icon)
    ImageView applicationIconImageView;

    @BindView(R.id.txt_app_name)
    TextView applicationNameTextView;



    public AllAppsViewHolder(View itemView) {
        super(itemView);
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
//        if (mItemClickListener !=null){
//            mItemClickListener.onItemClick(view, getAdapterPosition()Position());

        //context = itemView.getContext();
//        Intent i = new Intent(context, AllAppsDetailActivity.class);
//        context.startActivity(i);
        context.startActivity(new Intent(context,AllAppsDetailActivity.class));

        //mItemClickListner.onItemClick(view, getPosition());
//        Intent activity = new Intent(this,AllAppsDetailActivity.class);
//        vistartActivity(activity);


    }
}


//public void setOnItemClickListener(final OnItemClickListener mItemClickListener){
//    this.mItemClickListener = mItemClickListener;
//}
//}



