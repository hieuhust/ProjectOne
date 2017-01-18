package com.anonymous.carchecker.itinerary.view;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.anonymous.carchecker.R;
import com.anonymous.carchecker.common.util.MyCalendarDialog;
import com.anonymous.carchecker.common.view.BaseFragment;
import com.anonymous.carchecker.position.view.MapInfoActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.anonymous.carchecker.R.string.day;

public class ReviewItineraryFragment extends BaseFragment {

    private final static String DATE_PICKER = "datePicker";

    private static ReviewItineraryFragment sReviewItineraryFragment;

    private Button btnReview;
    private Button btnLoadData;
    private TextView tvFromHour;
    private TextView tvToHour;
    private TextView tvDate;
    private TextView tvNumberPlate;

    private MyCalendarDialog myCalendarDialog;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReviewItineraryFragment() {
    }

    public static ReviewItineraryFragment newInstance() {
        if (sReviewItineraryFragment == null) {
            sReviewItineraryFragment = new ReviewItineraryFragment();
        }
        return sReviewItineraryFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_itinerary, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btnReview = (Button) view.findViewById(R.id.btn_review_fragment_itinerary);
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapInfoActivity.class);
                getActivity().startActivity(intent);
            }
        });

        //Load data
        btnLoadData = (Button) view.findViewById(R.id.btn_load_data_fragment_itinerary);
        btnLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //Picker hour
        tvFromHour = (TextView) view.findViewById(R.id.from_hour_fragment_itinerary);
        tvFromHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvToHour = (TextView) view.findViewById(R.id.to_hour_fragment_itinerary);
        tvToHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //picker date
        tvDate = (TextView) view.findViewById(R.id.date_fragment_itinerary);
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendarDialog = new MyCalendarDialog();
                myCalendarDialog.setDataReturnListener(new MyCalendarDialog.DataReturnListener() {
                    @Override
                    public void onDataReturn(DatePicker view, int year, int month, int dayOfMonth) {
                        String myFormat = "yyyy/MM/dd";
                        SimpleDateFormat sdformat = new SimpleDateFormat(myFormat, Locale.US);
                        Calendar c = Calendar.getInstance();
                        c.set(year, month, dayOfMonth);
                        tvDate.setText(sdformat.format(c.getTime()));
                    }
                });
                myCalendarDialog.show(getActivity().getSupportFragmentManager(), DATE_PICKER);
            }
        });

        //picker number plate
        tvNumberPlate = (TextView) view.findViewById(R.id.number_plate_fragment_itinerary);
        tvNumberPlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
