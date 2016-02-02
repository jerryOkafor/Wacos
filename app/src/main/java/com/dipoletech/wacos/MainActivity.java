package com.dipoletech.wacos;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dipoletech.wacos.adapaters.CroppingOptionAdapter;
import com.dipoletech.wacos.adapaters.ViewPagerAdapter;
import com.dipoletech.wacos.model.User;
import com.dipoletech.wacos.util.Constants;
import com.dipoletech.wacos.util.CroppingOption;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements
        Contributions.OnFragmentInteractionListener,
        History.OnFragmentInteractionListener,
        News.OnFragmentInteractionListener,
        FeedBackFragment.OnFragmentInteractionListener,
        About.OnFragmentInteractionListener
{

    private static final String NEW_POST_FRAG = "new_post_frag";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String WACOS_SHARE_HASHTAG = "#WACOS";
    private Uri mImageCaptureUri;
    private int CAMERA_CODE = 100;
    private int GALLERY_CODE = 101;
    private int CROPPING_CODE = 103;
    private File outPutFile;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private DrawerLayout drawerlayout;
    private NavigationView navigationView;
    private TextView displayNameTv;
    private CircleImageView displayImageView;
    private Firebase fireBaseRef;
    private TextView upLineSuretyIdTv;
    private SharedPreferences settings;
    private Firebase pImageRef;
    private User user;
    private ProgressBar pImageProgress;
    private ImageButton pImageEditBtn;
    private String mShareString = "Downlaod your own copy of this awesome app for the Women Affairs Co-Operative Society.\n" +
            "Follow this link: link goes here\n\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        outPutFile = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");

        //shared prefs
        settings = PreferenceManager.getDefaultSharedPreferences(this);

        //get the fire-base ref
        fireBaseRef = new Firebase(Constants.appUrl);
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
                        //begin share, build the share intent and share String

                        Intent shareIntent = createShareIntent();
                        startActivity(shareIntent);
                        drawerlayout.closeDrawers();
                        break;
                    case R.id.action_settings:
                        drawerlayout.closeDrawers();
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        break;
                    case R.id.action_about:
                        drawerlayout.closeDrawers();
                        About about = new About();
                        about.show(getSupportFragmentManager(),"about_fragment");
                        break;
                    case R.id.action_sign_out:
                        //sign out
                        drawerlayout.closeDrawers();
                        fireBaseRef.unauth();
                        SharedPreferences.Editor editor = settings.edit();
                        editor.clear();
                        startActivity(new Intent(MainActivity.this, Intro.class));
                        break;
                }
                return true;
            }
        });


        //get the user details
        //get the header first
        View header = navigationView.getHeaderView(0);
        //get profile image progress indicator
        pImageProgress = (ProgressBar) header.findViewById(R.id.p_image_progress);
        displayNameTv = (TextView) header.findViewById(R.id.display_name_tv);
        upLineSuretyIdTv = (TextView)header.findViewById(R.id.surety_id_tv);
        displayImageView = (CircleImageView) header.findViewById(R.id.display_image);

        pImageEditBtn = (ImageButton) header.findViewById(R.id.p_image_edit);
        pImageEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageOption();
            }
        });
        displayImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start changing profile image
