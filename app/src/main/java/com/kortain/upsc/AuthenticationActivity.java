package com.kortain.upsc;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.kortain.upsc.fragments.AuthenticationFragment;
import com.kortain.upsc.fragments.NoNetworkAlertDialog;
import com.kortain.upsc.utils.FirebaseUtility;
import com.kortain.upsc.utils.NetworkUtility;

import static com.kortain.upsc.utils.FirebaseUtility.FirebaseUtilityCallbacks;

public class AuthenticationActivity extends AppCompatActivity implements FirebaseUtilityCallbacks, NoNetworkAlertDialog.NoNetworkDialogListeners {

    private static ActivityAction ACTION = ActivityAction.DEFAULT;
    private ConstraintLayout loadingScreen;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private DialogFragment dialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        init();

        if (loadingScreen.getVisibility() == View.VISIBLE) {
            loadingScreen.setVisibility(View.INVISIBLE);
        }

        setSupportActionBar(toolbar);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    void init() {
        loadingScreen = findViewById(R.id.loading);
        toolbar = findViewById(R.id.toolbar);
        mViewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabs);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
    }

    public void signIn(String email, String password) {

        ACTION = ActivityAction.SIGNIN;

        if (loadingScreen.getVisibility() == View.INVISIBLE) {
            loadingScreen.setVisibility(View.VISIBLE);
        }

        if (NetworkUtility.hasNetworkAccess(this)) {
            FirebaseUtility.getInstance(getApplicationContext(), this)
                    .signInWithEmailAndPassword(
                            email,
                            password,
                            FirebaseUtility.getFirebaseAuth());
        } else {
            showNoNetworkDialog();
        }
    }

    public void signUp(String email, String password) {

        ACTION = ActivityAction.SIGNUP;

        if (loadingScreen.getVisibility() == View.INVISIBLE) {
            loadingScreen.setVisibility(View.VISIBLE);
        }

        if (NetworkUtility.hasNetworkAccess(this)) {
            FirebaseUtility.getInstance(getApplicationContext(), this)
                    .signUpWithEmailAndPassword(
                            email,
                            password,
                            FirebaseUtility.getFirebaseAuth());
        } else {
            showNoNetworkDialog();
        }
    }

    //TODO
    @Override
    public void isSuccessful(FirebaseUser firebaseUser, int flag) {

        if (loadingScreen.getVisibility() == View.VISIBLE) {
            loadingScreen.setVisibility(View.INVISIBLE);
        }

        switch (flag) {

            case FirebaseUtility.SIGNIN_SUCCESS_UNVERIFIED: {
                Toast.makeText(getApplicationContext(), R.string.email_not_verified, Toast.LENGTH_SHORT).show();
                break;
            }
            case FirebaseUtility.SIGNIN_SUCCESS_VERIFIED: {
                App.user = firebaseUser;
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finishAffinity();
                break;
            }
            case FirebaseUtility.SIGNUP_SUCCESS: {
                Toast.makeText(getApplicationContext(), getString(R.string.account_created) + firebaseUser.getUid(), Toast.LENGTH_SHORT).show();
                break;
            }
        }

    }

    @Override
    public void isFailure(String message) {

        if (loadingScreen.getVisibility() == View.VISIBLE) {
            loadingScreen.setVisibility(View.INVISIBLE);
        }

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void retry(View view) {

        dialogFragment.getDialog().hide();

        if (loadingScreen.getVisibility() == View.INVISIBLE) {
            loadingScreen.setVisibility(View.VISIBLE);
        }

        switch (ACTION) {

            case SIGNIN: {

                EditText email = findViewById(R.id.al_signin_email_editText);
                EditText password = findViewById(R.id.al_signin_password_editText);

                if (email.getText().toString() != "" && password.getText().toString() != "") {

                    signIn(email.getText().toString(), password.getText().toString());
                }
                break;
            }
            case SIGNUP: {

                EditText email = findViewById(R.id.al_signup_email_editText);
                EditText password = findViewById(R.id.al_signup_password_editText);

                if (email.getText().toString() != "" && password.getText().toString() != "") {

                    signUp(email.getText().toString(), password.getText().toString());
                }
                break;
            }
        }

    }

    private void showNoNetworkDialog() {
        if (loadingScreen.getVisibility() == View.VISIBLE) {
            loadingScreen.setVisibility(View.INVISIBLE);
        }

        dialogFragment = new NoNetworkAlertDialog();
        dialogFragment.setCancelable(true);
        dialogFragment.show(getSupportFragmentManager(), "NO_NETWORK_DIALOG");

    }

    private enum ActivityAction {
        DEFAULT,
        SIGNIN,
        SIGNUP
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            AuthenticationFragment fragment = AuthenticationFragment.newInstance(position + 1);
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }


    }
}
