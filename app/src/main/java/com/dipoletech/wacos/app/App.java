package com.dipoletech.wacos.app;/**
 * Created by DABBY(3pleMinds) on 26-Jan-16.
 */

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * DABBY(3pleMinds) 26-Jan-16 3:58 PM 2016 01
 * 26 15 58 Wacos
 **/
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //set up the fire base app
        Firebase.setAndroidContext(this);

    }
}
