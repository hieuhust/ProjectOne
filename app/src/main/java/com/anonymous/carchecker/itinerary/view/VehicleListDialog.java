package com.anonymous.carchecker.itinerary.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.anonymous.carchecker.R;
import com.anonymous.carchecker.position.data.DummyContent;
import com.anonymous.carchecker.position.model.InfoVehicle;

import java.util.List;

/**
 * Created by Huy Hieu on 1/18/2017.
 */

public class VehicleListDialog extends DialogFragment {


    public interface onCheckItemListener {
        void setOnCheckItemListener(int position);
    }

    public interface DataReturnListener {
        void onDataReturn(String numberPlateName);
    }

    public static final String TAG = "VehicleListDialog";

    private RecyclerView mListDataRecycleView;
    private EditText mSearch;
    private NumberPlateRecyclerViewAdapter numberPlateRecyclerViewAdapter;
    private DataReturnListener mDataReturnListener;

    private List<InfoVehicle> dataList;

    public VehicleListDialog() {

    }

    public static VehicleListDialog newInstance() {
        return new VehicleListDialog();
    }

    public void setDataReturnListener(DataReturnListener dataReturnListener) {
        mDataReturnListener = dataReturnListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.number_plate_list, null);
        mSearch = (EditText) v.findViewById(R.id.search_number_plate_list);
        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mListDataRecycleView = (RecyclerView) v.findViewById(R.id.list_data_number_plate_list);

        //get data
        dataList = DummyContent.ITEMS;

        // use a linear layout manager
        mListDataRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        numberPlateRecyclerViewAdapter = new NumberPlateRecyclerViewAdapter(getActivity(), dataList, new onCheckItemListener() {
            @Override
            public void setOnCheckItemListener(int position) {
                mDataReturnListener.onDataReturn(dataList.get(position).mNumberPlates);
                dismiss();
            }
        });

        //specify an adapter
        mListDataRecycleView.setAdapter(numberPlateRecyclerViewAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.choose_vehicle)).setView(v);
        return builder.create();
    }

    public void search(String charText) {
        numberPlateRecyclerViewAdapter.filter(charText);
        mListDataRecycleView.invalidate();
    }
}
