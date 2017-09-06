package com.example.vmm408.taxiuserproject.signin.model;

import com.example.vmm408.taxiuserproject.App;
import com.example.vmm408.taxiuserproject.eventbus.EventUserFromBase;
import com.example.vmm408.taxiuserproject.DatabaseValueEventListener;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.example.vmm408.taxiuserproject.utils.UserSharedUtils;
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
            if (dataSnapshot.hasChildren()) {
                EventBus.getDefault().post(new EventUserFromBase(dataSnapshot.getValue(UserModel.class)));
            }
        }
    };

    @Override
    public void findUserInDataBase(String userId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(USERS_REF_KEY);
        reference.child(userId);
        reference.addListenerForSingleValueEvent(getUserFromBase);
    }

    @Override
    public void saveUserToSharedPreference(String userKey) {
        UserSharedUtils.saveUserToShared(new App().appBaseContext, userKey);
    }
}
