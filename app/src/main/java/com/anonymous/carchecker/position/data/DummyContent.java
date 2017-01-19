package com.anonymous.carchecker.position.data;

import com.anonymous.carchecker.common.model.StatusVehicleType;
import com.anonymous.carchecker.position.model.InfoVehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for mUserName interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<InfoVehicle> ITEMS = new ArrayList<InfoVehicle>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, InfoVehicle> ITEM_MAP = new HashMap<String, InfoVehicle>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createInfoVehicle(i));
        }
    }

    private static void addItem(InfoVehicle item) {
        ITEMS.add(item);
        ITEM_MAP.put(Integer.toString(item.id), item);
    }

    private static InfoVehicle createInfoVehicle(int position) {
        int id = position;
        int index = position % 3;
        int mCurrentStatus = StatusVehicleType.values()[index].ordinal();
        String mNumberPlates = position + "8H3333";
        String mDetailCurrentStatus = "Dừng 12'";
        String mCurrentPosition = "Lương Thế Vinh,Nguyễn Trãi, Thanh Xuân, Hà Nội";
        int mCurrentSpeed = 50;
        String mDistanceTotalInDay = "100km";
        String mDriveTimeInDay = "5h";
        int mRuleSpeed = 80;
        int mNumberInvalidSpeed = 3;
        int mCurrentFuel = 55;
        int mMaxFuel = 120;
        return new InfoVehicle(position, mCurrentStatus, mNumberPlates, mDetailCurrentStatus, mCurrentPosition, mCurrentSpeed, mDistanceTotalInDay, mDriveTimeInDay, mRuleSpeed, mNumberInvalidSpeed, mCurrentFuel, mMaxFuel);
    }
}
