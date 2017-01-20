package com.anonymous.carchecker.common.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TimePicker;

/**
 * Created by Huy Hieu on 1/18/2017.
 */

public class MyTimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public interface DataReturnListener {
        void onDataReturn(int hour, int minute);
    }

    public final static String TIME_PICKER = "timePicker";

    public static final String FORMAT = "hh:mm";

    private final static String DATE_STRING_ARGUMENT = "date";

    private DataReturnListener mDataReturnListener;

    private boolean mIsClick = false;

    public MyTimeDialog() {
        mIsClick = false;
    }

    public static MyTimeDialog newInstance(String textCalendar) {
        MyTimeDialog myCalendarDialog = new MyTimeDialog();
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

        //parse time string to hour,minute with format "hh:mm"
        String[] data = date.split(":");
        int hour = Integer.parseInt(data[0]);
        int minute = Integer.parseInt(data[1]);

        // Create a new instance of DatePickerDialog and return it
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, hour, minute, true);
        timePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mIsClick = true;
            }
        });
        return timePickerDialog;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (mIsClick) {
            mDataReturnListener.onDataReturn(hourOfDay,minute);
        }
    }

}
