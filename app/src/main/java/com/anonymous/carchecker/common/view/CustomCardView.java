package com.anonymous.carchecker.common.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.anonymous.carchecker.R;

/**
 * Created by Huy Hieu on 1/16/2017.
 */

public class CustomCardView extends CardView {

    public CustomCardView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public CustomCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public CustomCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void drawableStateChanged() {
        if (isPressed()) {
            this.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorBlueSkyLight));
        } else {
            this.setCardBackgroundColor(getContext().getResources().getColor(R.color.cardview_light_background));
        }
    }
}
