package com.anonymous.carchecker.position.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.anonymous.carchecker.R;
import com.anonymous.carchecker.common.ApplicationUtil;
import com.anonymous.carchecker.common.CustomDialogBuilder;
import com.anonymous.carchecker.common.logger.Logger;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapInfoActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final String TAG = "MapInfoActivity";
    private List<Marker> markers = new ArrayList<>();
    private GoogleMap googleMap;
    private final Handler mHandler = new Handler();
    private Animator animator = new Animator();
    private Button mBtWatchButton;
    private boolean mIsWatched;
    private Spinner mSpeedSpinner;
    private SpinnerAdapter mSpinnerAdapter;
    private Context mContext;
    private PolylineOptions mRectOptions = new PolylineOptions().color(Color.BLUE).width(3);
    private boolean mIsMapReady;
    private boolean mIsFirstWatchPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_info);

        mContext = getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayShowTitleEnabled(true);
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle(R.string.itinerary);
            actionbar.setHomeButtonEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }

        if (!ApplicationUtil.isNetworkAvailable(mContext)) {
            Logger.d(TAG, "network errors");
            showNetworkUnavailable();
        } else {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mBtWatchButton = (Button) findViewById(R.id.mWatchButton);
            mBtWatchButton.setAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_select));
            mBtWatchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mIsMapReady) {
                        if (mIsWatched) {
                            mBtWatchButton.setText(mContext.getString(R.string.watch));
                            mBtWatchButton.setBackgroundResource(R.drawable.xem_button);
                            mIsWatched = false;
                            Logger.d(TAG, "Press Stop");
                        } else {
                            mBtWatchButton.setText(mContext.getString(R.string.stop));
                            mBtWatchButton.setBackgroundResource(R.drawable.dung_button);
                            mIsWatched = true;
                            animator.startAnimation(true);
                            Logger.d(TAG, "Press watch");
                        }
                    } else {
                        Toast.makeText(mContext, "bản đồ chưa sẵn sàng, hãy đợi ít phút!!", Toast.LENGTH_LONG);
                    }
                }
            });
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mIsMapReady = true;
        this.googleMap = googleMap;
        addDefaultLocations();
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (Marker marker : markers) {
                    builder.include(marker.getPosition());
                }
                LatLngBounds bounds = builder.build();
                int padding = 0; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                googleMap.animateCamera(cu);
            }
        });
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
        googleMap.addPolyline(mRectOptions);
    }

    private void addMarkerToMap(LatLng latLng, boolean isMarkerRemoved) {
        Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng)
                .title("Kien An")
                .snippet("Map example"));
        markers.add(marker);
        mRectOptions.add(marker.getPosition());
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

        private int animate_speed = 1500;

        private final Interpolator interpolator = new LinearInterpolator();

        int currentIndex = 0;

        long start = SystemClock.uptimeMillis();

        LatLng endLatLng = null;
        LatLng beginLatLng = null;


        private Marker trackingMarker;

        private void reset() {
            resetMarkers();
            currentIndex = 0;
            start = SystemClock.uptimeMillis();
            endLatLng = getEndLatLng();
            beginLatLng = getBeginLatLng();
        }

        private void finalizeStop() {
            trackingMarker.remove();
            mHandler.removeCallbacks(animator);
            mBtWatchButton.setText(mContext.getString(R.string.watch));
            mBtWatchButton.setBackgroundResource(R.drawable.xem_button);
            mIsWatched = false;
        }

        private void setAnimateSpeed(int speed) {
            animate_speed = speed;
        }

        private void initialize() {
            if (mIsFirstWatchPressed && mIsWatched) {
                Logger.d(TAG, "mIsFirstWatchPressed = true + Move to next marker.... current = " + currentIndex + " and size = " + markers.size());
                // imagine 5 elements -  0|1|2|3|4 currentindex must be smaller than 4
                if (currentIndex < markers.size() - 2) {

                    currentIndex++;

                    endLatLng = getEndLatLng();
                    beginLatLng = getBeginLatLng();
                    trackingMarker.setIcon(BitmapDescriptorFactory.fromResource(getCarDirections(beginLatLng, endLatLng)));
                    highLightMarker(currentIndex);
                    start = SystemClock.uptimeMillis();
                    if (mIsWatched)
                        mHandler.postDelayed(animator, 16);

                } else {
                    currentIndex++;
                    highLightMarker(currentIndex);
                    animator.finalizeStop();
                }
            } else {
                reset();
                mIsFirstWatchPressed = true;
                LatLng markerPos = markers.get(0).getPosition();

                trackingMarker = googleMap.addMarker(new MarkerOptions().position(markerPos)
                        .title("car")
                        .anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.car_down))
                        .snippet("car"));
                trackingMarker.setIcon(BitmapDescriptorFactory.fromResource(getCarDirections(beginLatLng, endLatLng)));
                highLightMarker(0);
                mHandler.post(animator);
                Logger.d(TAG, "first start : trackingMarker = " + trackingMarker);
            }
        }

        private void stopAnimation() {
            mHandler.removeCallbacks(animator);
        }

        private void startAnimation(boolean showPolyLine) {
            Logger.d(TAG, "startAnimation markers list size = " + markers.size());
            if (markers.size() >= 2) {
                animator.initialize();
            }
        }

        @Override
        public void run() {
            if (mIsWatched) {
                long elapsed = SystemClock.uptimeMillis() - start;
                double t = interpolator.getInterpolation((float) elapsed / animate_speed);

                double lat = t * endLatLng.latitude + (1 - t) * beginLatLng.latitude;
                double lng = t * endLatLng.longitude + (1 - t) * beginLatLng.longitude;
                LatLng newPosition = new LatLng(lat, lng);
                trackingMarker.setIcon(BitmapDescriptorFactory.fromResource(getCarDirections(beginLatLng, endLatLng)));
//            LatLngBounds.Builder builder = new LatLngBounds.Builder();
//            builder.include(beginLatLng);
//            builder.include(endLatLng);
//            builder.include(trackingMarker.getPosition());
//            LatLngBounds bounds = builder.build();
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));
                trackingMarker.setPosition(newPosition);

                // It's not possible to move the marker + center it through a cameraposition update while another camerapostioning was already happening.
                if (t < 1) {
                        mHandler.postDelayed(this, 16);
                } else {
                    Logger.d(TAG, "Move to next marker.... current = " + currentIndex + " and size = " + markers.size());
                    if (currentIndex < markers.size() - 2) {

                        currentIndex++;

                        endLatLng = getEndLatLng();
                        beginLatLng = getBeginLatLng();
                        trackingMarker.setIcon(BitmapDescriptorFactory.fromResource(getCarDirections(beginLatLng, endLatLng)));
                        highLightMarker(currentIndex);
                        start = SystemClock.uptimeMillis();

                        mHandler.postDelayed(animator, 16);

                    } else {
                        currentIndex++;
                        highLightMarker(currentIndex);
                        animator.finalizeStop();
                        mIsFirstWatchPressed = false;
                    }
                }
            }
        }

        private LatLng getEndLatLng() {
            return markers.get(currentIndex + 1).getPosition();
        }

        private LatLng getBeginLatLng() {
            return markers.get(currentIndex).getPosition();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.itinerary_menu, menu);
        MenuItem item = menu.findItem(R.id.spinnerSpeed);
        mSpeedSpinner = (Spinner) MenuItemCompat.getActionView(item);
        List<String> speedList = new ArrayList<>(Arrays.asList(mContext.getResources().getStringArray(R.array.speed)));
        mSpinnerAdapter = new SpinnerAdapter(mContext, R.layout.spinner_item, speedList);
        mSpeedSpinner.setAdapter(mSpinnerAdapter);
        int width = getResources().getDimensionPixelSize(R.dimen.dropdown_list_width);
        int verticalOffset = getResources().getDimensionPixelSize(R.dimen.dropdown_vertical_offset);
        mSpeedSpinner.setDropDownWidth(width);
        mSpeedSpinner.setDropDownVerticalOffset(verticalOffset);
        mSpeedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSpeedSpinner.setSelection(i);
                mSpinnerAdapter.setSelection(i);
                if (i == 0) {
                    animator.setAnimateSpeed(100);
                } else if (i == 1) {
                    animator.setAnimateSpeed(200);
                } else if (i == 2) {
                    animator.setAnimateSpeed(300);
                } else if (i == 3) {
                    animator.setAnimateSpeed(400);
                } else if (i == 4) {
                    animator.setAnimateSpeed(500);
                } else if (i == 5) {
                    animator.setAnimateSpeed(600);
                } else if (i == 6) {
                    animator.setAnimateSpeed(800);
                } else if (i == 7) {
                    animator.setAnimateSpeed(1000);
                } else {
                    animator.setAnimateSpeed(2000);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void showLocationNotFound() {
        if (!isFinishing()) {
            CustomDialogBuilder customDialogBuilder = new CustomDialogBuilder(MapInfoActivity.this)
                    .setMessage("Chưa bật gps để xác định vị trí, xin hãy bật gps lên!")
                    .setTitle("Chưa tìm được vị trí")
                    .addLeftButton("ignore", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    })
                    .addRightButton("settings", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    });
            Dialog dialog = customDialogBuilder.build();
            dialog.show();
        } else {
            Logger.d(TAG, "NearbyActivity is finishing");
        }
    }

    private void showNetworkUnavailable() {
        CustomDialogBuilder customDialogBuilder = new CustomDialogBuilder(this)
                .setTitle(R.string.notice)
                .setMessage(R.string.network_error)
                .addRightButton(R.string.btn_ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
        customDialogBuilder.build().show();
    }

    private int getCarDirections(LatLng startMarker, LatLng endMarker) {
        int angle = getAngle(startMarker, endMarker);
        Logger.d(TAG, "angle = " + angle);
        if (angle == 270) {
            Logger.d(TAG, "KienAn: R.drawable.car_down");
            return R.drawable.car_down;
        }
        if (angle > 225 && angle < 270) {
            Logger.d(TAG, "KienAn: R.drawable.car_left_down_down");
            return R.drawable.car_left_down_down;
        }
        if (angle == 225) {
            Logger.d(TAG, "KienAn: R.drawable.car_left_down");
            return R.drawable.car_left_down;
        }
        if (angle > 180 && angle < 225) {
            Logger.d(TAG, "KienAn: R.drawable.car_left_down_left");
            return R.drawable.car_left_down_left;
        }
        if (angle == 180) {
            Logger.d(TAG, "KienAn: R.drawable.car_left ");
            return R.drawable.car_left;
        }
        if (angle > 135 && angle < 180) {
            Logger.d(TAG, "KienAn: R.drawable.car_left_up_left");
            return R.drawable.car_left_up_left;
        }
        if (angle == 135) {
            Logger.d(TAG, "KienAn: R.drawable.car_left_up");
            return R.drawable.car_left_up;
        }
        if (angle > 90 && angle < 135) {
            Logger.d(TAG, "KienAn: R.drawable.car_left_up_up");
            return R.drawable.car_left_up_up;
        }
        if (angle == 90) {
            Logger.d(TAG, "KienAn: R.drawable.car_up");
            return R.drawable.car_up;
        }
        if (angle > 45 && angle < 90) {
            Logger.d(TAG, "KienAn: R.drawable.car_right_up_up");
            return R.drawable.car_right_up_up;
        }
        if (angle > 0 && angle < 45) {
            Logger.d(TAG, "KienAn: R.drawable.car_right_up_right");
            return R.drawable.car_right_up_right;
        }
        if (angle == 45) {
            Logger.d(TAG, "KienAn: R.drawable.car_right_up");
            return R.drawable.car_right_up;
        }
        if (angle == 0) {
            Logger.d(TAG, "KienAn: R.drawable.car_right");
            return R.drawable.car_right;
        }
        if (angle > 315 && angle < 360) {
            Logger.d(TAG, "KienAn: R.drawable.car_right_down_right");
            return R.drawable.car_right_down_right;
        }
        if (angle > 270 && angle < 315) {
            Logger.d(TAG, "KienAn: R.drawable.car_right_down_down");
            return R.drawable.car_right_down_down;
        }
        if (angle == 315) {
            Logger.d(TAG, "KienAn: R.drawable.car_right_down");
            return R.drawable.car_right_down;
        }

        return R.drawable.car_down;
    }

    private double abs(double x) {
        return x < 0 ? (-x) : x;
    }

    public int getAngle(LatLng start, LatLng target) {
        float angle = (float) Math.toDegrees(Math.atan2(target.latitude - start.latitude, target.longitude - start.longitude));

        if (angle < 0) {
            angle += 360;
        }

        return (int) angle;
    }
}
