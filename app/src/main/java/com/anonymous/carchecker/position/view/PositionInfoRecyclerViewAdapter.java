package com.anonymous.carchecker.position.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anonymous.carchecker.R;
import com.anonymous.carchecker.position.model.InfoVehicle;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link InfoVehicle} and makes a call to the
 * specified {@link PositionInfoFragment.OnPositionInfoFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PositionInfoRecyclerViewAdapter extends RecyclerView.Adapter<PositionInfoRecyclerViewAdapter.ViewHolder> {

    private final List<InfoVehicle> mValues;
    private final PositionInfoFragment.OnPositionInfoFragmentInteractionListener mListener;
    private ViewHolder.IViewHolderClick mIViewHolderClick;
    private Context mContext;

    public PositionInfoRecyclerViewAdapter(Context context, List<InfoVehicle> items, PositionInfoFragment.OnPositionInfoFragmentInteractionListener listener, ViewHolder.IViewHolderClick iViewHolderClick) {
        mValues = items;
        mListener = listener;
        mIViewHolderClick = iViewHolderClick;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_position_info_item, parent, false);

        return new ViewHolder(view, mIViewHolderClick);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        InfoVehicle infoVehicle = mValues.get(position);
        holder.mItem = infoVehicle;
        holder.mTvStatusFuel.setText(infoVehicle.mCurrentFuel + "/" + infoVehicle.mMaxFuel);
        holder.mTvNumberInvalidSpeed.setText(Integer.toString(infoVehicle.mNumberInvalidSpeed));
        holder.mTvRuleSpeed.setText(Integer.toString(infoVehicle.mRuleSpeed));
        holder.mImvCurrentStatus.setImageDrawable(mContext.getResources().getDrawable(infoVehicle.getDrawableByStatus()));
        holder.mTvDriveTimeInDay.setText(infoVehicle.mDriveTimeInDay);
        holder.mTvDistanceTotalInDay.setText(infoVehicle.mDistanceTotalInDay);
        holder.mTvCurrentPosition.setText(infoVehicle.mCurrentPosition);
        holder.mTvDetailCurrentStatus.setText(infoVehicle.mDetailCurrentStatus);
        holder.mTvNumberPlates.setText(infoVehicle.mNumberPlates);
        holder.mTvCurrentSpeed.setText(Integer.toString(infoVehicle.mCurrentSpeed));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public ImageView mImvCurrentStatus;
        public TextView mTvNumberPlates;
        public TextView mTvDetailCurrentStatus;
        public TextView mTvCurrentPosition;
        public TextView mTvCurrentSpeed;
        public TextView mTvDistanceTotalInDay;
        public TextView mTvDriveTimeInDay;
        public TextView mTvRuleSpeed;
        public TextView mTvNumberInvalidSpeed;
        public TextView mTvStatusFuel;


        public final CardView mCardView;
        public InfoVehicle mItem;
        private IViewHolderClick mIViewHolderClick;

        public ViewHolder(View view, IViewHolderClick iViewHolderClick) {
            super(view);
            mIViewHolderClick = iViewHolderClick;
            mView = view;
            mCardView = (CardView) view.findViewById(R.id.card_view_fragment_position_info_item);
            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mIViewHolderClick.onCardClick(view, getAdapterPosition(), ViewHolder.this);
                }
            });

            mImvCurrentStatus = (ImageView) view.findViewById(R.id.icon_status_info_fragment_position_info_item);
            mTvNumberPlates = (TextView) view.findViewById(R.id.number_plates_fragment_position_info_item);
            mTvDetailCurrentStatus = (TextView) view.findViewById(R.id.current_status_fragment_position_info_item);
            mTvCurrentPosition = (TextView) view.findViewById(R.id.current_position_fragment_position_info_item);
            mTvCurrentSpeed = (TextView) view.findViewById(R.id.current_speed_fragment_position_info_item);
            mTvDistanceTotalInDay = (TextView) view.findViewById(R.id.distance_total_fragment_position_info_item);
            mTvDriveTimeInDay = (TextView) view.findViewById(R.id.tv_drive_time_in_day_fragment_position_info_item);
            mTvRuleSpeed = (TextView) view.findViewById(R.id.speed_in_rule_fragment_position_info_item);
            mTvNumberInvalidSpeed = (TextView) view.findViewById(R.id.number_invalid_fragment_position_info_item);
            mTvStatusFuel = (TextView) view.findViewById(R.id.tv_status_fuel_fragment_position_info_item);
        }

        public interface IViewHolderClick {
            void onCardClick(View view, int position, ViewHolder viewHolder);
        }
    }
}
