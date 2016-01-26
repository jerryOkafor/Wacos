package com.dipoletech.wacos.slides;/**
 * Created by DABBY(3pleMinds) on 18-Jan-16.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dipoletech.wacos.R;

/**
 * DABBY(3pleMinds) 18-Jan-16 4:34 PM 2016 01
 * 18 16 34 Wacos
 **/
public class Intro extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.intro1,container,false);
    }
}
