package com.dipoletech.wacos;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dipoletech.wacos.adapaters.ViewPagerAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements
        Contributions.OnFragmentInteractionListener,
        History.OnFragmentInteractionListener,
        News.OnFragmentInteractionListener,
        FeedBackFragment.OnFragmentInteractionListener
{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private DrawerLayout drawerlayout;
    private NavigationView navigationView;
    private TextView displayNameTv;
    private CircleImageView displayImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize the drawer layout
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        //action bar drawer Toggle
        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawerlayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //TransitionManager.beginDelayedTransition(drawerlayout, new Fade());
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //TransitionManager.beginDelayedTransition(drawerlayout, new Fade());
            }

        };

        //set the drawerLayout Listener
        drawerlayout.setDrawerListener(drawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        drawerToggle.syncState();


        //initialise the Navigation view
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        //make all the menu icons to appear as required
//       navigationView.setItemIconTintList(null);
        //set the item text color
//        navigationView.setItemTextColor(getResources().getColorStateList(R.color.colorPrimary));

        //set the listener for the Navigation view
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_pay:
                        drawerlayout.closeDrawers();
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_history:
                        drawerlayout.closeDrawers();
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.action_news:
                        drawerlayout.closeDrawers();
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.action_rate:
                        //you shall complete action using google play or others
                        drawerlayout.closeDrawers();
                        break;
                    case R.id.action_feedback:
                        drawerlayout.closeDrawers();
                        FeedBackFragment feedBackFragment = new FeedBackFragment();
                        feedBackFragment.show(getSupportFragmentManager(), "feed_back_fragment");
                        break;
                    case R.id.action_share:
                        drawerlayout.closeDrawers();
                        break;
                    case R.id.action_settings:
                        drawerlayout.closeDrawers();
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        break;
                    case R.id.action_sign_out:
                        //clear account
//                        SharedPreferences.Editor editor = settings.edit();
//                        editor.clear();
//                        editor.commit();
//                        drawerlayout.closeDrawers();
//                        finish();
                        break;
                }
                return true;
            }
        });


        //get the user details
        //get the header first
        View header = navigationView.getHeaderView(0);
        displayNameTv = (TextView) header.findViewById(R.id.display_name_tv);
        displayImageView = (CircleImageView) header.findViewById(R.id.display_image);

        //start setting it
//        displayNameTv.setText(String.format(getString(R.string.format_display_name), displayName));
//        Glide.with(this)
//                .load(profileImageUrl)
//                .into(displayImageView);


        //get the viewpager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        setupViewPager(viewPager);

        //get the tab layout and tweek it
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setTint(getResources().getColor(R.color.white));
                viewPager.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setTint(getResources().getColor(R.color.disabled));

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tintTabIcons();
    }//end onCreate

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void tintTabIcons() {
        tabLayout.getTabAt(0).getIcon().setTint(getResources().getColor(R.color.disabled));
        tabLayout.getTabAt(1).getIcon().setTint(getResources().getColor(R.color.disabled));
        tabLayout.getTabAt(2).getIcon().setTint(getResources().getColor(R.color.disabled));

        tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getIcon().setTint(getResources().getColor(R.color.white));
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Contributions(), "Contributions");
        adapter.addFragment(new History(), "History");
        adapter.addFragment(new News(), "News");
        viewPager.setAdapter(adapter);
    }


    private void setupTabIcons() {
        int[] tabIcons = {
                R.drawable.ic_payment,
                R.drawable.ic_history,
                R.drawable.ic_archive

        };


        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
