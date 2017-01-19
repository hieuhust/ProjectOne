package com.anonymous.carchecker.position.model;

import com.anonymous.carchecker.R;
import com.anonymous.carchecker.common.model.DataModel;
import com.anonymous.carchecker.common.model.StatusVehicleType;

/**
 * Created by Huy Hieu on 1/16/2017.
 */

public class InfoVehicle extends DataModel {

    public int id;
    public int mCurrentStatus;
    public String mNumberPlates;
    public String mDetailCurrentStatus;
    public String mCurrentPosition;
    public int mCurrentSpeed;
    public String mDistanceTotalInDay;
    public String mDriveTimeInDay;
    public int mRuleSpeed;
    public int mNumberInvalidSpeed;
    public int mCurrentFuel;
    public int mMaxFuel;

    public InfoVehicle(int id, int mCurrentStatus, String mNumberPlates, String mDetailCurrentStatus, String mCurrentPosition, int mCurrentSpeed, String mDistanceTotalInDay, String mDriveTimeInDay, int mRuleSpeed, int mNumberInvalidSpeed, int mCurrentFuel, int mMaxFuel) {
        this.id = id;
        this.mCurrentStatus = mCurrentStatus;
        this.mNumberPlates = mNumberPlates;
        this.mDetailCurrentStatus = mDetailCurrentStatus;
        this.mCurrentPosition = mCurrentPosition;
        this.mCurrentSpeed = mCurrentSpeed;
        this.mDistanceTotalInDay = mDistanceTotalInDay;
        this.mDriveTimeInDay = mDriveTimeInDay;
        this.mRuleSpeed = mRuleSpeed;
        this.mNumberInvalidSpeed = mNumberInvalidSpeed;
        this.mCurrentFuel = mCurrentFuel;
        this.mMaxFuel = mMaxFuel;
    }

    public int getDrawableByStatus() {
        StatusVehicleType status = StatusVehicleType.values()[mCurrentStatus];
        switch (status) {
            case PAUSE:
                return R.drawable.doxe_icon;
            case STOP:
                return R.drawable.dangdo_icon;
            case OVERSPEED:
                return R.drawable.overspeed_icon;
            case NOT_GPRS:
                return R.drawable.quahan_icon;
        }
        return -1;
    }

}
