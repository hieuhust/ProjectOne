package com.anonymous.carchecker;

/**
 * Created by huyhieu.ph on 01/14/2016.
 */

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anonymous.carchecker.common.view.BaseActivity;

public class SplashScreen extends BaseActivity {

    private SplashScreen mSplashScreen;

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    private ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        mSplashScreen = this;

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }

        setContentView(R.layout.spash_screen_layout);

        mProgressBar = (ProgressBar)findViewById(R.id.progress_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //// TODO: 1/14/2017 check network
                gotoActivity(LoginActivity.class);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
