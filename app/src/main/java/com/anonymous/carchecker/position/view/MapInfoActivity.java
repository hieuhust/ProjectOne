package com.anonymous.carchecker.position.view;

import android.app.ActionBar;
import android.graphics.Color;
import android.location.Location;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.anonymous.carchecker.R;
import com.anonymous.carchecker.common.logger.Logger;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapInfoActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final String TAG = "MapInfoActivity";
    private List<Marker> markers = new ArrayList<>();
    private GoogleMap googleMap;
    private final Handler mHandler = new Handler();
    private Animator animator = new Animator();
    private Button mBtWatchButton;
    private boolean mIsWatched;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_info);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mBtWatchButton = (Button) findViewById(R.id.mWatchButton);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar();
        mBtWatchButton.setAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_select));
        mBtWatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsWatched){
                    mBtWatchButton.setText(getApplicationContext().getString(R.string.watch));
                    mBtWatchButton.setBackgroundResource(R.drawable.xem_button);
                    mIsWatched = false;
                    animator.stop();
                } else {
                    mBtWatchButton.setText(getApplicationContext().getString(R.string.stop));
                    mBtWatchButton.setBackgroundResource(R.drawable.dung_button);
                    mIsWatched = true;
                    animator.startAnimation(true);
                }

            }
        });
        mapFragment.getMapAsync(this);
    }
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.itinerary);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }
        );
        setSupportActionBar(toolbar);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        addDefaultLocations();
        animator.initializePolyLine();
    }

    private void addDefaultLocations() {
        clearMarkers();
        addMarkerToMap(new LatLng(21.734106, 105.25956), false);
        addMarkerToMap(new LatLng(21.73056, 105.287498), true);
        addMarkerToMap(new LatLng(21.736765, 105.280807), true);
        addMarkerToMap(new LatLng(21.738024, 105.286842), true);
        addMarkerToMap(new LatLng(21.734106, 105.25956), true);
        addMarkerToMap(new LatLng(21.737768, 105.282921), true);
        addMarkerToMap(new LatLng(21.736843, 105.276176), true);
        addMarkerToMap(new LatLng(21.732967, 105.259758), true);
        addMarkerToMap(new LatLng(21.739027, 105.261452), true);
        addMarkerToMap(new LatLng(21.733704, 105.261749), true);
        addMarkerToMap(new LatLng(21.732967, 105.259758), true);
        addMarkerToMap(new LatLng(21.736698, 105.275635), true);
        addMarkerToMap(new LatLng(21.734106, 105.25956), true);
        addMarkerToMap(new LatLng(21.736975, 105.28154), true);
        addMarkerToMap(new LatLng(21.734106, 105.25956), true);
        addMarkerToMap(new LatLng(21.733763, 105.262306), false);
    }

    private Location convertLatLngToLocation(LatLng latLng) {
        Location loc = new Location("someLoc");
        loc.setLatitude(latLng.latitude);
        loc.setLongitude(latLng.longitude);
        return loc;
    }

    private float bearingBetweenLatLngs(LatLng begin, LatLng end) {
        Location beginL = convertLatLngToLocation(begin);
        Location endL = convertLatLngToLocation(end);
        return beginL.bearingTo(endL);
    }

    public void toggleStyle() {
        if (GoogleMap.MAP_TYPE_NORMAL == googleMap.getMapType()) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }


    public void addMarkerToMap(LatLng latLng, boolean isMarkerRemoved) {
        Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng)
                .title("Kien An")
                .snippet("Map example"));
        markers.add(marker);
        if (isMarkerRemoved) {
            marker.setVisible(false);
        }
    }

    public void clearMarkers() {
        googleMap.clear();
        markers.clear();
    }

    private void highLightMarker(int index) {
        highLightMarker(markers.get(index));
    }

    private void highLightMarker(Marker marker) {
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        marker.showInfoWindow();
    }

    private void resetMarkers() {
        for (Marker marker : this.markers) {
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }
    }

    public class Animator implements Runnable {

        private static final int ANIMATE_SPEEED = 1500;
        private static final int ANIMATE_SPEEED_TURN = 1000;
        private static final int BEARING_OFFSET = 20;

        private final Interpolator interpolator = new LinearInterpolator();

        int currentIndex = 0;

        float tilt = 90;
        float zoom = 15.5f;
        boolean upward = true;

        long start = SystemClock.uptimeMillis();

        LatLng endLatLng = null;
        LatLng beginLatLng = null;

        boolean showPolyline = false;

        private Marker trackingMarker;

        public void reset() {
            resetMarkers();
            start = SystemClock.uptimeMillis();
            currentIndex = 0;
            endLatLng = getEndLatLng();
            beginLatLng = getBeginLatLng();

        }

        public void stop() {
            trackingMarker.remove();
            mHandler.removeCallbacks(animator);

        }

        public void initialize(boolean showPolyLine) {
            reset();
            this.showPolyline = showPolyLine;

            highLightMarker(0);

            if (showPolyLine) {
                polyLine = initializePolyLine();
            }

            // We first need to put the camera in the correct position for the first run (we need 2 markers for this).....
            LatLng markerPos = markers.get(0).getPosition();
            LatLng secondPos = markers.get(1).getPosition();

            setupCameraPositionForMovement(markerPos, secondPos);

        }

        private void setupCameraPositionForMovement(LatLng markerPos,
                                                    LatLng secondPos) {

            float bearing = bearingBetweenLatLngs(markerPos, secondPos);

            trackingMarker = googleMap.addMarker(new MarkerOptions().position(markerPos)
                    .title("car")
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.carr))
                    .snippet("car"));

            CameraPosition cameraPosition =
                    new CameraPosition.Builder()
                            .target(markerPos)
                            .bearing(bearing + BEARING_OFFSET)
                            .tilt(90)
