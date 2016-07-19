package com.lftechnology.activitylogger.Adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lftechnology.activitylogger.R;

import java.util.List;

/**
 * Created by Sugam on 7/15/2016.
 */
public class AllAppsAdapter extends RecyclerView.Adapter<AllAppsAdapter.AllAppsViewholder> {

    private Context context;
    private  List<PackageInfo> packageInfoList;

    public AllAppsAdapter(Context context, List<PackageInfo> packageInfoList){
        this.context = context;
        this.packageInfoList = packageInfoList;
    }

    @Override
    public AllAppsViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_all_applications_grid, parent, false);
        return new AllAppsViewholder(view);
    }

    @Override
    public void onBindViewHolder(AllAppsViewholder holder, int position) {
        ApplicationInfo applicationInfo = packageInfoList.get(position).applicationInfo;
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

    public class AllAppsViewholder extends RecyclerView.ViewHolder {

        private ImageView applicationIcon;
        private TextView applicationName;

        public AllAppsViewholder(View itemView) {
            super(itemView);
            applicationIcon = (ImageView) itemView.findViewById(R.id.image_view_app_icon);
            applicationName = (TextView) itemView.findViewById(R.id.txt_app_name);
        }

    }
}
