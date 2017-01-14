package com.anonymous.carchecker.common.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Huy Hieu on 1/7/2017.
 */

public class BaseFragment extends Fragment {

    public String TAG = this.getClass().getSimpleName();

    public void gotoFragment(int fragment_container, BaseFragment newFragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the mUserName can navigate back
        transaction.replace(fragment_container, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

}