//                            .zoom(googleMap.getCameraPosition().zoom >= 16 ? googleMap.getCameraPosition().zoom : 16)
                            .zoom(13)
                            .build();

            googleMap.animateCamera(
                    CameraUpdateFactory.newCameraPosition(cameraPosition),
                    ANIMATE_SPEEED_TURN,
                    new GoogleMap.CancelableCallback() {

                        @Override
                        public void onFinish() {
                            System.out.println("finished camera");
                            animator.reset();
                            Handler handler = new Handler();
                            handler.post(animator);
                        }

                        @Override
                        public void onCancel() {
                            System.out.println("cancelling camera");
                        }
                    }
            );
        }

        private Polyline polyLine;
        private PolylineOptions rectOptions = new PolylineOptions().color(Color.GREEN).width(2);;


        private Polyline initializePolyLine() {
            rectOptions.add(markers.get(0).getPosition());
            return googleMap.addPolyline(rectOptions);
        }

        /**
         * Add the marker to the polyline.
         */
        private void updatePolyLine(LatLng latLng) {
            List<LatLng> points = polyLine.getPoints();
            points.add(latLng);
            polyLine.setPoints(points);
        }


        public void stopAnimation() {
            animator.stop();
        }

        public void startAnimation(boolean showPolyLine) {
            if (markers.size() >= 2) {
                animator.initialize(showPolyLine);
            }
        }


        @Override
        public void run() {

            long elapsed = SystemClock.uptimeMillis() - start;
            double t = interpolator.getInterpolation((float) elapsed / ANIMATE_SPEEED);

            double lat = t * endLatLng.latitude + (1 - t) * beginLatLng.latitude;
            double lng = t * endLatLng.longitude + (1 - t) * beginLatLng.longitude;
            LatLng newPosition = new LatLng(lat, lng);

            trackingMarker.setPosition(newPosition);

            if (showPolyline) {
                updatePolyLine(newPosition);
            }
            // It's not possible to move the marker + center it through a cameraposition update while another camerapostioning was already happening.
            if (t < 1) {
                mHandler.postDelayed(this, 16);
            } else {

                Logger.d(TAG, "Move to next marker.... current = " + currentIndex + " and size = " + markers.size());
                // imagine 5 elements -  0|1|2|3|4 currentindex must be smaller than 4
                if (currentIndex < markers.size() - 2) {

                    currentIndex++;

                    endLatLng = getEndLatLng();
                    beginLatLng = getBeginLatLng();


                    start = SystemClock.uptimeMillis();

                    LatLng begin = getBeginLatLng();
                    LatLng end = getEndLatLng();

                    float bearingL = bearingBetweenLatLngs(begin, end);

                    highLightMarker(currentIndex);

                    CameraPosition cameraPosition =
                            new CameraPosition.Builder()
                                    .target(end) // changed this...
                                    .bearing(bearingL + BEARING_OFFSET)
                                    .tilt(tilt)
                                    .zoom(googleMap.getCameraPosition().zoom)
                                    .build();


                    googleMap.animateCamera(
                            CameraUpdateFactory.newCameraPosition(cameraPosition),
                            ANIMATE_SPEEED_TURN,
                            null
                    );

                    start = SystemClock.uptimeMillis();
                    mHandler.postDelayed(animator, 16);

                } else {
                    currentIndex++;
                    highLightMarker(currentIndex);
                    stopAnimation();
                }

            }
        }

        private LatLng getEndLatLng() {
            return markers.get(currentIndex + 1).getPosition();
        }

        private LatLng getBeginLatLng() {
            return markers.get(currentIndex).getPosition();
        }

        private void adjustCameraPosition() {
            Logger.d(TAG, "tilt = " + tilt);
            Logger.d(TAG, "upward = " + upward);
            Logger.d(TAG, "zoom = " + zoom);
            if (upward) {

                if (tilt < 90) {
                    tilt++;
                    zoom -= 0.01f;
                } else {
                    upward = false;
                }

            } else {
                if (tilt > 0) {
                    tilt--;
                    zoom += 0.01f;
                } else {
                    upward = true;
                }
            }
        }
    }

    ;
}
