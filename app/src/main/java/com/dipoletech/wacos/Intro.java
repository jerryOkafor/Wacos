package com.dipoletech.wacos;

import android.content.Intent;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;

public class Intro extends AppIntro {

    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(new com.dipoletech.wacos.slides.Intro());
        addSlide(new com.dipoletech.wacos.slides.SecondIntro());
        addSlide(new com.dipoletech.wacos.slides.ThirdIntro());

    }

    @Override
    public void onSkipPressed() {

        loadMainActivity();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {

        loadMainActivity();
    }

    @Override
    public void onSlideChanged() {

    }

    public  void loadMainActivity()
    {
        startActivity(new Intent(this,Auth.class));
        finish();
//        startActivity(new Intent(this, MainActivity.class));
    }
}