//                Toast.makeText(MainActivity.this,"You Clicke me",Toast.LENGTH_LONG).show();

            }

        });

        //start setting it
        Firebase pRef = fireBaseRef.child("users");
        pRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = null;
                for (DataSnapshot users : dataSnapshot.getChildren()) {
                    user = users.getValue(User.class);
                    //check if authData is null
                    if (fireBaseRef.getAuth().getUid() != null) {
                        //check if the user matches the giving surety id
                        if (user.getUid().equals(fireBaseRef.getAuth().getUid())) {
                            displayNameTv.setText(user.getName() + " (" + user.getSuretyId() + ")");
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString(Constants.displaName, user.getName());
                            editor.commit();
                            upLineSuretyIdTv.setText("UpLine Surety Id: " + user.getUpLineSuretyId());

                            //check if the user has changed his profile image
                            if(user.getProfileImage()!=null) {
                                displayImageView.setImageBitmap(Constants.getDecodedBitmap(user.getProfileImage()));
//                                Log.v(TAG,user.getProfileImage());
                                pImageProgress.setVisibility(View.INVISIBLE);
                            }else {
                                //load the image
                    Glide.with(MainActivity.this)
                            .load(fireBaseRef.getAuth().getProviderData().get("profileImageURL"))
                            .placeholder(R.mipmap.ic_launcher)
                            .listener(new RequestListener<Object, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, Object model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, Object model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    pImageProgress.setVisibility(View.INVISIBLE);
                                    return true;
                                }
                            })
                            .into(displayImageView);

                            }
                        }
                    }


                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




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

    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mShareString + WACOS_SHARE_HASHTAG);
        return shareIntent;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void tintTabIcons() {
        tabLayout.getTabAt(0).getIcon().setTint(getResources().getColor(R.color.disabled));
        tabLayout.getTabAt(1).getIcon().setTint(getResources().getColor(R.color.disabled));
        tabLayout.getTabAt(2).getIcon().setTint(getResources().getColor(R.color.disabled));

        tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getIcon().setTint(getResources().getColor(R.color.white));
    }

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

            mImageCaptureUri = data.getData();
            System.out.println("Gallery Image URI : " + mImageCaptureUri);
            CroppingImage();

        } else if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {

            System.out.println("Camera Image URI : " + mImageCaptureUri);
            CroppingImage();
        } else if (requestCode == CROPPING_CODE) {

            try {
                if (outPutFile.exists()) {
                    Bitmap photo = Constants.decodeFile(outPutFile);
                    displayImageView.setImageBitmap(photo);
                    Log.v(TAG, photo.toString());
                    //here you shall use volley to update the user profile picture
                    uploadProfileImage(photo);
                } else {
                    Toast.makeText(this, "Error while saving image", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadProfileImage(Bitmap photo) {
        String image = Constants.getStringImage(photo);
        Log.v(TAG,image);
        pImageRef = fireBaseRef.child("users").child(fireBaseRef.getAuth().getUid()).child("profileImage");
        pImageRef.setValue(image);
        pImageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    //useful functions begins
    private void selectImageOption() {
        final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Capture Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp1.jpg");
                    mImageCaptureUri = Uri.fromFile(f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                    startActivityForResult(intent, CAMERA_CODE);

                } else if (items[item].equals("Choose from Gallery")) {

                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, GALLERY_CODE);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    //used to vrop the images wen the size is much
    private void CroppingImage() {

        final ArrayList<CroppingOption> cropOptions = new ArrayList();

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
        int size = list.size();
        if (size == 0) {
            Toast.makeText(this, "Cann't find image cropping app", Toast.LENGTH_SHORT).show();
            return;
        } else {
            intent.setData(mImageCaptureUri);
            intent.putExtra("outputX", 512);
            intent.putExtra("outputY", 512);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);

            //TODO: don't use return-data tag because it's not return large image data and crash not given any message
            //intent.putExtra("return-data", true);

            //Create output file here
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outPutFile));

            if (size == 1) {
                Intent i = new Intent(intent);
                ResolveInfo res = list.get(0);

                i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                startActivityForResult(i, CROPPING_CODE);
            } else {
                for (ResolveInfo res : list) {
                    final CroppingOption co = new CroppingOption();

                    co.title = getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                    co.icon = getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);
                    co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                    cropOptions.add(co);
                }

                CroppingOptionAdapter adapter = new CroppingOptionAdapter(getApplicationContext(), cropOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose Cropping App");
                builder.setCancelable(false);
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        startActivityForResult(cropOptions.get(item).appIntent, CROPPING_CODE);
                    }
                });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        if (mImageCaptureUri != null) {
                            getContentResolver().delete(mImageCaptureUri, null, null);
                            mImageCaptureUri = null;
                        }
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }



}
