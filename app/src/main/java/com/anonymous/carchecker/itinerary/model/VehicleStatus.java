package com.anonymous.carchecker.itinerary.model;

import com.anonymous.carchecker.common.model.DataModel;

/**
 * Created by Huy Hieu on 1/19/2017.
 */

public class VehicleStatus extends DataModel {
    public int mId;
    public String mTime;
    public String mStatus;

    public VehicleStatus(int mId, String mTime, String mStatus) {
        this.mId = mId;
        this.mTime = mTime;
        this.mStatus = mStatus;
    }
}
