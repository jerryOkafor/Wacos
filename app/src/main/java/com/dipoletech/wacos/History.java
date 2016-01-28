package com.dipoletech.wacos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dipoletech.wacos.adapaters.HistoryAdapter;
import com.dipoletech.wacos.model.HistoryItem;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link History.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link History#newInstance} factory method to
 * create an instance of this fragment.
 */
public class History extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int GRID_COUNT = 1;
    private static final String TAG = History.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View rootView;
    private RecyclerView recycler;
    private HistoryAdapter rAdapter;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private Firebase hRef;
    private Firebase myHref;

    //list of history
    private List<HistoryItem> histories;
    private Firebase nHRef;
    private ProgressBar hPBar;

    public History() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment History.
     */
    // TODO: Rename and change types and number of parameters
    public static History newInstance(String param1, String param2) {
        History fragment = new History();
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
        hRef = new Firebase(Constants.appUrl + "/history");
        myHref = hRef.child(hRef.getAuth().getUid());
        histories = new ArrayList<>();

        //add some history for now
//        int i = 0;
//        do {
//            nHRef = myHref.child(String.valueOf(System.currentTimeMillis()));
//            HistoryItem item = new HistoryItem();
//            item.setDate(System.currentTimeMillis());
//            item.setPurpose("donation");
//            item.setAmount("2000.00");
//            item.setMyId("my Id");
//
//            nHRef.setValue(item);
//
//            i++;
//        }while (i<6);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_history, container, false);


        //get hPBar
        hPBar = (ProgressBar) rootView.findViewById(R.id.hPBar);

        myHref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot history : dataSnapshot.getChildren()) {
//                    User user = users.getValue(User.class);
                    HistoryItem hItem = history.getValue(HistoryItem.class);
                    histories.add(hItem);
                }
                Log.v(TAG,histories.toString());
                rAdapter = new HistoryAdapter(getContext(), histories);
                recycler.setAdapter(rAdapter);
                hPBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //get the recycler
        recycler = (RecyclerView) rootView.findViewById(R.id.history_recycler);

        rAdapter = new HistoryAdapter(getContext(),histories);

        //get the Layout manager for thr Recycler view
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(GRID_COUNT, StaggeredGridLayoutManager.VERTICAL);
        recycler.setLayoutManager(mStaggeredLayoutManager);

        recycler.setAdapter(rAdapter);


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
