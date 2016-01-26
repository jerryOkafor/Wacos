package com.dipoletech.wacos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewAccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewAccountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View rootView;
    private Button regBtn;
    private TextView tofs, agree,sHereTv;
    public CheckBox termsCheckBox;

    public NewAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewAccountFragment newInstance(String param1, String param2) {
        NewAccountFragment fragment = new NewAccountFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_new_account, container, false);

        regBtn = (Button) rootView.findViewById(R.id.register_button);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegisterButtonPressed();

            }
        });


        //get the two twxt view for the agreement and terms of service
        agree = (TextView) rootView.findViewById(R.id.agreement_tv);
        tofs = (TextView) rootView.findViewById(R.id.terms_of_serv_tv);

        //set an onclick listener to them all
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTermsOfService();
            }
        });

        tofs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTermsOfService();
            }
        });

        //get the terms of service check box
        termsCheckBox = (CheckBox)rootView.findViewById(R.id.checkBox);


        sHereTv = (TextView)rootView.findViewById(R.id.s_here_tv);
        sHereTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.toLogin();
            }
        });

        return rootView;

    }

    private void showTermsOfService() {
        mListener.showTermsOfService();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onRegisterButtonPressed() {
        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
            mListener.registerBtnPressed();
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
        void registerBtnPressed();

        void showTermsOfService();

        void toLogin();
    }
}
