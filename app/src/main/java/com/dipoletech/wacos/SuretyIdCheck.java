package com.dipoletech.wacos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dipoletech.wacos.model.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SuretyIdCheck.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SuretyIdCheck#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuretyIdCheck extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = SuretyIdCheck.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View rootView;
    private TextView suretyIdTv;
    private Button contunueBtn;
    private ProgressBar spBar;
    private Firebase rootRef;
    private Firebase sCheckRef;

    public SuretyIdCheck() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SuretyIdCheck.
     */
    // TODO: Rename and change types and number of parameters
    public static SuretyIdCheck newInstance(String param1, String param2) {
        SuretyIdCheck fragment = new SuretyIdCheck();
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

        //get the reference to the firebase
        rootRef = new Firebase(Constants.appUrl);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_surety_id_check, container, false);

        //get instance of the progress bar
        spBar = (ProgressBar) rootView.findViewById(R.id.sPBar);


        suretyIdTv = (TextView) rootView.findViewById(R.id.surety_id_tv);

        contunueBtn = (Button) rootView.findViewById(R.id.surety_continue_btn);

        contunueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String suretyId = suretyIdTv.getText().toString().trim();
                //i shall check if the surety id exists

                showProgress();

                //add a single event listener
                sCheckRef = rootRef.child("users");
                 Query qRef = sCheckRef.orderByChild("suretyId");

                qRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Log.v(TAG, dataSnapshot.getValue().toString());
                        for (DataSnapshot users : dataSnapshot.getChildren()) {
                            User user = users.getValue(User.class);
                            //check if the user matches the giving surety id
                            if (user.getSuretyId().equals(suretyId)) {
                                Toast.makeText(getContext(), "Surety Id Check passed.", Toast.LENGTH_LONG).show();
                                onVerifiedSuretyId(suretyId);
                            }
                        }
                        hideProgress();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        //make a toast
                        Toast.makeText(getContext(), "Error: " + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });



            }
        });





        return rootView;
    }

    private void showProgress() {
        spBar.setVisibility(View.VISIBLE);
        contunueBtn.setText("Verifying surety id.");
        contunueBtn.setAllCaps(false);
        contunueBtn.setAlpha(new Float(0.5));
    }

    //hides the progress bar
    private void hideProgress() {
        spBar.setVisibility(View.INVISIBLE);
        contunueBtn.setText("Continue");
        contunueBtn.setAllCaps(true);
        contunueBtn.setAlpha(new Float(1));
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onVerifiedSuretyId(String sId) {
        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
            mListener.suretyIdVerified(sId);


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
        void suretyIdVerified(String sId);
    }
}
