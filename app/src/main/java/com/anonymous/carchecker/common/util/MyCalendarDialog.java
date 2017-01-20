package com.anonymous.carchecker.common.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Huy Hieu on 1/18/2017.
 */

public class MyCalendarDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public interface DataReturnListener {
        void onDataReturn(int year, int month, int dayOfMonth);
    }

    public final static String DATE_PICKER = "datePicker";

    public static final String FORMAT = "yyyy/MM/dd";

    private final static String DATE_STRING_ARGUMENT = "date";

    private DataReturnListener mDataReturnListener;

    private int year, month, dayOfMonth;
    private boolean mIsClick = false;

    public MyCalendarDialog() {
        mIsClick = false;
    }

    public static MyCalendarDialog newInstance(String textCalendar) {
        MyCalendarDialog myCalendarDialog = new MyCalendarDialog();
        Bundle bundle = new Bundle();
        bundle.putString(DATE_STRING_ARGUMENT, textCalendar);
        myCalendarDialog.setArguments(bundle);
        return myCalendarDialog;
    }

    public void setDataReturnListener(DataReturnListener dataReturnListener) {
        mDataReturnListener = dataReturnListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String date = getArguments().getString(DATE_STRING_ARGUMENT);

        //parse date string to day,month, year with format "yyyy/MM/dd"
        String[] data = date.split("/");
        int year = Integer.parseInt(data[0]);
        int month = Integer.parseInt(data[1]);
        int day = Integer.parseInt(data[2]);

        // Create a new instance of DatePickerDialog and return it
        // issue due to we have to -1 in month var
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month - 1, day);
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mIsClick = true;
            }
        });
        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (mIsClick) {
            mDataReturnListener.onDataReturn(year, month, dayOfMonth);
        }
    }
}
