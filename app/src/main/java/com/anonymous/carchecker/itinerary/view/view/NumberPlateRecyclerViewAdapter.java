package com.anonymous.carchecker.itinerary.view.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.anonymous.carchecker.R;
import com.anonymous.carchecker.position.model.InfoVehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class NumberPlateRecyclerViewAdapter extends RecyclerView.Adapter<NumberPlateRecyclerViewAdapter.ViewHolder> {

    private List<InfoVehicle> mInfoVehicles;
    private List<InfoVehicle> mCachedInfoVehicles;
    private final VehicleListDialog.onCheckItemListener mOnCheckItemListener;
    private Context mContext;

    public NumberPlateRecyclerViewAdapter(Context context, List<InfoVehicle> items, VehicleListDialog.onCheckItemListener onCheckItemListener) {
        mOnCheckItemListener = onCheckItemListener;
        mCachedInfoVehicles = new ArrayList<>();
        setData(items);
        mContext = context;
    }

    public void setData(List<InfoVehicle> infoVehicles) {
        mInfoVehicles = infoVehicles;
        mCachedInfoVehicles.clear();
        mCachedInfoVehicles.addAll(mInfoVehicles);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.number_plate_item, parent, false);

        return new ViewHolder(view, mOnCheckItemListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        InfoVehicle infoVehicle = mInfoVehicles.get(position);
        holder.mNumberPlateName.setText(infoVehicle.mNumberPlates);
    }

    @Override
    public int getItemCount() {
        return mInfoVehicles.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        mInfoVehicles.clear();
        if (charText.length() == 0) {
            mInfoVehicles.addAll(mCachedInfoVehicles);

        } else {
            for (InfoVehicle infoVehicle : mCachedInfoVehicles) {
                if (charText.length() != 0 && infoVehicle.mNumberPlates.toLowerCase(Locale.getDefault()).contains(charText)) {
                    mInfoVehicles.add(infoVehicle);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mNumberPlateName;
        public CheckBox mCheckbox;


        public ViewHolder(View view, final VehicleListDialog.onCheckItemListener onCheckItemListener) {
            super(view);
            mNumberPlateName = (TextView) view.findViewById(R.id.name_number_plate_item);
            mCheckbox = (CheckBox) view.findViewById(R.id.chb_number_plate_item);
            mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        onCheckItemListener.setOnCheckItemListener(getAdapterPosition());
                    }
                }
            });
        }

    }
}
