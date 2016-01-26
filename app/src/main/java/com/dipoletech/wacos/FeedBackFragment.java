package com.dipoletech.wacos;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeedBackFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeedBackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedBackFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View rootView;
    private Button feedBackCancelButton;
    private Button feedBackSendButton;
    private EditText feedEditText;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedBackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedBackFragment newInstance(String param1, String param2) {
        FeedBackFragment fragment = new FeedBackFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FeedBackFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //set my default dialog fragment style
        //setStyle(DialogFragment.STYLE_NORMAL,R.style.MY_DIALOG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.alpha(Color.BLUE)));

        rootView =  inflater.inflate(R.layout.fragment_feed_back_fragment, container, false);

        feedEditText = (EditText) rootView.findViewById(R.id.feedBackText);
                feedBackCancelButton = (Button) rootView.findViewById(R.id.feedBackCancelButton);

        feedBackSendButton = (Button) rootView.findViewById(R.id.feedBackSendButton);

        feedBackSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String feedbackText = feedEditText.getText().toString().trim();

                if (feedbackText.isEmpty()) {
                    feedEditText.setError("Feedback Required!");
                } else {
                    //dismiss dialog first
                    dismiss();
                    //start sending the mail using intent
                    Intent feedbackIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:hanksjerry_dedon@yahoo.com"));
                    feedbackIntent.putExtra(Intent.EXTRA_EMAIL, "jerryhanksokafor@gmail.com");
                    feedbackIntent.putExtra(Intent.EXTRA_BCC, "jerryhanksokafor@gmail.com");
                    feedbackIntent.putExtra(Intent.EXTRA_CC, "jerryhanksokafor@gmail.com");
                    feedbackIntent.putExtra(Intent.EXTRA_SUBJECT,"Feedback");
                    feedbackIntent.putExtra(Intent.EXTRA_TEXT, getFeedBackText(feedbackText));

                    startActivity(feedbackIntent);
                }
            }
        });

        feedBackCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });


        return rootView;
    }

    private String getFeedBackText(String feedbackText) {
        //just return feed back text for now
        //later we shall build a suitable feedback string
        //using feedback text
        return feedbackText;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
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
        public void onFragmentInteraction(Uri uri);

    }


}
