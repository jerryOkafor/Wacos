package com.dipoletech.wacos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.firebase.client.Firebase;

public class Auth extends AppCompatActivity implements
        SuretyIdCheck.OnFragmentInteractionListener,
        NewAccountFragment.OnFragmentInteractionListener,
        LoginFragment.OnFragmentInteractionListener,
        TermsOfService.OnFragmentInteractionListener{

    private static final String TAG_2 = "newAccountFrag";
    private static final String TAG_1 = "suretyIdCheckFrag";
    private static final String TAG_3 = "loginFrag";
    private static final String TAG_4 = "tofSFrag";
    private Firebase authRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //check if the user is log in,
        //present a login for if not
        // else tell them to register
//       beginNewAccountProcess();


                    toLogin();
    }

    private void startMainActivity() {
        Intent mIntent = new Intent(this,MainActivity.class);
        startActivity(mIntent);
    }

    private void beginNewAccountProcess() {
        getSupportActionBar().setTitle("Surety Id Check.");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SuretyIdCheck fragment = new SuretyIdCheck();
        transaction.replace(R.id.frags, fragment, TAG_1);
        transaction.commitAllowingStateLoss();
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
        getSupportActionBar().setTitle("User Login.::");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        LoginFragment fragment = new LoginFragment();
        transaction.replace(R.id.frags, fragment, TAG_3);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void suretyIdVerified(String sId) {
        getSupportActionBar().setTitle("New Account.::");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        NewAccountFragment fragment = NewAccountFragment.newInstance(sId,"test");
        transaction.replace(R.id.frags, fragment,TAG_2);
        transaction.commitAllowingStateLoss();
    }

}
