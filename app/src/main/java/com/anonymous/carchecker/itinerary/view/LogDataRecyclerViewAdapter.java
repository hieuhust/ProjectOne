package com.anonymous.carchecker.itinerary.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anonymous.carchecker.R;
import com.anonymous.carchecker.itinerary.model.VehicleStatus;

import java.util.ArrayList;
import java.util.List;


public class LogDataRecyclerViewAdapter extends RecyclerView.Adapter<LogDataRecyclerViewAdapter.ViewHolder> {

    private List<VehicleStatus> mVehicleStatus;
    private Context mContext;

    public LogDataRecyclerViewAdapter(Context context) {
        mContext = context;
        mVehicleStatus = new ArrayList<>();
    }

    public void addData(List<VehicleStatus> items) {
        mVehicleStatus.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.status_table_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        VehicleStatus vehicleStatus = mVehicleStatus.get(position);
        holder.mStatus.setText(vehicleStatus.mStatus);
        holder.mId.setText(Integer.toString(vehicleStatus.mId));
        holder.mTime.setText(vehicleStatus.mTime);
    }

    @Override
    public int getItemCount() {
        return mVehicleStatus.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mId;
        private TextView mTime;
        private TextView mStatus;


        public ViewHolder(View view) {
            super(view);
            mId = (TextView) view.findViewById(R.id.id_status_table_item);
            mTime = (TextView) view.findViewById(R.id.time_status_table_item);
            mStatus = (TextView) view.findViewById(R.id.status_status_table_item);
        }

    }
}
