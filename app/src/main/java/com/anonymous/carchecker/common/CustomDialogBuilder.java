package com.anonymous.carchecker.common;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.anonymous.carchecker.R;

public class CustomDialogBuilder {
    private Context mContext;
    private Dialog mDialog;
    private TextView tvTitle, tvMessage, tvLeft, tvMiddle, tvRight;

    public CustomDialogBuilder(Context context) {
        mContext = context;
        if (mContext == null) {
            throw new IllegalArgumentException("Context must be not null");
        }
        mDialog = new Dialog(mContext, R.style.CustomDialog);
        mDialog.setContentView(R.layout.dialog_custom_builder);

        tvTitle = (TextView) mDialog.findViewById(R.id.tvTitle);
        tvMessage = (TextView) mDialog.findViewById(R.id.tvMessage);
        tvLeft = (TextView) mDialog.findViewById(R.id.tvLeft);
        tvMiddle = (TextView) mDialog.findViewById(R.id.tvMiddle);
        tvRight = (TextView) mDialog.findViewById(R.id.tvRight);
    }

    public CustomDialogBuilder setTitle(int resId) {
        setTitle(mContext.getString(resId));
        return this;
    }

    public CustomDialogBuilder setTitle(String message) {
        tvTitle.setText(message);
        tvTitle.setVisibility(View.VISIBLE);
        return this;
    }

    public CustomDialogBuilder setMessage(int resId) {
        setMessage(mContext.getString(resId));
        return this;
    }

    public CustomDialogBuilder setMessage(String message) {
        tvMessage.setText(message);
        return this;
    }

    public CustomDialogBuilder addLeftButton(String text, final View.OnClickListener onClickListener) {
        tvLeft.setText(text);
        tvLeft.setVisibility(View.VISIBLE);
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(view);
                }
                mDialog.dismiss();
            }
        });
        return this;
    }

    public CustomDialogBuilder addLeftButton(int resId, final View.OnClickListener onClickListener) {
        addLeftButton(mContext.getString(resId), onClickListener);
        return this;
    }

    public CustomDialogBuilder addMiddleButton(String text, final View.OnClickListener onClickListener) {
        tvMiddle.setText(text);
        tvMiddle.setVisibility(View.VISIBLE);
        tvMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(view);
                }
                mDialog.dismiss();
            }
        });
        return this;
    }

    public CustomDialogBuilder addMiddleButton(int resId, final View.OnClickListener onClickListener) {
        addMiddleButton(mContext.getString(resId), onClickListener);
        return this;
    }

    public CustomDialogBuilder addRightButton(String text, final View.OnClickListener onClickListener) {
        tvRight.setText(text);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(view);
                }
                mDialog.dismiss();
            }
        });
        return this;
    }

    public CustomDialogBuilder addRightButton(int resId, final View.OnClickListener onClickListener) {
        addRightButton(mContext.getString(resId), onClickListener);
        return this;
    }

    public Dialog build() {
        return mDialog;
    }
}
