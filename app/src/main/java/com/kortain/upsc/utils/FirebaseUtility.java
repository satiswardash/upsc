package com.kortain.upsc.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.kortain.upsc.R;
import com.kortain.upsc.firestore_models.User;
import com.kortain.upsc.helpers.Constants;

import java.util.HashMap;
import java.util.Map;

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

    public static final FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    public static final FirebaseUser getFirebaseUser(FirebaseAuth auth) { return auth.getCurrentUser(); }

    public static final String getCurrentUserId(){ return FirebaseAuth.getInstance().getCurrentUser().getUid(); }

    public static final FirebaseFirestore getFirebaseFirestore() { return FirebaseFirestore.getInstance(); }

    /**
     * getUserFromFirestore
     *
     * @param id
     * @return DocumentSnapshot
     */
    public void getUserFromFirestore(String id){
        FirebaseFirestore.getInstance()
                .collection(Constants.FIRESTORE_COLLECTIONS_USERS)
                .document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snapshot = task.getResult();
                            mCallbacks.onSuccess(snapshot.getData(), 1);
                        }
                    }
                });

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
                                Map<String, Object> fsUser = new HashMap<>();
                                fsUser.put("uid", user.getUid());
                                mCallbacks.onSuccess(fsUser, SIGNIN_SUCCESS_VERIFIED);
                            } else {
                                mCallbacks.onSuccess(null, SIGNIN_SUCCESS_UNVERIFIED);
                            }
                        } else {
                            mCallbacks.onFailure(task.getException().getMessage());
                        }
                    }
                });
    }

    /**
     * signUpWithUserDto
     *
     * @param userDto
     */
    public void signUpWithEmailAndPassword(final Map<String, Object> userDto) {

        if (!userDto.containsKey(Constants.AUTH_USER_EMAIL)) {
            mCallbacks.onFailure(mContext.getString(R.string.NO_EMAIL_KEY));
            return;
        }
        if (!userDto.containsKey(Constants.AUTH_USER_PASSWORD)) {
            mCallbacks.onFailure(mContext.getString(R.string.NO_PASSWORD_KEY));
            return;
        }
        if (!userDto.containsKey(Constants.AUTH_USER_PHONE)) {
            mCallbacks.onFailure(mContext.getString(R.string.NO_PHONE_KEY));
            return;
        }
        if (!userDto.containsKey(Constants.AUTH_USER_DISPLAY_NAME)) {
            mCallbacks.onFailure(mContext.getString(R.string.NO_NAME_KEY));
            return;
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                (String) userDto.get(Constants.AUTH_USER_EMAIL), (String) userDto.get(Constants.AUTH_USER_PASSWORD))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            addUserToFireStore(user.getUid(), userDto, SetOptions.merge());
                        } else {
                            mCallbacks.onFailure(task.getException().getMessage());
                        }
                    }
                });

    }

    /**
     * addUserToFireStore
     *
     * @param uid
     * @param userDto
     * @param options
     */
    public void addUserToFireStore(final String uid, final Map<String, Object> userDto, final SetOptions options) {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(
                (String) userDto.get(Constants.AUTH_USER_EMAIL),
                (String) userDto.get(Constants.AUTH_USER_PASSWORD))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        final FirebaseUser user = task.getResult().getUser();

                        if (task.isSuccessful()) {
                            if (userDto.containsKey(Constants.AUTH_USER_PASSWORD)) {
                                userDto.remove(Constants.AUTH_USER_PASSWORD);
                            }

                            FirebaseFirestore.getInstance()
                                    .collection(Constants.FIRESTORE_COLLECTIONS_USERS)
                                    .document(uid)
                                    .set(userDto, options)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            userDto.put("uid", user.getUid());
                                            user.sendEmailVerification();
                                            auth.signOut();
                                            mCallbacks.onSuccess(userDto, SIGNUP_SUCCESS);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    mCallbacks.onFailure(e.getMessage());
                                }
                            });
                        }

                    }
                });


    }

    public interface FirebaseUtilityCallbacks {
        void onSuccess(Map<String, Object> user, int flag);
        void onFailure(String message);
    }
}
