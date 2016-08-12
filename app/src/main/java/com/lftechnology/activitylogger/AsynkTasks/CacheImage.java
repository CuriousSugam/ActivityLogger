package com.lftechnology.activitylogger.AsynkTasks;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * CacheImage is a Singleton class.
 * CacheImage allocates the memory for caching the image.
 * <p/>
 * Created by Sugam on 7/26/2016.
 */
public class CacheImage {
    private LruCache<String, Bitmap> mMemoryCache;
    private static CacheImage ourInstance = new CacheImage();

    /**
     * @return returns the reference to the existing object of CacheImage
     */
    public static CacheImage getInstance() {
        return ourInstance;
    }

    /**
     * allocates the memory for caching the image on instantiation
     */
    private CacheImage() {
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    /**
     * @return the reference of the LruCache
     */
    public LruCache<String, Bitmap> getLruCache() {
        return mMemoryCache;
    }
}
