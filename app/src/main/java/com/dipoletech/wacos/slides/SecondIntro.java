package com.dipoletech.wacos.slides;/**
 * Created by DABBY(3pleMinds) on 23-Jan-16.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dipoletech.wacos.R;

/**
 * DABBY(3pleMinds) 23-Jan-16 7:16 PM 2016 01
 * 23 19 16 Wacos
 **/
public class SecondIntro extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView  = inflater.inflate(R.layout.intro2,container,false);

        return rootView;
    }
}
