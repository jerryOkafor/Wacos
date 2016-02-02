package com.dipoletech.wacos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Contributions.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Contributions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Contributions extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View rootView;
    private Button payBtn;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private TextView amountTv;

    public Contributions() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contributions.
     */
    // TODO: Rename and change types and number of parameters
    public static Contributions newInstance(String param1, String param2) {
        Contributions fragment = new Contributions();
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
        rootView =  inflater.inflate(R.layout.fragment_contributions, container, false);

        payBtn = (Button) rootView.findViewById(R.id.pay_bnt);

        //get the payment amount
        amountTv = (TextView) rootView.findViewById(R.id.contribution_amount);


        spinner = (Spinner)rootView.findViewById(R.id.spinner);

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ensure that the user selects required options
                if (spinner.getSelectedItemPosition()==0)
                {
                    //wrong selection
                    Toast.makeText(getContext(),"You must choose payment type",Toast.LENGTH_LONG).show();

                }else if (amountTv.getText().toString().isEmpty()){
                    //wrong input value
                    Toast.makeText(getContext(), "You must enter amount", Toast.LENGTH_LONG).show();

                }else {

                    //good selection so continue
                    //get what ever thing the spinner holds for us
                    String payFor = spinner.getSelectedItem().toString().trim();

                    String amount = amountTv.getText().toString().trim();
                    //launch the inter-switch APi passing payFOr as a parameter
                }

            }
        });


        spinnerAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.payment_choice,
                R.layout.support_simple_spinner_dropdown_item
                );

        //specify list of layout to use when the list of resources appear in the spinner
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        //tell the spinner about its adapter
        spinner.setAdapter(spinnerAdapter);
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
}
