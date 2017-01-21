package com.anonymous.carchecker.position.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.anonymous.carchecker.R;
import com.anonymous.carchecker.common.ApplicationUtil;
import com.anonymous.carchecker.common.util.PhoneUtil;
import com.anonymous.carchecker.common.view.BaseFragment;
import com.anonymous.carchecker.position.data.DummyContent;
import com.anonymous.carchecker.position.model.InfoVehicle;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnPositionInfoFragmentInteractionListener}
 * interface.
 */
public class PositionInfoFragment extends BaseFragment {

    private OnPositionInfoFragmentInteractionListener mListener;
    private PositionInfoRecyclerViewAdapter positionInfoRecyclerViewAdapter;

    private RecyclerView mRecyclerView;
    private TextView mTvNorthPhone;
    private TextView mTvSouthPhone;
    private TextView mTvNumberVehicle;

    private static PositionInfoFragment sPositionInfoFragment;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PositionInfoFragment() {
    }

    public static PositionInfoFragment newInstance() {
        if (sPositionInfoFragment != null) {
            return sPositionInfoFragment;
        }
        sPositionInfoFragment = new PositionInfoFragment();
        return sPositionInfoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_position_info_list, container, false);
        mTvNumberVehicle = (TextView) view.findViewById(R.id.number_vehicle_fragment_position_info_list);
        mTvNorthPhone = (TextView) view.findViewById(R.id.north_phone_fragment_position_info_list);
        mTvNorthPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneUtil.call(getActivity(), getString(R.string.vn_north_phone));
            }
        });
        mTvSouthPhone = (TextView) view.findViewById(R.id.south_phone_fragment_position_info_list);
        mTvSouthPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneUtil.call(getActivity(), getString(R.string.vn_south_phone));
            }
        });

        // Set the adapter
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_fragment_pos_info);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        positionInfoRecyclerViewAdapter = new PositionInfoRecyclerViewAdapter(getActivity(), DummyContent.ITEMS, mListener, new PositionInfoRecyclerViewAdapter.ViewHolder.IViewHolderClick() {
            @Override
            public void onCardClick(View view, int position, PositionInfoRecyclerViewAdapter.ViewHolder viewHolder) {
                Context context = getContext();
                if(!ApplicationUtil.isNetworkAvailable(context)){
                    Toast.makeText(context, R.string.network_error, Toast.LENGTH_LONG).show();
                } else {
                    // Goto Map info activity
                    Intent intent = new Intent(getActivity(), LocationInfoMapActivity.class);
                    getActivity().startActivity(intent);
                }
            }
        });

        //specify an adapter
        setAdapter(positionInfoRecyclerViewAdapter);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPositionInfoFragmentInteractionListener) {
            mListener = (OnPositionInfoFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPositionInfoFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPositionInfoFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(InfoVehicle item);
    }

    private void setAdapter(PositionInfoRecyclerViewAdapter positionInfoRecyclerViewAdapter) {
        mRecyclerView.setAdapter(positionInfoRecyclerViewAdapter);
        mTvNumberVehicle.setText(getString(R.string.number_car) + " " + Integer.toString(positionInfoRecyclerViewAdapter.getItemCount()));
    }

    public void search(String charText) {
        positionInfoRecyclerViewAdapter.filter(charText);
        mRecyclerView.invalidate();
        mTvNumberVehicle.setText(getString(R.string.number_car) + " " + Integer.toString(positionInfoRecyclerViewAdapter.getItemCount()));
    }
}
