package com.anonymous.carchecker.itinerary.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anonymous.carchecker.R;
import com.anonymous.carchecker.common.util.MyCalendarDialog;
import com.anonymous.carchecker.common.util.MyTimeDialog;
import com.anonymous.carchecker.common.view.BaseFragment;
import com.anonymous.carchecker.itinerary.data.DummyContentLogStatus;

public class ReviewItineraryFragment extends BaseFragment {


    private static ReviewItineraryFragment sReviewItineraryFragment;

    private Button btnReview;
    private Button btnLoadData;
    private TextView tvFromHour;
    private TextView tvToHour;
    private TextView tvDate;
    private TextView tvNumberPlate;
    private RecyclerView recyclerView;
    private LogDataRecyclerViewAdapter mLogDataRecyclerViewAdapter;

    private MyCalendarDialog myCalendarDialog;
    private MyTimeDialog myTimeDialog;

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
        btnReview.setEnabled(false);
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InMapVehicleSimulationActivity.class);
                getActivity().startActivity(intent);
            }
        });

        //Load data
        btnLoadData = (Button) view.findViewById(R.id.btn_load_data_fragment_itinerary);
        btnLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getString(R.string.choose_vehicle).equals(tvNumberPlate.getText().toString())) {
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.prompt_choose_vehicle), Toast.LENGTH_SHORT).show();
                } else {
                    mLogDataRecyclerViewAdapter.addData(DummyContentLogStatus.ITEMS);
                    if (DummyContentLogStatus.ITEMS.size() != 0) {
                        btnReview.setEnabled(true);
                    }
                }
            }
        });

        //Picker hour
        tvFromHour = (TextView) view.findViewById(R.id.from_hour_fragment_itinerary);
        tvFromHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTimeDialog = MyTimeDialog.newInstance(tvFromHour.getText().toString());
                myTimeDialog.setDataReturnListener(new MyTimeDialog.DataReturnListener() {
                    @Override
                    public void onDataReturn(int hour, int minute) {
                        setTimeForTextView(tvFromHour, hour, minute);
                    }
                });
                myTimeDialog.show(getActivity().getSupportFragmentManager(), MyTimeDialog.TIME_PICKER);
            }
        });

        tvToHour = (TextView) view.findViewById(R.id.to_hour_fragment_itinerary);
        tvToHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTimeDialog = MyTimeDialog.newInstance(tvToHour.getText().toString());
                myTimeDialog.setDataReturnListener(new MyTimeDialog.DataReturnListener() {
                    @Override
                    public void onDataReturn(int hour, int minute) {
                        setTimeForTextView(tvToHour, hour, minute);
                    }
                });
                myTimeDialog.show(getActivity().getSupportFragmentManager(), MyTimeDialog.TIME_PICKER);
            }
        });

        //picker date
        tvDate = (TextView) view.findViewById(R.id.date_fragment_itinerary);
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendarDialog = MyCalendarDialog.newInstance(tvDate.getText().toString());
                myCalendarDialog.setDataReturnListener(new MyCalendarDialog.DataReturnListener() {
                    @Override
                    public void onDataReturn(int year, int month, int dayOfMonth) {
                        setDateForTextView(tvDate, year, month, dayOfMonth);
                    }
                });
                myCalendarDialog.show(getActivity().getSupportFragmentManager(), MyCalendarDialog.DATE_PICKER);
            }
        });

        //picker number plate
        tvNumberPlate = (TextView) view.findViewById(R.id.number_plate_fragment_itinerary);
        tvNumberPlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VehicleListDialog vehicleListDialog = VehicleListDialog.newInstance();
                vehicleListDialog.setDataReturnListener(new VehicleListDialog.DataReturnListener() {
                    @Override
                    public void onDataReturn(String numberPlateName) {
                        tvNumberPlate.setText(numberPlateName);
                    }
                });
                vehicleListDialog.show(getActivity().getSupportFragmentManager(), VehicleListDialog.TAG);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_fragment_itinerary);
        // use a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLogDataRecyclerViewAdapter = new LogDataRecyclerViewAdapter(getActivity());

        //specify an adapter
        recyclerView.setAdapter(mLogDataRecyclerViewAdapter);

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
