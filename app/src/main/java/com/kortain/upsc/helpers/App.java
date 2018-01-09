package com.kortain.upsc.helpers;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kortain.upsc.AuthenticationActivity;
import com.kortain.upsc.MainActivity;
import com.kortain.upsc.firestore_models.User;
import com.kortain.upsc.utils.FirebaseUtility;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by satiswardash on 19/12/17.
 */

public class App extends Application {

    public static FirebaseUser user;
    public static User fsUser;

    @Override
    public void onCreate() {
        super.onCreate();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }
}
