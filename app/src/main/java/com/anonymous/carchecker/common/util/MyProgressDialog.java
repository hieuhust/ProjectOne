package com.anonymous.carchecker.common.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Huy Hieu on 1/14/2017.
 */

public class MyProgressDialog {

    private ProgressDialog mProgress;

    private Context mContext;


    public MyProgressDialog(Context context) {
        mContext = context;
    }

    public void show(String title, String msg) {
        mProgress = ProgressDialog.show(mContext, title,
                msg, true);
    }

    public void hide() {
        mProgress.dismiss();
    }

}
