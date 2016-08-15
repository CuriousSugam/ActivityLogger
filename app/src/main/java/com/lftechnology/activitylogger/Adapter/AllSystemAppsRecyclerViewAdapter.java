package com.lftechnology.activitylogger.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lftechnology.activitylogger.asynkTasks.BitmapWorkerTask;
import com.lftechnology.activitylogger.R;
import com.lftechnology.activitylogger.model.AppDetails;

import java.util.List;

/**
 * It is an adapter class that binds the data of the system apps to the recycler view
 *
 * Created by Sugam on 7/18/2016.
 */
public class AllSystemAppsRecyclerViewAdapter extends RecyclerView.Adapter<AllSystemAppsRecyclerViewAdapter .AllSystemAppsViewholder >  {
    private Context context;
    private List<AppDetails> appDetailsList;

    /**
     *
     * @param context context of the calling
     * @param appDetailsList List of AppDetails object
     */
    public AllSystemAppsRecyclerViewAdapter(Context context, List<AppDetails> appDetailsList){
        this.context = context;
        this.appDetailsList = appDetailsList;
    }

    @Override
    public AllSystemAppsViewholder  onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_all_applications_grid, parent, false);
        return new AllSystemAppsViewholder (view);
    }



    @Override
    public void onBindViewHolder(AllSystemAppsViewholder  holder, int position) {
        String packageName = appDetailsList.get(position).getPackageName();
        String applicationLabel = appDetailsList.get(position).getApplicationName();
        if(applicationLabel.length() > 9){
            holder.applicationName.setText(applicationLabel.substring(0, 8)+"...");
        }else {
            holder.applicationName.setText(applicationLabel);
        }
        loadBitmap(packageName, holder.applicationIcon);
    }

    /**
     * loads the application icon of the package to the image view.
     *
     * @param packageName name of the package whose icon is to be loaded
     * @param mImageView reference to the ImageView where the application icon is to be loaded
     */
    public void loadBitmap(String packageName, ImageView mImageView) {
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask();
        final Bitmap bitmap = bitmapWorkerTask.getBitmapFromMemCache(packageName);
        if (bitmap != null) {
            mImageView.setImageBitmap(bitmap);
        } else {
            mImageView.setImageResource(R.mipmap.ic_launcher);
            BitmapWorkerTask task = new BitmapWorkerTask();
            task.execute(packageName, mImageView, context);
        }
    }

    @Override
    public int getItemCount() {
        return appDetailsList.size();
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
