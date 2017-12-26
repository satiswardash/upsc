package com.kortain.upsc.helpers;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kortain.upsc.AuthenticationActivity;
import com.kortain.upsc.MainActivity;

/**
 * Created by satiswardash on 19/12/17.
 */

public class App extends Application {

    public static FirebaseUser user;

    @Override
    public void onCreate() {
        super.onCreate();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public static void authenticate(Context context){
        if (user == null){
            //TODO
            Intent intent = new Intent(context, AuthenticationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        else {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
