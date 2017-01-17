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
import com.anonymous.carchecker.common.util.MyDialogAlert;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
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
    private PolylineOptions mRectOptions = new PolylineOptions().color(Color.BLUE).width(5);
    private boolean mIsMapReady;

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
                    if (mIsMapReady){
                        if (mIsWatched) {
                            mBtWatchButton.setText(mContext.getString(R.string.watch));
                            mBtWatchButton.setBackgroundResource(R.drawable.xem_button);
                            mIsWatched = false;
                            animator.stopAnimation();
                        } else {
                            mBtWatchButton.setText(mContext.getString(R.string.stop));
                            mBtWatchButton.setBackgroundResource(R.drawable.dung_button);
                            mIsWatched = true;
                            animator.startAnimation(true);
                        }
                    }else{
                        Toast.makeText(mContext, "bản đồ chưa sẵn sàng, hãy đợi ít phút!!", Toast.LENGTH_LONG);
                    }
                }
            });
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mIsMapReady = true;
        this.googleMap = googleMap;

        addDefaultLocations();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers.get(0).getPosition(), 13));
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

        boolean showPolyline = false;

        private Marker trackingMarker;

        private void reset() {
            resetMarkers();
            start = SystemClock.uptimeMillis();
            currentIndex = 0;
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

        private void setAnimateSpeed (int speed) {
            animate_speed = speed;
        }

        private void initialize(boolean showPolyLine) {
            reset();
            this.showPolyline = showPolyLine;

            LatLng markerPos = markers.get(0).getPosition();

            trackingMarker = googleMap.addMarker(new MarkerOptions().position(markerPos)
                    .title("car")
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.carr))
                    .snippet("car"));
            animator.reset();
            highLightMarker(0);
            Handler handler = new Handler();
            handler.post(animator);

        }

        private void stopAnimation() {
            trackingMarker.remove();
            mHandler.removeCallbacks(animator);
        }

        private void startAnimation(boolean showPolyLine) {
            if (markers.size() >= 2) {
                animator.initialize(showPolyLine);
            }
        }

        @Override
        public void run() {

            long elapsed = SystemClock.uptimeMillis() - start;
            double t = interpolator.getInterpolation((float) elapsed / animate_speed);

            double lat = t * endLatLng.latitude + (1 - t) * beginLatLng.latitude;
            double lng = t * endLatLng.longitude + (1 - t) * beginLatLng.longitude;
            LatLng newPosition = new LatLng(lat, lng);

            trackingMarker.setPosition(newPosition);

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


                    highLightMarker(currentIndex);

                    start = SystemClock.uptimeMillis();
                    mHandler.postDelayed(animator, 16);

                } else {
                    currentIndex++;
                    highLightMarker(currentIndex);
                    animator.finalizeStop();
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
                if(i == 0){
                    animator.setAnimateSpeed(100);
                }else if (i==1){
                    animator.setAnimateSpeed(200);
                }else if (i==2){
                    animator.setAnimateSpeed(300);
                }else if (i==3){
                    animator.setAnimateSpeed(400);
                }else if (i==4){
                    animator.setAnimateSpeed(500);
                }else if (i==5){
                    animator.setAnimateSpeed(600);
                }else if (i==6){
                    animator.setAnimateSpeed(800);
                }else{
                    animator.setAnimateSpeed(1000);
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
}
