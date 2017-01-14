package com.anonymous.carchecker.common.logger;

import android.util.Log;

/**
 * Created by Huy Hieu on 12/19/2016.
 */

public class Logger {

    private final static boolean DEBUG = true;

    public static void d(String TAG, String msg) {
        if (DEBUG)
            Log.d(TAG, msg);
    }

    public static void i(String TAG, String msg) {
        if (DEBUG)
            Log.i(TAG, msg);
    }

    public static void e(String TAG, String msg) {
        if (DEBUG)
            Log.e(TAG, msg);
    }
}
