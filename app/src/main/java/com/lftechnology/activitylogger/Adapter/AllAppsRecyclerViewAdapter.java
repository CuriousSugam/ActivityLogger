package com.lftechnology.activitylogger.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lftechnology.activitylogger.AsynkTasks.BitmapWorkerTask;
import com.lftechnology.activitylogger.R;
import com.lftechnology.activitylogger.Model.AppDetails;
import com.lftechnology.activitylogger.Viewholders.AllAppsViewHolder;

import java.util.List;

/**
 * It is an adapter class that binds the data of the installed apps to the recycler view
 * <p/>
 * Created by Sugam on 7/15/2016.
 */
public class AllAppsRecyclerViewAdapter extends RecyclerView.Adapter<AllAppsViewHolder> {

    private Context context;
    private List<AppDetails> appDetailsList;

    /**
     * @param context        context of the calling
     * @param appDetailsList List of AppDetails object
     */
    public AllAppsRecyclerViewAdapter(Context context, List<AppDetails> appDetailsList) {
        this.context = context;
        this.appDetailsList = appDetailsList;
    }

    @Override
    public AllAppsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_all_applications_grid, parent, false);
        return new AllAppsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(AllAppsViewHolder holder, int position) {
        String applicationName = appDetailsList.get(position).getApplicationName();
        String packageName = appDetailsList.get(position).getPackageName();
        holder.getApplicationNameTextView().setText(applicationName);
        loadBitmap(packageName, holder.getApplicationIconImageView());
    }

    /**
     * loads the application icon of the package to the image view.
     *
     * @param packageName name of the package whose icon is to be loaded
     * @param imageView  reference to the ImageView where the application icon is to be loaded
     */
    public void loadBitmap(String packageName, ImageView imageView) {
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
        return appDetailsList.size();
    }


}
