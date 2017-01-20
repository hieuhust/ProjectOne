package com.anonymous.carchecker.common.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.anonymous.carchecker.common.util.MyCalendarDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

    public void setDateForTextView(TextView textView, int year, int month, int dayOfMonth) {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat sdformat = new SimpleDateFormat(MyCalendarDialog.FORMAT, Locale.US);
        c.set(year, month, dayOfMonth);
        textView.setText(sdformat.format(c.getTime()));
    }

    public void setTimeForTextView(TextView textView, int hour, int minute) {
        String formatedTime = String.format("%1$02d:%2$02d", hour, minute);
        textView.setText(formatedTime);
    }

}
