package com.anonymous.carchecker.itinerary.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anonymous.carchecker.R;
import com.anonymous.carchecker.common.view.BaseFragment;
import com.anonymous.carchecker.position.view.MapInfoActivity;

public class ReviewItineraryFragment extends BaseFragment {

    private static ReviewItineraryFragment sReviewItineraryFragment;

    private Button btnReview;

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
        btnReview = (Button) view.findViewById(R.id.btn_review_fragment_itinerary);
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapInfoActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
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
