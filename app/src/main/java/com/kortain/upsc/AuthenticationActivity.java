package com.kortain.upsc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kortain.upsc.fragments.AuthenticationFragment;
import com.kortain.upsc.fragments.NoNetworkAlertDialog;
import com.kortain.upsc.helpers.App;
import com.kortain.upsc.utils.FirebaseUtility;
import com.kortain.upsc.utils.NetworkUtility;

import java.util.Arrays;

import static com.kortain.upsc.utils.FirebaseUtility.FirebaseUtilityCallbacks;

public class AuthenticationActivity extends AppCompatActivity implements FirebaseUtilityCallbacks, NoNetworkAlertDialog.NoNetworkDialogListeners {

    private static final int RC_SIGN_IN = 420;
    public static ActivityAction ACTION = ActivityAction.DEFAULT;
    GoogleSignInAccount account;
    private ConstraintLayout loadingScreen;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private DialogFragment dialogFragment;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mFacebookCallbackManager;

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

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mFacebookCallbackManager = CallbackManager.Factory.create();
    }

    void init() {
        loadingScreen = findViewById(R.id.loading);
        toolbar = findViewById(R.id.toolbar);
        mViewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabs);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
    }

    public void facebookAuthentication(View view) {

        if (NetworkUtility.hasNetworkAccess(this)) {
            if (loadingScreen.getVisibility() == View.INVISIBLE) {
                loadingScreen.setVisibility(View.VISIBLE);
            }
            String[] permissions = {"email", "public_profile"};
            LoginManager loginManager = LoginManager.getInstance();

            loginManager.logInWithReadPermissions(this, Arrays.asList(permissions));
            LoginManager.getInstance().registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    if (loadingScreen.getVisibility() == View.VISIBLE) {
                        loadingScreen.setVisibility(View.INVISIBLE);
                    }
                    handleFacebookAccessToken(loginResult.getAccessToken());
                }

                @Override
                public void onCancel() {
                    if (loadingScreen.getVisibility() == View.VISIBLE) {
                        loadingScreen.setVisibility(View.INVISIBLE);
                    }
                    //TODO
                }

                @Override
                public void onError(FacebookException error) {
                    if (loadingScreen.getVisibility() == View.VISIBLE) {
                        loadingScreen.setVisibility(View.INVISIBLE);
                    }
                    //TODO
                }
            });
        } else {
            showNoNetworkDialog();
        }
    }

    public void googleAuthentication(View view) {

        if (NetworkUtility.hasNetworkAccess(this)) {
            if (loadingScreen.getVisibility() == View.INVISIBLE) {
                loadingScreen.setVisibility(View.VISIBLE);
            }
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        } else {
            showNoNetworkDialog();
        }

    }

    public void signIn(String email, String password) {
        if (NetworkUtility.hasNetworkAccess(this)) {
            if (loadingScreen.getVisibility() == View.INVISIBLE) {
                loadingScreen.setVisibility(View.VISIBLE);
            }
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

        if (NetworkUtility.hasNetworkAccess(this)) {
            if (loadingScreen.getVisibility() == View.INVISIBLE) {
                loadingScreen.setVisibility(View.VISIBLE);
            }
            FirebaseUtility.getInstance(getApplicationContext(), this)
                    .signUpWithEmailAndPassword(
                            email,
                            password,
                            FirebaseUtility.getFirebaseAuth());
        } else {
            showNoNetworkDialog();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (loadingScreen.getVisibility() == View.VISIBLE) {
            loadingScreen.setVisibility(View.INVISIBLE);
        }
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(this, R.string.update_playservice, Toast.LENGTH_SHORT).show();
            }
        } else {
            mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        final FirebaseAuth mAuth = FirebaseUtility.getFirebaseAuth();
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            switch (ACTION) {
                                case F_SIGNIN: {
                                    App.user = firebaseUser;
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finishAffinity();
                                    break;
                                }
                                case F_SIGNUP: {

                                    //TODO
                                    break;
                                }
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(AuthenticationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        final FirebaseAuth auth = FirebaseUtility.getFirebaseAuth();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            switch (ACTION) {
                                case G_SIGNIN: {
                                    App.user = firebaseUser;
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finishAffinity();
                                    break;
                                }
                                case G_SIGNUP: {

                                    //TODO
                                    break;
                                }
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(AuthenticationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

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
                //TODO
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

            case G_SIGNIN: {
                googleAuthentication(null);
                break;
            }

            case F_SIGNIN: {
                facebookAuthentication(null);
                break;
            }

            case G_SIGNUP: {
                //TODO
                break;
            }

            case F_SIGNUP: {
                //TODO
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

    public enum ActivityAction {
        DEFAULT,
        SIGNIN,
        SIGNUP,
        G_SIGNIN,
        G_SIGNUP,
        F_SIGNIN,
        F_SIGNUP
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
