package com.anonymous.carchecker.common.view;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Huy Hieu on 1/7/2017.
 */

public class BaseActivity extends AppCompatActivity {

    public String TAG = this.getClass().getSimpleName();

    public void gotoFragment(int fragment_container, BaseFragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the mUserName can navigate back
        transaction.replace(fragment_container, newFragment);

        // Commit the transaction
        transaction.commit();
    }

    public void gotoActivity(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

}
