package com.dipoletech.wacos;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class Auth extends AppCompatActivity implements
        SuretyIdCheck.OnFragmentInteractionListener,
        NewAccountFragment.OnFragmentInteractionListener,
        LoginFragment.OnFragmentInteractionListener,
        TermsOfService.OnFragmentInteractionListener{

    private static final String TAG_2 = "newAccountFrag";
    private static final String TAG_1 = "suretyIdCheckFrag";
    private static final String TAG_3 = "loginFrag";
    private static final String TAG_4 = "tofSFrag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       beginNewAccountProcess();
    }

    private void beginNewAccountProcess() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SuretyIdCheck fragment = new SuretyIdCheck();
        transaction.replace(R.id.frags, fragment, TAG_1);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void toRegister() {
        beginNewAccountProcess();
    }

    @Override
    public void tofsOkClicked() {
        NewAccountFragment fragment = (NewAccountFragment) getSupportFragmentManager().findFragmentByTag(TAG_2);
        if (!fragment.termsCheckBox.isChecked())
        fragment.termsCheckBox.setChecked(true);
    }

    @Override
    public void showTermsOfService() {
        TermsOfService termsOfService = new TermsOfService();
        termsOfService.show(getSupportFragmentManager(),TAG_4);
    }

    @Override
    public void toLogin() {
        registerBtnPressed();
    }

    @Override
    public void registerBtnPressed() {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        LoginFragment fragment = new LoginFragment();
        transaction.replace(R.id.frags,fragment,TAG_3);
        transaction.commit();
    }

    @Override
    public void continueButtonClicked(String sId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        NewAccountFragment fragment = new NewAccountFragment();
        transaction.replace(R.id.frags, fragment,TAG_2);
        transaction.commit();
    }
}
