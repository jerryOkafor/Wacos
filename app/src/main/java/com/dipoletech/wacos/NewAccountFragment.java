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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dipoletech.wacos.model.User;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;
import java.util.Random;


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
    private String uPlineSuretyId;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View rootView;
    private Button regBtn;
    private TextView tofs, agree,sHereTv;
    private EditText regNameTv,regEmailTv,regPassTv,regPass2Tv,regPhoneTv;
    public CheckBox termsCheckBox;
    private ProgressBar npBar;
    private Firebase regRef;

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
            uPlineSuretyId = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //get instance of the dame firebase
        regRef = new Firebase(Constants.appUrl+"/users");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_new_account, container, false);

        //get instance of the progress bar
        npBar = (ProgressBar) rootView.findViewById(R.id.npBar);
        regBtn = (Button) rootView.findViewById(R.id.register_button);
        //get all the text views
        regNameTv = (EditText) rootView.findViewById(R.id.reg_name);
        regEmailTv = (EditText) rootView.findViewById(R.id.reg_email);
        regPhoneTv = (EditText)rootView.findViewById(R.id.reg_phone);
        regPassTv = (EditText) rootView.findViewById(R.id.reg_password);
        regPass2Tv = (EditText) rootView.findViewById(R.id.reg_password2);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onRegisterButtonPressed();
                final String name = regNameTv.getText().toString().trim();
                final String email = regEmailTv.getText().toString().trim();
                String phone = regPhoneTv.getText().toString().trim();
                String password = regPassTv.getText().toString().trim();
                String password2 = regPass2Tv.getText().toString().trim();
                final String suretyId = String.valueOf(getSuretyId());

                if (name.isEmpty()||email.isEmpty()||phone.isEmpty()||password.isEmpty()||password2.isEmpty())
                {
                    //make a toast
                    Toast.makeText(getContext(),"You must fill all fields!",Toast.LENGTH_LONG).show();
                }
                else {
                    if (!password.equals(password2))
                    {
                        //make another toast
                        Toast.makeText(getContext(), "Passwords not equal!", Toast.LENGTH_LONG).show();
                    }
                    //show progress
                    showProgress();
                    //add a new user now
                    regRef.createUser(
                            email,
                            password,
                            new Firebase.ValueResultHandler<Map<String, Object>>(){
                                @Override
                                public void onSuccess(Map<String, Object> stringObjectMap) {
                                    //persist other user details
                                    Firebase userRef = regRef.child(regRef.getAuth().getUid());
                                    User user = new User();
                                    //set all the user values
                                    user.setUid(stringObjectMap.get("uid").toString());
                                    user.setName(name);
                                    user.setEmail(email);
                                    user.setJoinedDate(System.currentTimeMillis());
                                    user.setBirthDay(System.currentTimeMillis());
                                    user.setSuretyId(suretyId);
                                    user.setUpLineSuretyId(uPlineSuretyId);


                                    userRef.setValue(user);
                                    hideProgress();
                                    //make a toast with the error
                                    Toast.makeText(getContext(), "Account created successfully", Toast.LENGTH_LONG).show();
                                    mListener.toLogin();


                                }

                                @Override
                                public void onError(FirebaseError firebaseError) {
                                    hideProgress();
                                    //make a toast with the error
                                    Toast.makeText(getContext(), "Error: "+firebaseError.getMessage(), Toast.LENGTH_LONG).show();

                                }
                            }
                    );

                }

            }
        });


        //get the two text view for the agreement and terms of service
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

    private String getSuretyId() {

        final String number = "0123456789";
        final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final int N = number.length();
        final int M = alphabet.length();

        String s = "";

        Random r = new Random();

        for (int i = 0; i < 4; i++) {
             s+=(number.charAt(r.nextInt(N)));
            if (i==3)
            {
                for (int j=0;j<2;j++)
                {
                    s+= (alphabet.charAt(r.nextInt(M)));
                }
            }
        }
        return s;
    }


    //shows progress bar
    private void showProgress() {
        npBar.setVisibility(View.VISIBLE);
        regBtn.setAlpha(new Float(0.5));
        regBtn.setText("Creating Account...");
        regBtn.setAllCaps(false);
    }

    //hides progress bar

    private void hideProgress()
    {
        npBar.setVisibility(View.INVISIBLE);
        regBtn.setAlpha(new Float(1));
        regBtn.setText("Create Account");
        regBtn.setAllCaps(true);
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
