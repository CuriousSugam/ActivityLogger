package com.lftechnology.activitylogger.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lftechnology.activitylogger.asyncTasks.BitmapWorkerTask;
import com.lftechnology.activitylogger.R;
import com.lftechnology.activitylogger.model.NetworkUsageDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * NetworkDataAdapter is an adapter class that binds the data (such as: application name, network bytes)
 * to the recycler view layout.
 *
 * Created by Sugam on 7/14/2016.
 */
public class NetworkDataAdapter extends RecyclerView.Adapter<NetworkDataAdapter.NetworkViewHolder> {

    private Context context;
    private List<NetworkUsageDetails> networkUsageDetailsList;
    private float totalBytes;

    /**
     *
     * @param context context of the calling
     * @param networkUsageDetailsList List that contains the object of NetworkUsageDetails to bind
     *                                the data to the view.
     */
    public NetworkDataAdapter(Context context, List<NetworkUsageDetails> networkUsageDetailsList, long totalBytes){
        this.context = context;
        this.networkUsageDetailsList = networkUsageDetailsList;
        this.totalBytes = (float)totalBytes;
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
            loadBitmap(applicationInfo.packageName, holder.applicationIcon);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        long rxBytes = networkUsageDetailsList.get(position).getTotalRxBytes();
        long txBytes = networkUsageDetailsList.get(position).getTotalTxBytes();
        long total = rxBytes + txBytes;

        holder.receivedBytes.setText("Down: "+memorySizeFormat(rxBytes));
        holder.transmittedBytes.setText("Up: "+memorySizeFormat(txBytes));
        holder.totalBytes.setText("Total:  "+ memorySizeFormat(total));
        holder.progressBar.setProgress((int)(total/totalBytes*100));
    }

    /**
     * takes the number of bytes as input and convert it to the readable memory format
     * for eg: if the input to the method is 1024 then it returns 1KB
     *
     * @param membytes number of bytes
     * @return memory size in the readable format
     */
    private String memorySizeFormat(long membytes){
        float bytes = (float)membytes;
        String returnValue;
        if(bytes > (1024*1024*1024)){
            returnValue = String.format("%.2f", bytes/(1024*1024*1024))+" GB";
        }else if(bytes > (1024*1024)){
            returnValue = String.format("%.2f", bytes/(1024*1024))+" MB";
        }else if(bytes> 1024){
            returnValue = String.format("%.2f", bytes/(1024))+" KB";
        }else{
            returnValue = String.format("%.2f", bytes)+" bytes";
        }
        return returnValue;
    }

    /**
     * loads the application icon of the package to the image view.
     *
     * @param packageName name of the package whose icon is to be loaded
     * @param imageView  reference to the ImageView where the application icon is to be loaded
     */
    private void loadBitmap(String packageName, ImageView imageView) {
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask();
        final Bitmap bitmap = bitmapWorkerTask.getBitmapFromMemCache(packageName);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.mipmap.ic_launcher);
            BitmapWorkerTask task = new BitmapWorkerTask();
            task.execute(packageName, imageView, context);
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
    public class NetworkViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.main_app_icon)
        ImageView applicationIcon;

        @BindView(R.id.main_app_name)
        TextView applicationName;

        @BindView(R.id.txt_network_data_received)
        TextView receivedBytes;

        @BindView(R.id.txt_network_data_transmitted)
        TextView transmittedBytes;

        @BindView(R.id.txt_total_network_data)
        TextView totalBytes;

        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        public NetworkViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
