package com.dipoletech.wacos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.dipoletech.wacos.util.Constants;
import com.github.paolorotolo.appintro.AppIntro;

public class Intro extends AppIntro {

    private SharedPreferences settings;
    private boolean isFirstTime;

    @Override
    public void init(Bundle savedInstanceState) {
        settings = PreferenceManager.getDefaultSharedPreferences(this);

        isFirstTime = settings.getBoolean(Constants.isFisrtTime, false);


        if (!isFirstTime) {
            addSlide(new com.dipoletech.wacos.slides.Intro());
            addSlide(new com.dipoletech.wacos.slides.SecondIntro());
            addSlide(new com.dipoletech.wacos.slides.ThirdIntro());
        }else {
            loadAuthActivity();
        }

    }

    @Override
    public void onSkipPressed() {

        loadAuthActivity();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {

        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Constants.isFisrtTime,true);
        editor.commit();
        loadAuthActivity();
    }

    @Override
    public void onSlideChanged() {

    }

    public  void loadAuthActivity()
    {
        startActivity(new Intent(this,Auth.class));
        finish();
    }
}
