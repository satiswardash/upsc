package com.kortain.upsc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kortain.upsc.firestore_models.User;
import com.kortain.upsc.helpers.App;
import com.kortain.upsc.helpers.Constants;
import com.kortain.upsc.utils.FirebaseUtility;

import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    private Context mContext = SplashActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (App.user != null) {
            FirebaseFirestore.getInstance()
                    .collection(Constants.FIRESTORE_COLLECTIONS_USERS)
                    .document(App.user.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful() && task.getResult().exists()) {
                                Map<String, Object> data = task.getResult().getData();
                                App.fsUser = User.convertFromMap(data);

                                Intent intent = new Intent(mContext, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finishAffinity();
                            }
                        }
                    });
        } else {
            Intent intent = new Intent(mContext, AuthenticationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finishAffinity();
        }
    }
}
