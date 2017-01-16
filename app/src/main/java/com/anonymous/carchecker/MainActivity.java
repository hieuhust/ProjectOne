package com.anonymous.carchecker;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.anonymous.carchecker.common.data.PreferencesUtil;
import com.anonymous.carchecker.common.util.CircleTransform;
import com.anonymous.carchecker.common.util.MyDialogAlert;
import com.anonymous.carchecker.common.view.BaseActivity;
import com.anonymous.carchecker.common.view.BaseFragment;
import com.anonymous.carchecker.itinerary.view.ReviewItineraryFragment;
import com.anonymous.carchecker.login.model.Account;
import com.anonymous.carchecker.login.view.LoginActivity;
import com.anonymous.carchecker.position.model.InfoVehicle;
import com.anonymous.carchecker.position.view.PositionInfoFragment;
import com.squareup.picasso.Picasso;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, PositionInfoFragment.OnPositionInfoFragmentInteractionListener {

    private DrawerLayout mDrawerLayout;

    private NavigationView mNavigationView;

    private Toolbar toolbar;

    private Menu mMenu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar_app_bar_main);
        toolbar.setTitle(R.string.position);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        // transform avatar to circle shape
        ImageView avatar = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.avata_nav_header_main);
        Picasso.with(this).load(R.drawable.ic_user).transform(new CircleTransform()).into(avatar);

        // Default go to the first fragment containing position info
        setFragment(R.string.position, PositionInfoFragment.newInstance(1));
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the mMenu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.mMenu = menu;

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_position) {
            setFragment(R.string.position, PositionInfoFragment.newInstance(1));
        } else if (id == R.id.nav_review_itinerary) {
            setFragment(R.string.review_hanh_trinh, ReviewItineraryFragment.newInstance());
        }
        else if(id == R.id.nav_logout){
            MyDialogAlert myDialogAlert = new MyDialogAlert(this);
            myDialogAlert.show(R.string.confirm, R.string.logout_msg, new MyDialogAlert.DialogListener() {
                @Override
                public void onPositiveButtonClick() {
                    PreferencesUtil preferencesUtil = PreferencesUtil.newInstance(getApplicationContext());
                    preferencesUtil.removeDataModel(Account.class);
                    gotoActivity(LoginActivity.class);
                    finish();
                }

                @Override
                public void onNegativeButtonClick() {

                }
            });
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(InfoVehicle item) {

    }

    private void setFragment(int titleToolbar, BaseFragment fragment) {
        gotoFragment(R.id.content_fragment_container, fragment);

        //set title for toolbar
        toolbar.setTitle(titleToolbar);
    }




}
