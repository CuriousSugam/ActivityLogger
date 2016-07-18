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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_all_application_list, parent, false);
        return new AllAppsViewholder(view);
    }

    @Override
    public void onBindViewHolder(AllAppsViewholder holder, int position) {
        ApplicationInfo applicationInfo = packageInfoList.get(position).applicationInfo;
        holder.applicationName.setText(context.getPackageManager().getApplicationLabel(applicationInfo));
        holder.applicationIcon.setImageDrawable(context.getPackageManager().getApplicationIcon(applicationInfo));

        holder.receivedBytes.setText("");
        holder.transmittedBytes.setText("");
        holder.totalBytes.setText("");
    }

    @Override
    public int getItemCount() {
        return packageInfoList.size();
    }

    public class AllAppsViewholder extends RecyclerView.ViewHolder {

        private ImageView applicationIcon;
        private TextView applicationName;
        private TextView receivedBytes;
        private TextView transmittedBytes;
        private TextView totalBytes;

        public AllAppsViewholder(View itemView) {
            super(itemView);
            applicationIcon = (ImageView) itemView.findViewById(R.id.main_app_icon);
            applicationName = (TextView) itemView.findViewById(R.id.main_app_name);
            receivedBytes = (TextView) itemView.findViewById(R.id.txt_network_data_received);
            transmittedBytes = (TextView) itemView.findViewById(R.id.txt_network_data_trasmitted);
            totalBytes = (TextView) itemView.findViewById(R.id.txt_total_network_data);
        }

    }
}
