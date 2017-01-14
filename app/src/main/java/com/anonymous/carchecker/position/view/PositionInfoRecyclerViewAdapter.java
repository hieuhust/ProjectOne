package com.anonymous.carchecker.position.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anonymous.carchecker.R;
import com.anonymous.carchecker.position.data.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link PositionInfoFragment.OnPositionInfoFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PositionInfoRecyclerViewAdapter extends RecyclerView.Adapter<PositionInfoRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final PositionInfoFragment.OnPositionInfoFragmentInteractionListener mListener;
    private ViewHolder.IViewHolderClick mIViewHolderClick;

    public PositionInfoRecyclerViewAdapter(List<DummyItem> items, PositionInfoFragment.OnPositionInfoFragmentInteractionListener listener,ViewHolder.IViewHolderClick iViewHolderClick) {
        mValues = items;
        mListener = listener;
        mIViewHolderClick = iViewHolderClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_position_info_item, parent, false);

        return new ViewHolder(view,mIViewHolderClick);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final LinearLayout mLinearLayout;
        public DummyItem mItem;
        private IViewHolderClick mIViewHolderClick;

        public ViewHolder(View view, IViewHolderClick iViewHolderClick) {
            super(view);
            mIViewHolderClick = iViewHolderClick;
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mLinearLayout = (LinearLayout)view.findViewById(R.id.fragment_postion_info_item_ll);
            mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mIViewHolderClick.onCardClick(view,getAdapterPosition(),ViewHolder.this);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        public interface IViewHolderClick {
            void onCardClick(View view, int position, ViewHolder viewHolder);
        }
    }
}
