package com.anonymous.carchecker.position.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anonymous.carchecker.common.view.BaseFragment;
import com.anonymous.carchecker.R;
import com.anonymous.carchecker.position.data.DummyContent;
import com.anonymous.carchecker.position.data.DummyContent.DummyItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnPositionInfoFragmentInteractionListener}
 * interface.
 */
public class PositionInfoFragment extends BaseFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnPositionInfoFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PositionInfoFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PositionInfoFragment newInstance(int columnCount) {
        PositionInfoFragment fragment = new PositionInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_position_info_list, container, false);

        // Set the adapter
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_fragment_pos_info);
        if (mColumnCount <= 1) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        mRecyclerView.setAdapter(new PositionInfoRecyclerViewAdapter(DummyContent.ITEMS, mListener, new PositionInfoRecyclerViewAdapter.ViewHolder.IViewHolderClick() {
            @Override
            public void onCardClick(View view, int position, PositionInfoRecyclerViewAdapter.ViewHolder viewHolder) {
                // Goto Map info activity
                Intent intent = new Intent(getActivity(), MapInfoActivity.class);
                getActivity().startActivity(intent);
            }
        }));

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
        void onListFragmentInteraction(DummyItem item);
    }
}
