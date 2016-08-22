package com.lftechnology.activitylogger.asyncTasks;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

/**
 * BitmapWorkerTask is an AsyncTask subclass which fetches the icon of the application of provided
 * package name and place the icon to the given ImageView asynchronously.
 * <p/>
 * Created by Sugam on 7/26/2016.
 */
public class BitmapWorkerTask extends AsyncTask<Object, Void, Map<String, Object>> {
    private LruCache<String, Bitmap> memoryCache;

    public BitmapWorkerTask() {
        this.memoryCache = CacheImage.getInstance().getLruCache();
    }

    @Override
    protected Map<String, Object> doInBackground(Object... objects) {
        String packageName = (String) objects[0];
        ImageView imageView = (ImageView) objects[1];
        Context context = (Context) objects[2];
        PackageManager packageManager = context.getPackageManager();

        Bitmap bitmap = null;
        try {
            bitmap = ((BitmapDrawable) packageManager.getApplicationIcon(
                    packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            )).getBitmap();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        addBitmapToMemoryCache(packageName, bitmap);
        Map<String, Object> placeImage = new HashMap<>();
        placeImage.put("imageView", imageView);
        placeImage.put("bitmap", bitmap);
        return placeImage;
    }

    @Override
    protected void onPostExecute(Map<String, Object> placeImage) {
        ImageView imageView = (ImageView) placeImage.get("imageView");
        Bitmap bitmap = (Bitmap) placeImage.get("bitmap");
        imageView.setImageBitmap(bitmap);
    }

    /**
     * adds the bitmap to the cache memory
     *
     * @param key    the package name that acts as a key to the bitmap
     * @param bitmap the bitmap object which is to be cached.
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) != null) {
            memoryCache.put(key, bitmap);
        }
    }

    /**
     * get the corresponding bitmap of the key from the cache memory
     *
     * @param key the package name that act as a key to the cache memory
     * @return the bitmap image of the corresponding key
     */
    public Bitmap getBitmapFromMemCache(String key) {
        return memoryCache.get(key);
    }

}
