package com.example.vmm408.taxiuserproject.signin.model;

import android.util.Log;

import com.example.vmm408.taxiuserproject.App;
import com.example.vmm408.taxiuserproject.eventbus.EventMessage;
import com.example.vmm408.taxiuserproject.DatabaseValueEventListener;
import com.example.vmm408.taxiuserproject.utils.UserSharedUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import static com.example.vmm408.taxiuserproject.utils.FirebaseDataBaseKeys.USERS_REF_KEY;

public class SignInModelImpl implements SignInModel {
    private ValueEventListener getUserFromBase = new DatabaseValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            Log.d("tag", "post");
            EventBus.getDefault().post(new EventMessage(dataSnapshot));
        }
    };

    @Override
    public void handleSignInResult(GoogleSignInAccount googleSignInAccount) {

        Log.d("tag", "6");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(USERS_REF_KEY);
        reference.child(googleSignInAccount.getId());
        reference.addListenerForSingleValueEvent(getUserFromBase);

        Log.d("tag", "handleSignModel");
    }

    @Override
    public void saveUserToSharedPreference(String userKey) {
        UserSharedUtils.saveUserToShared(new App().appBaseContext, userKey);
    }
}
