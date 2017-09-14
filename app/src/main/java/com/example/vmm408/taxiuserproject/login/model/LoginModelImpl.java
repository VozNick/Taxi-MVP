package com.example.vmm408.taxiuserproject.login.model;

import com.example.vmm408.taxiuserproject.App;
import com.example.vmm408.taxiuserproject.DatabaseValueEventListener;
import com.example.vmm408.taxiuserproject.login.presenter.UserProfileCallBack;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.example.vmm408.taxiuserproject.utils.PreferenceUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.vmm408.taxiuserproject.constants.FirebaseDataBaseKeys.USERS_REF_KEY;

public class LoginModelImpl implements LoginModel {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    public boolean userSignedInApp() {
        return PreferenceUtil.getUserProfileFromShared(App.getAppBaseContext()) != null;
    }

    @Override
    public void checkUserExist(String userId, UserProfileCallBack userProfileCallBack) {
        DatabaseReference reference = database.getReference(USERS_REF_KEY).child(userId);
        reference.addListenerForSingleValueEvent(new DatabaseValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userProfileCallBack.getUserProfile(dataSnapshot.getValue(UserModel.class));
            }
        });
    }

    @Override
    public void saveUser(UserModel model) {
        PreferenceUtil.saveUserProfileToShared(App.getAppBaseContext(), model);
    }
}

