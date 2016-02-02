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

import com.dipoletech.wacos.util.Constants;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = LoginFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View rootView;
    private Button loginBtn;
    private TextView hereTv, emailTv,passTv;
    private ProgressBar loginPBar;
    private Firebase loginRef;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        loginRef = new Firebase(Constants.appUrl);


    }

    private void startMainActivity() {
        if (mListener != null) {
            mListener.startMainActivity();
        }else {
//            Intent mIntent = new Intent(getContext(),MainActivity.class);
//            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            startActivity(mIntent);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        loginRef.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData!=null)
                {
                    startMainActivity();
                }
            }
        });

       // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_login, container, false);

        loginPBar = (ProgressBar) rootView.findViewById(R.id.loginPBar);

        //get the email and password

        emailTv = (TextView) rootView.findViewById(R.id.email);
        passTv = (TextView) rootView.findViewById(R.id.password);

        loginBtn = (Button) rootView.findViewById(R.id.login_button);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), MainActivity.class));
                //here i shall get the email and password and attempt to login the
                //user

                String email = emailTv.getText().toString().trim();
                String password = passTv.getText().toString().trim();

                if (email.isEmpty()||password.isEmpty())
                {
                    //do nothing
                    //just post error
                    Toast.makeText(getContext(),"You must fill the email and password fields",Toast.LENGTH_LONG).show();
                }else {
                    //start showing the p bar and ling text
                    showProgress();
                    //try to do the login

                    loginRef.authWithPassword(
                            email,
                            password,
                            new Firebase.AuthResultHandler() {

                                @Override
                                public void onAuthenticated(AuthData authData) {

                                    Toast.makeText(getContext(), "login Successful!", Toast.LENGTH_LONG).show();
                                    hideProgress();
                                    startMainActivity();
                                    getActivity().finish();
                                }

                                @Override
                                public void onAuthenticationError(FirebaseError firebaseError) {
                                    //log the error
                                    Log.v(TAG,firebaseError.toString());
                                    Toast.makeText(getContext(), "Login Error: "+ firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                                    hideProgress();

                                }
                            }
                    );

                }


            }
        });

        hereTv = (TextView) rootView.findViewById(R.id.here_tv);
        hereTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.toRegister();
            }
        });


        return rootView;

    }



    private void hideProgress() {
        loginPBar.setVisibility(View.INVISIBLE);
        loginBtn.setAlpha(new Float(1));
        loginBtn.setText("Login");
        loginBtn.setAllCaps(true);
    }

    private void showProgress() {
        loginPBar.setVisibility(View.VISIBLE);
        loginBtn.setAlpha(new Float(0.4));
        loginBtn.setAllCaps(false);
        loginBtn.setText("Authenticating....");
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

        void toRegister();

        void startMainActivity();
    }
}
