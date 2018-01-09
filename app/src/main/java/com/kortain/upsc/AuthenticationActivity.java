package com.kortain.upsc;

import android.content.Context;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.kortain.upsc.firestore_models.User;
import com.kortain.upsc.fragments.AuthenticationFragment;
import com.kortain.upsc.fragments.NoNetworkAlertDialog;
import com.kortain.upsc.helpers.App;
import com.kortain.upsc.helpers.Constants;
import com.kortain.upsc.utils.FirebaseUtility;
import com.kortain.upsc.utils.NetworkUtility;
import com.kortain.upsc.utils.StringUtility;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.kortain.upsc.utils.FirebaseUtility.FirebaseUtilityCallbacks;

public class AuthenticationActivity extends AppCompatActivity implements FirebaseUtilityCallbacks, NoNetworkAlertDialog.NoNetworkDialogListeners {

    public static ActivityAction ACTION = ActivityAction.DEFAULT;
    private final int RC_SIGN_IN = 420;
    private final String[] permissions = {"email", "public_profile"};
    private final Context mContext = AuthenticationActivity.this;
    private ConstraintLayout loadingScreen;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private DialogFragment dialogFragment;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mFacebookCallbackManager;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        init();
        setLoadingScreen(false);
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

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

    /**
     * @param user
     * @param flag
     */
    @Override
    public void onSuccess(Map<String, Object> user, int flag) {

        switch (flag) {

            case FirebaseUtility.SIGNIN_SUCCESS_UNVERIFIED: {
                setLoadingScreen(false);
                Toast.makeText(getApplicationContext(), R.string.email_not_verified, Toast.LENGTH_SHORT).show();
                break;
            }
            case FirebaseUtility.SIGNIN_SUCCESS_VERIFIED: {
                handleOnSuccessVerifiedSignin();
                break;
            }
            case FirebaseUtility.SIGNUP_SUCCESS: {
                setLoadingScreen(false);
                Toast.makeText(this,
                        R.string.ACCOUNT_CREATED_SUCCESS, Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    /**
     * @param message
     */
    @Override
    public void onFailure(String message) {
        setLoadingScreen(false);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * @param view
     */
    @Override
    public void retry(View view) {
        setLoadingScreen(true);
        switch (ACTION) {

            case SIGNIN: {

                EditText email = findViewById(R.id.al_signin_email_editText);
                EditText password = findViewById(R.id.al_signin_password_editText);

                if (StringUtility.validate(email.getText().toString()) && StringUtility.validate(password.getText().toString())) {
                    signIn(email.getText().toString(), password.getText().toString());
                }
                break;
            }
            case SIGNUP: {

                EditText signUpEmailId = findViewById(R.id.al_signup_email_editText);
                EditText signUpPassword = findViewById(R.id.al_signup_password_editText);
                EditText signupName = findViewById(R.id.al_signup_name_editText);
                EditText signupPhone = findViewById(R.id.al_signup_phone_editText);

                if (StringUtility.validate(signUpEmailId.getText().toString()) && StringUtility.validate(signUpPassword.getText().toString())) {
                    Map<String, Object> userDto = new HashMap<>();
                    userDto.put(Constants.AUTH_USER_DISPLAY_NAME, signupName.getText().toString());
                    userDto.put(Constants.AUTH_USER_PHONE, signupPhone.getText().toString());
                    userDto.put(Constants.AUTH_USER_EMAIL, signUpEmailId.getText().toString());
                    userDto.put(Constants.AUTH_USER_PASSWORD, signUpPassword.getText().toString());
                    signUp(userDto);
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
                googleAuthentication(null);
                break;
            }

            case F_SIGNUP: {
                facebookAuthentication(null);
                break;
            }
        }

    }

    /**
     * @param view
     */
    public void facebookAuthentication(View view) {

        if (NetworkUtility.hasNetworkAccess(this)) {
            setLoadingScreen(true);
            LoginManager loginManager = LoginManager.getInstance();

            loginManager.logInWithReadPermissions(this, Arrays.asList(permissions));
            LoginManager.getInstance().registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    setLoadingScreen(false);
                    handleFacebookAccessToken(loginResult.getAccessToken());
                }

                @Override
                public void onCancel() {
                    setLoadingScreen(false);
                }

                @Override
                public void onError(FacebookException error) {
                    setLoadingScreen(false);
                    Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            showNoNetworkDialog();
        }
    }

    /**
     * @param view
     */
    public void googleAuthentication(View view) {

        if (NetworkUtility.hasNetworkAccess(this)) {
            setLoadingScreen(true);
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        } else {
            showNoNetworkDialog();
        }

    }

    /**
     * @param email
     * @param password
     */
    public void signIn(String email, String password) {
        if (NetworkUtility.hasNetworkAccess(this)) {
            setLoadingScreen(true);
            FirebaseUtility.getInstance(getApplicationContext(), this)
                    .signInWithEmailAndPassword(
                            email,
                            password,
                            FirebaseUtility.getFirebaseAuth());
        } else {
            showNoNetworkDialog();
        }
    }

    /**
     * @param userDto
     */
    public void signUp(Map<String, Object> userDto) {
        if (NetworkUtility.hasNetworkAccess(this)) {
            setLoadingScreen(true);
            FirebaseUtility.getInstance(getApplicationContext(), this)
                    .signUpWithEmailAndPassword(
                            userDto);
        } else {
            showNoNetworkDialog();
        }
    }

    /**
     *
     */
    private void init() {
        loadingScreen = findViewById(R.id.loading);
        toolbar = findViewById(R.id.toolbar);
        mViewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabs);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
    }

    /**
     * @param isVisible
     */
    private void setLoadingScreen(boolean isVisible) {
        if (isVisible) {
            if (loadingScreen.getVisibility() == View.INVISIBLE) {
                loadingScreen.setVisibility(View.VISIBLE);
            }
        } else {
            if (loadingScreen.getVisibility() == View.VISIBLE) {
                loadingScreen.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     *
     */
    private void handleOnSuccessVerifiedSignin() {
        if (App.fsUser == null) {
            FirebaseUtility.getInstance(mContext, new FirebaseUtilityCallbacks() {
                @Override
                public void onSuccess(Map<String, Object> user, int flag) {
                    if (user != null) {
                        App.fsUser = User.convertFromMap(user);
                        setLoadingScreen(false);
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                }
                @Override
                public void onFailure(String message) {
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                }
            }).getUserFromFirestore(FirebaseUtility.getCurrentUserId());
        }
    }

    /**
     * @param token
     */
    private void handleFacebookAccessToken(AccessToken token) {
        final FirebaseAuth mAuth = FirebaseUtility.getFirebaseAuth();
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            final FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            switch (ACTION) {
                                case F_SIGNIN: {
                                    FirebaseFirestore.getInstance()
                                            .collection(Constants.FIRESTORE_COLLECTIONS_USERS)
                                            .document(firebaseUser.getUid())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.getResult().exists()) {
                                                        setLoadingScreen(false);
                                                        App.user = firebaseUser;
                                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                        startActivity(intent);
                                                        finishAffinity();
                                                    } else {
                                                        setLoadingScreen(false);
                                                        firebaseUser.delete();
                                                        Toast.makeText(AuthenticationActivity.this, R.string.FAILED_FETCHING_ACCOUNT, Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    setLoadingScreen(false);
                                                    firebaseUser.delete();
                                                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                    break;
                                }
                                case F_SIGNUP: {
                                    Map<String, Object> user = new HashMap<>();
                                    if (firebaseUser.getDisplayName() != null) {
                                        user.put(Constants.AUTH_USER_DISPLAY_NAME, firebaseUser.getDisplayName());
                                    }
                                    if (firebaseUser.getEmail() != null) {
                                        user.put(Constants.AUTH_USER_EMAIL, firebaseUser.getEmail());
                                    }
                                    if (firebaseUser.getPhoneNumber() != null) {
                                        user.put(Constants.AUTH_USER_PHONE, firebaseUser.getPhoneNumber());
                                    }
                                    if (firebaseUser.getPhotoUrl() != null) {
                                        user.put(Constants.AUTH_USER_PICTURE, firebaseUser.getPhotoUrl().getPath());
                                    }
                                    FirebaseFirestore.getInstance()
                                            .collection(Constants.FIRESTORE_COLLECTIONS_USERS)
                                            .document(firebaseUser.getUid())
                                            .set(user, SetOptions.merge())
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        setLoadingScreen(false);
                                                        App.user = firebaseUser;
                                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                        startActivity(intent);
                                                        finishAffinity();
                                                    } else {
                                                        setLoadingScreen(false);
                                                        firebaseUser.delete();
                                                        Toast.makeText(AuthenticationActivity.this, R.string.FAILED_FETCHING_ACCOUNT, Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            setLoadingScreen(false);
                                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
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

    /**
     * @param acct
     */
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        final FirebaseAuth auth = FirebaseUtility.getFirebaseAuth();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            final FirebaseUser firebaseUser = auth.getCurrentUser();
                            switch (ACTION) {
                                case G_SIGNIN: {
                                    FirebaseFirestore.getInstance()
                                            .collection(Constants.FIRESTORE_COLLECTIONS_USERS)
                                            .document(firebaseUser.getUid()).get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.getResult().exists()) {
                                                        setLoadingScreen(false);
                                                        App.user = firebaseUser;
                                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                        startActivity(intent);
                                                        finishAffinity();
                                                    } else {
                                                        setLoadingScreen(false);
                                                        firebaseUser.delete();
                                                        Toast.makeText(AuthenticationActivity.this, R.string.FAILED_FETCHING_ACCOUNT, Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            firebaseUser.delete();
                                            setLoadingScreen(false);
                                            Toast.makeText(AuthenticationActivity.this, R.string.FAILED_FETCHING_ACCOUNT, Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    break;
                                }
                                case G_SIGNUP: {
                                    Map<String, Object> user = new HashMap<>();
                                    if (firebaseUser.getDisplayName() != null) {
                                        user.put(Constants.AUTH_USER_DISPLAY_NAME, firebaseUser.getDisplayName());
                                    }
                                    if (firebaseUser.getEmail() != null) {
                                        user.put(Constants.AUTH_USER_EMAIL, firebaseUser.getEmail());
                                    }
                                    if (firebaseUser.getPhoneNumber() != null) {
                                        user.put(Constants.AUTH_USER_PHONE, firebaseUser.getPhoneNumber());
                                    }
                                    if (firebaseUser.getPhotoUrl() != null) {
                                        user.put(Constants.AUTH_USER_PICTURE, firebaseUser.getPhotoUrl().getPath());
                                    }

                                    FirebaseFirestore.getInstance()
                                            .collection(Constants.FIRESTORE_COLLECTIONS_USERS)
                                            .document(firebaseUser.getUid())
                                            .set(user, SetOptions.merge())
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        setLoadingScreen(false);
                                                        App.user = firebaseUser;
                                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                        startActivity(intent);
                                                        finishAffinity();
                                                    } else {
                                                        setLoadingScreen(false);
                                                        firebaseUser.delete();
                                                        Toast.makeText(AuthenticationActivity.this, R.string.FAILED_FETCHING_ACCOUNT, Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    setLoadingScreen(false);
                                                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                    });
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

    /**
     *
     */
    private void showNoNetworkDialog() {
        setLoadingScreen(false);
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
