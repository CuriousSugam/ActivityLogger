package com.lftechnology.activitylogger.Adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lftechnology.activitylogger.R;
import com.lftechnology.activitylogger.model.NetworkUsageDetails;

import java.util.List;

/**
 * NetworkDataAdapter is an adapter class that binds the data (such as: application name, network bytes)
 * to the recycler view layout.
 *
 * Created by Sugam on 7/14/2016.
 */
public class NetworkDataAdapter extends RecyclerView.Adapter<NetworkDataAdapter.NetworkViewHolder> {

    private Context context;
    private List<NetworkUsageDetails> networkUsageDetailsList;

    /**
     *
     * @param context context of the calling
     * @param networkUsageDetailsList List that contains the object of NetworkUsageDetails to bind
     *                                the data to the view.
     */
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
        float rxBytes = (float) networkUsageDetailsList.get(position).getTotalRxBytes();
        float txBytes = (float) networkUsageDetailsList.get(position).getTotalTxBytes();
        float total = rxBytes + txBytes;

        if(rxBytes > (1024*1024*1024)){
            holder.receivedBytes.setText("Received:  "+String.format("%.2f", rxBytes/(1024*1024*1024))+ " GB");
        }else if(rxBytes > (1024*1024)){
            holder.receivedBytes.setText("Received:  "+String.format("%.2f", rxBytes/(1024*1024))+ " MB");
        }else if(rxBytes > 1024){
            holder.receivedBytes.setText("Received:  "+String.format("%.2f", rxBytes/1024)+ " KB");
        }else{
            holder.receivedBytes.setText("Received:  "+rxBytes+ " bytes");
        }

        if(rxBytes > (1024*1024*1024)){
            holder.transmittedBytes.setText("Transmitted:  "+String.format("%.2f", txBytes/(1024*1024*1024))+" GB");
        }else if(rxBytes > (1024*1024)){
            holder.transmittedBytes.setText("Transmitted:  "+String.format("%.2f", txBytes/(1024*1024))+" MB");
        }else if(rxBytes > 1024){
            holder.transmittedBytes.setText("Transmitted:  "+String.format("%.2f", txBytes/1024)+" KB");
        }else{
            holder.transmittedBytes.setText("Transmitted:  "+txBytes+ " bytes");
        }

        if(rxBytes > (1024*1024*1024)){
            holder.totalBytes.setText("Total:  "+ String.format("%.2f", total/(1024*1024*1024))+" GB");
        }else if(rxBytes > (1024*1024)){
            holder.totalBytes.setText("Total:  "+ String.format("%.2f", total/(1024*1024))+" MB");
        }else if(rxBytes > 1024){
            holder.totalBytes.setText("Total:  "+ String.format("%.2f", total/1024)+" KB");
        }else{
            holder.totalBytes.setText("Total:  "+total+ " bytes");
        }
    }

    @Override
    public int getItemCount() {
        return networkUsageDetailsList.size();
    }

    /**
     * Custom viewholder class. It gets references to the view components of the layout to which the
     * data is to be bound to
     */
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
