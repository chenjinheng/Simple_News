/*
 * Created by Storm Zhang, Feb 11, 2014.
 */

package com.example.simple_news.volley;
import android.app.ActivityManager;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;



public class VolleyManager {

	//请求队列
	private static RequestQueue mRequestQueue;

	//图片加载工具类
	private static ImageLoader mImageLoader;

	private VolleyManager() {
		// no instances
	}


	public static void init(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);

		//告诉你你的机器还有多少内存，在计算缓存大小的时候会比较有
		int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE))
				.getMemoryClass();
		// Use 1/8th of the available memory for this memory cache.
		int cacheSize = 1024 * 1024 * memClass / 8;//图片缓存的空间
		mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(cacheSize));
	}


	public static RequestQueue getRequestQueue() {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("RequestQueue not initialized");
		}
	}


	public static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        mRequestQueue.add(request);
    }

	public static void cancelAll(Object tag) {
        mRequestQueue.cancelAll(tag);
    }


	public static ImageLoader getImageLoader() {
		if (mImageLoader != null) {
			return mImageLoader;
		} else {
			throw new IllegalStateException("ImageLoader not initialized");
		}
	}
}
