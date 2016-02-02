package com.dipoletech.wacos;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dipoletech.wacos.adapaters.CroppingOptionAdapter;
import com.dipoletech.wacos.adapaters.PostsAdapter;
import com.dipoletech.wacos.model.Post;
import com.dipoletech.wacos.util.Constants;
import com.dipoletech.wacos.util.CroppingOption;
import com.dipoletech.wacos.util.HideViewOnScroll;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link News.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link News#newInstance} factory method to
 * create an instance of this fragment.
 */
public class News extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int GRID_COUNT = 1;
    private static final String TAG = News.class.getSimpleName();
    private static final int FILE_CODE = 104;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View rootView;
    private RecyclerView infoRecycler;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private PostsAdapter infoAdapter;
    private LinearLayout pLayout;
    private ImageButton newPostBtn;
    private List<Post> postsList;
    private Firebase postRef;
    private LinearLayout pPBar;
    private TextView newInfoTv;
    private Firebase nPostRef;
    private SharedPreferences settings;
    private ImageButton attachBtn;
    private Uri mAttachmentUri;

    private int CAMERA_CODE = 100;
    private int GALLERY_CODE = 101;
    private int CROPPING_CODE = 103;
    private File outPutFile;
    private ArrayList<String> attachListArray;
    private TextView attachCountTv;
    private LinearLayout newPostBox;

    public News() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment News.
     */
    // TODO: Rename and change types and number of parameters
    public static News newInstance(String param1, String param2) {
        News fragment = new News();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }


        outPutFile = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
        attachListArray = new ArrayList();

        postsList = new ArrayList<>();
        //get firbase ref
        postRef  = new Firebase(Constants.appUrl+"/posts");
        settings = PreferenceManager.getDefaultSharedPreferences(getContext());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_news, container, false);

        //get the attachment button
        attachBtn = (ImageButton)rootView.findViewById(R.id.post_attach_btn);
        attachCountTv = (TextView) rootView.findViewById(R.id.attach_count_tv);

        attachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check if the length is up to four
                if (attachListArray.size()<4) {
                    //start selecting the file/ not only image
                    selectFileOptions();
                }else {
                    Toast.makeText(getContext(),"Max. attachment reached",Toast.LENGTH_LONG).show();
                }
            }
        });

        pPBar = (LinearLayout) rootView.findViewById(R.id.pLayout);
        //get the new post edit text
        newInfoTv = (TextView) rootView.findViewById(R.id.info_edit_text);
        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clear the old data
                postsList.clear();
                for (DataSnapshot posts : dataSnapshot.getChildren()) {
                    Post post = posts.getValue(Post.class);
                    postsList.add(post);
                }
                infoAdapter.swapData(postsList);
                infoRecycler.scrollToPosition(postsList.size() - 1);
                Log.v(TAG, postsList.toString());
                pPBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //get the fab
        newPostBtn = (ImageButton) rootView.findViewById(R.id.send_info_btn);
        newPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newInfoTv.getText().toString().isEmpty()) {
//                    Toast.makeText(getContext(), "You must enter a message", Toast.LENGTH_LONG).show();
                    newInfoTv.setError("Enter a message");
                } else {
                    //get the ref for the particular post
                    nPostRef = postRef.child(String.valueOf(System.currentTimeMillis()));
                    //get the text
                    String postText = newInfoTv.getText().toString().trim();
                    Post post = new Post();
                    post.setDate(System.currentTimeMillis());
                    post.setWhat(postText);
                    post.setViews(0);
                    post.setUid(postRef.getAuth().getUid());
                    post.setDisplayName(settings.getString(Constants.displaName, null));
                    post.setAttachments(attachListArray);
                    nPostRef.setValue(post);
                    //clear old data first.
                    attachListArray.clear();
                    //clear post text aswell
                    newInfoTv.setText("");
                    attachCountTv.setText("0");
                }

            }
        });

        //get the pLayout
        pLayout = (LinearLayout) rootView.findViewById(R.id.pLayout);
        newPostBox = (LinearLayout) rootView.findViewById(R.id.new_post_box);

        //get the infoRecycler
        infoRecycler = (RecyclerView) rootView.findViewById(R.id.info_recycler);

        infoAdapter = new PostsAdapter(getContext(), postsList,postRef.getAuth().getUid());

        //get the Layout manager for thr Recycler view
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(GRID_COUNT, StaggeredGridLayoutManager.VERTICAL);
        infoRecycler.setLayoutManager(mStaggeredLayoutManager);

        infoRecycler.setAdapter(infoAdapter);

        //set the scroll listener
        infoRecycler.addOnScrollListener(new HideViewOnScroll() {
            @Override
            protected void onHide() {
                newPostBox.animate().translationY(newPostBox.getHeight()+newPostBox.getBottom())
                        .setInterpolator(new LinearInterpolator()).start();

            }

            @Override
            protected void onShow() {

                newPostBox.animate().translationY(0)
                        .setInterpolator(new LinearInterpolator()).start();
            }
        });


        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK) {
            System.out.println("Camera Image URI : " + mAttachmentUri);
            if (Constants.isInRange(outPutFile))
            {
                //do something which is to upload the image
                decodeToBitmapAndAdd();
//                Toast.makeText(getContext(),"File is in Range",Toast.LENGTH_LONG).show();
            } else {
                CroppingImage();
//                Toast.makeText(getContext(), "File is in Out of Range", Toast.LENGTH_LONG).show();
            }
            }else if (requestCode == GALLERY_CODE && resultCode == Activity.RESULT_OK && null != data)
            {
                //get the attachment uri
            mAttachmentUri = data.getData();
            System.out.println("Gallery Image URI : " + mAttachmentUri);
            if(Constants.isInRange(outPutFile))            {
                //do something which is to upload the image
                decodeToBitmapAndAdd();
            }else {
                CroppingImage();
            }
        } else if (requestCode==FILE_CODE && resultCode ==Activity.RESULT_OK){
            //get the uri
            mAttachmentUri = data.getData();
            System.out.println("File URI : " + mAttachmentUri);
            //get the output file
            outPutFile = new File(mAttachmentUri.getPath());
            //you can not crop a file so just pause
            if(Constants.isInRange(outPutFile))
            {
                addFile(outPutFile);
                Toast.makeText(getContext(), "The file is in range!"+outPutFile.length(), Toast.LENGTH_LONG).show();
                
            }else {
                Toast.makeText(getContext(),"The file is too big, attachment failed",Toast.LENGTH_LONG).show();
            }

        }else if (requestCode == CROPPING_CODE) {
            decodeToBitmapAndAdd();
        }
    }

    private void addFile(File file) {
        String fileString  = null;
        try {
            fileString = com.firebase.client.utilities.Base64.encodeFromFile(file.getPath());
            attachListArray.add(fileString);
            Toast.makeText(getContext(), "File attached.", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }

    private void decodeToBitmapAndAdd() {
        try {
            if (outPutFile.exists()) {
                Bitmap photo = Constants.decodeFile(outPutFile);
                //here you shall use volley to update the user profile picture
                addImage(photo);
                Toast.makeText(getContext(), "Image attached.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Error while attaching image image", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addImage(Bitmap photo) {
        String encoded = Constants.getStringImage(photo);
        attachListArray.add(encoded);
        attachCountTv.setText(String.valueOf(attachListArray.size()));
    }

    private void selectFileOptions() {
        final CharSequence[] items = {"Capture Photo Now", "Choose Image from Gallery","Choose File from Device", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add A File!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Capture Photo Now")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp1.jpg");
                    mAttachmentUri = Uri.fromFile(f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mAttachmentUri);
                    startActivityForResult(intent, CAMERA_CODE);

                } else if (items[item].equals("Choose Image from Gallery")) {

                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, GALLERY_CODE);

                } else if (items[item].equals("Choose File from Device")) {

//                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//                    i.setType("*/*");
//                    startActivityForResult(i, FILE_CODE);
                    Toast.makeText(getContext(),"Coming Soon",Toast.LENGTH_LONG).show();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    //used to crop the images wen the size is much
    private void CroppingImage() {

        final ArrayList<CroppingOption> cropOptions = new ArrayList();

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        int size = list.size();
        if (size == 0) {
            Toast.makeText(getContext(), "Cann't find image cropping app", Toast.LENGTH_SHORT).show();
            return;
        } else {
            intent.setData(mAttachmentUri);
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

                    co.title = getActivity().getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                    co.icon = getActivity().getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);
                    co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                    cropOptions.add(co);
                }

                CroppingOptionAdapter adapter = new CroppingOptionAdapter(getActivity().getApplicationContext(), cropOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                        if (mAttachmentUri != null) {
                            getActivity().getContentResolver().delete(mAttachmentUri, null, null);

                            mAttachmentUri = null;
                        }
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }//end cropping function

}
