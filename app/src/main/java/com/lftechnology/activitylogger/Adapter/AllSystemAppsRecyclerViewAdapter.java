package com.lftechnology.activitylogger.Adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lftechnology.activitylogger.AllApps;
import com.lftechnology.activitylogger.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sugam on 7/18/2016.
 */
public class AllSystemAppsRecyclerViewAdapter extends RecyclerView.Adapter<AllSystemAppsRecyclerViewAdapter .AllSystemAppsViewholder >  {
    private Context context;
    private List<ResolveInfo> packageInfoList;

    public AllSystemAppsRecyclerViewAdapter(Context context, List<ResolveInfo> packageInfoList){
        this.context = context;
        this.packageInfoList = packageInfoList;
    }

    @Override
    public AllSystemAppsViewholder  onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_all_applications_grid, parent, false);
        return new AllSystemAppsViewholder (view);
    }



    @Override
    public void onBindViewHolder(AllSystemAppsViewholder  holder, int position) {
        ApplicationInfo applicationInfo = packageInfoList.get(position).activityInfo.applicationInfo;
        String applicationLabel = String.valueOf(context.getPackageManager().getApplicationLabel(applicationInfo));
        if(applicationLabel.length() > 9){
            holder.applicationName.setText(applicationLabel.substring(0, 8)+"...");
        }else {
            holder.applicationName.setText(applicationLabel);
        }
        holder.applicationIcon.setImageDrawable(context.getPackageManager().getApplicationIcon(applicationInfo));
    }

    @Override
    public int getItemCount() {
        return packageInfoList.size();
    }

    public class AllSystemAppsViewholder extends RecyclerView.ViewHolder {

        private ImageView applicationIcon;
        private TextView applicationName;

        public AllSystemAppsViewholder(View itemView) {
            super(itemView);
            applicationIcon = (ImageView) itemView.findViewById(R.id.image_view_app_icon);
            applicationName = (TextView) itemView.findViewById(R.id.txt_app_name);
        }

    }
}
