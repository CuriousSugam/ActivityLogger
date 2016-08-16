package com.lftechnology.activitylogger.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lftechnology.activitylogger.model.EachAppDetails;
import com.lftechnology.activitylogger.R;

import java.util.List;

/**
 * Created by sparsha on 7/8/2016.
 * A custom Adapter that shows App image app name and appusage duration in recycler view
 */
public class CustomAdapterAppDetails extends RecyclerView.Adapter<CustomAdapterAppDetails.DetailsViewHolder> {

    private List<EachAppDetails> eachAppDetailsList;
    LayoutInflater inflater;
    Context context;

    public CustomAdapterAppDetails(Context context, List<EachAppDetails> eachAppDetailsList) {
        this.eachAppDetailsList = eachAppDetailsList;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public DetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_custom_app_info, parent, false);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailsViewHolder holder, int position) {
        final EachAppDetails current = eachAppDetailsList.get(position);
        //Convert time with Long format into hh:mm:ss format
        String durationInTime = String.format("%02d", (current.eachAppUsageDuration / 1000 / 3600))
                + " : " + String.format("%02d", (((current.eachAppUsageDuration / 1000) % 3600) / 60))
                + " : " + String.format("%02d", ((current.eachAppUsageDuration / 1000) % 60));
        holder.appIconImage.setImageDrawable(current.eachAppIcon);
        holder.appNameText.setText(current.eachAppName);
        holder.appUsageDurationText.setText(durationInTime);

    }

    @Override
    public int getItemCount() {
        return eachAppDetailsList.size();
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        ImageView appIconImage;
        TextView appNameText, appUsageDurationText;

        public DetailsViewHolder(View itemView) {
            super(itemView);
            appIconImage = (ImageView) itemView.findViewById(R.id.appIcon);
            appNameText = (TextView) itemView.findViewById(R.id.textAppName);
            appUsageDurationText = (TextView) itemView.findViewById(R.id.textAppUsageDuration);
        }
    }
}
