package com.lftechnology.activitylogger;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lftechnology.activitylogger.model.NetworkUsageDetails;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sugam on 7/14/2016.
 */
public class NetworkDataAdapter extends RecyclerView.Adapter<NetworkDataAdapter.NetworkViewHolder> {

    private Context context;
    private List<NetworkUsageDetails> networkUsageDetailsList;

    public NetworkDataAdapter(Context context, List<NetworkUsageDetails> networkUsageDetailsList){
        this.context = context;
        this.networkUsageDetailsList = networkUsageDetailsList;
    }

    @Override
    public NetworkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_all_application_list, parent, false);
        return new NetworkViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NetworkViewHolder holder, int position) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager()
                    .getApplicationInfo(networkUsageDetailsList
                            .get(position).getPackageName(), PackageManager.GET_META_DATA);
            holder.applicationName.setText(context.getPackageManager().getApplicationLabel(applicationInfo));
            holder.applicationIcon.setImageDrawable(context.getPackageManager().getApplicationIcon(applicationInfo));

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
//        DecimalFormat decimalFormat = new DecimalFormat("#.##");
//        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        float rxBytes = (float) networkUsageDetailsList.get(position).getTotalRxBytes();
        float txBytes = (float) networkUsageDetailsList.get(position).getTotalTxBytes();
        float total = rxBytes + txBytes;
//        holder.receivedBytes.setText("Received:  "+decimalFormat.format(rxBytes)+" MB");
//        holder.transmittedBytes.setText("Transmitted:  "+decimalFormat.format(txBytes)+" MB");
//        holder.totalBytes.setText("Total:  "+decimalFormat.format(total)+ " MB");
        holder.receivedBytes.setText("Received:  "+rxBytes/(1024*1024));
        holder.transmittedBytes.setText("Transmitted:  "+txBytes/(1024*1024));
        holder.totalBytes.setText("Total:  "+total/(1024*1024));
//        Log.e("message", rxBytes+" + "+txBytes+" = "+total);

    }

    @Override
    public int getItemCount() {
        return networkUsageDetailsList.size();
    }

    public class NetworkViewHolder extends RecyclerView.ViewHolder{

        private ImageView applicationIcon;
        private TextView applicationName;
        private TextView receivedBytes;
        private TextView transmittedBytes;
        private TextView totalBytes;

        public NetworkViewHolder(View itemView) {
            super(itemView);
            applicationIcon = (ImageView) itemView.findViewById(R.id.main_app_icon);
            applicationName = (TextView) itemView.findViewById(R.id.main_app_name);
            receivedBytes = (TextView) itemView.findViewById(R.id.txt_network_data_received);
            transmittedBytes = (TextView) itemView.findViewById(R.id.txt_network_data_trasmitted);
            totalBytes = (TextView) itemView.findViewById(R.id.txt_total_network_data);
        }
    }
}
