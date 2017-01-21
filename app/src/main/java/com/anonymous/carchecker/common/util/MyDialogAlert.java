package com.anonymous.carchecker.common.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import com.anonymous.carchecker.R;
import android.view.View;

/**
 * Created by Huy Hieu on 1/14/2017.
 */

public class MyDialogAlert {

    public static interface DialogListener {
        void onPositiveButtonClick();

        void onNegativeButtonClick();
    }

    private Context mContext;

    public MyDialogAlert(Context context) {
        mContext = context;
    }

    public void show(int title, int message, final DialogListener dialogListener, final boolean isShowPositiveButton, final boolean isShowNegativeButton) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        if(isShowPositiveButton)
        builder.setPositiveButton(mContext.getResources().getText(R.string.btn_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialogListener.onPositiveButtonClick();
            }
        });
        if(isShowNegativeButton)
        builder.setNegativeButton(mContext.getResources().getText(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogListener.onNegativeButtonClick();
                    }
                });

        // Create the AlertDialog object and return it
        builder.create().show();
    }
}
