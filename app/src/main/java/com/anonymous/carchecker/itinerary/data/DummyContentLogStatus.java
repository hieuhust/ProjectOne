package com.anonymous.carchecker.itinerary.data;

import com.anonymous.carchecker.itinerary.model.VehicleStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for mUserName interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContentLogStatus {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<VehicleStatus> ITEMS = new ArrayList<VehicleStatus>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createVehicleStatus(i));
        }
    }

    private static void addItem(VehicleStatus item) {
        ITEMS.add(item);
    }

    private static VehicleStatus createVehicleStatus(int position) {
        int id = position;
        String time = "04:39:" + position;
        String status = "Dừng 1" + position;
        return new VehicleStatus(id, time, status);
    }
}
