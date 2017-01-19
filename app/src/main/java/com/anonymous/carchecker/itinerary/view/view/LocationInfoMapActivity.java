package com.anonymous.carchecker.itinerary.view.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.anonymous.carchecker.R;
import com.anonymous.carchecker.common.data.PreferencesUtil;
import com.anonymous.carchecker.common.util.MyDialogAlert;
import com.anonymous.carchecker.login.model.Account;
import com.anonymous.carchecker.login.view.LoginActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationInfoMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_location_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayShowTitleEnabled(true);
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle(R.string.location);
            actionbar.setHomeButtonEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_car_location_info);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng current = new LatLng(21.734106, 105.25956);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 16));
        Marker marker = googleMap.addMarker(new MarkerOptions().position(current).title("29C-64344").icon(BitmapDescriptorFactory.fromResource(R.drawable.nosign_left_down_down)));
        marker.showInfoWindow();
        googleMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        showCarInfo();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.location_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_location_info:
                showCarInfo();
                break;
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void showCarInfo () {
        MyDialogAlert myDialogAlert = new MyDialogAlert(this);
        myDialogAlert.show(R.string.user_name, R.string.fake_msg_data, new MyDialogAlert.DialogListener() {
            @Override
            public void onPositiveButtonClick() {

            }

            @Override
            public void onNegativeButtonClick() {

            }
        }, false, true);
    }
}
