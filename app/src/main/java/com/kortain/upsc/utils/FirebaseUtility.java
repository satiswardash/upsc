package com.kortain.upsc.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by satiswardash on 19/12/17.
 */

public class FirebaseUtility {

    public final static int SIGNIN_SUCCESS_VERIFIED = 21;
    public final static int SIGNIN_SUCCESS_UNVERIFIED = 22;
    public final static int SIGNUP_SUCCESS = 11;

    private static FirebaseUtility instance;
    private FirebaseUtilityCallbacks mCallbacks;
    private Context mContext;

    private FirebaseUtility(Context context, FirebaseUtilityCallbacks callbacks) {
        this.mCallbacks = callbacks;
        this.mContext = context;
    }

    public static FirebaseUtility getInstance(Context context, FirebaseUtilityCallbacks callbacks) {
        if (instance == null) {
            return new FirebaseUtility(context, callbacks);
        }
        return instance;
    }

    public static FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    public static FirebaseUser getFirebaseUser(FirebaseAuth auth) {
        return auth.getCurrentUser();
    }

    /**
     * signInWithEmailAndPassword
     *
     * @param email
     * @param password
     * @param auth
     * @return
     */
    public void signInWithEmailAndPassword(String email, String password, FirebaseAuth auth) {

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            if (user.isEmailVerified()) {
                                mCallbacks.isSuccessful(user, 21);
                            } else {
                                mCallbacks.isSuccessful(user, 22);
                            }
                        } else {
                            mCallbacks.isFailure(task.getException().getMessage());
                        }
                    }
                });
    }

    /**
     * signUpWithEmailAndPassword
     *
     * @param email
     * @param password
     * @param auth
     */
    public void signUpWithEmailAndPassword(String email, String password, FirebaseAuth auth) {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            user.sendEmailVerification();
                            mCallbacks.isSuccessful(user, 11);
                        } else {
                            mCallbacks.isFailure(task.getException().getMessage());
                        }
                    }
                });
    }

    public interface FirebaseUtilityCallbacks {
        void isSuccessful(FirebaseUser firebaseUser, int flag);

        void isFailure(String message);
    }
}
