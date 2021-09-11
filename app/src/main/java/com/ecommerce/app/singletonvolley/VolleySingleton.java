package com.ecommerce.app.singletonvolley;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.ecommerce.app.utils.LruBitmapCache;

public class VolleySingleton extends Application {

    private static VolleySingleton mInstance;
    public static final String TAG = VolleySingleton.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static Context mCtx;
    private ImageLoader mImageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    private VolleySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    public static synchronized VolleySingleton getInstance2() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
