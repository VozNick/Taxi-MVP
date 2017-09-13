package com.example.vmm408.taxiuserproject.login.model;

import com.example.vmm408.taxiuserproject.App;
import com.example.vmm408.taxiuserproject.DatabaseValueEventListener;
import com.example.vmm408.taxiuserproject.login.presenter.UserExistCallBack;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.example.vmm408.taxiuserproject.utils.PreferenceUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.vmm408.taxiuserproject.utils.keys.FirebaseDataBaseKeys.USERS_REF_KEY;

public class LoginModelImpl implements LoginModel {
    private UserExistCallBack userExistCallBack;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ValueEventListener getUserFromBase = new DatabaseValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.hasChildren()) {
                userExistCallBack.userExist(true);
                saveUserToShared(dataSnapshot.getValue(UserModel.class));
            } else {
                userExistCallBack.userExist(false);
            }
        }
    };

    @Override
    public boolean userSignedInApp() {
        return PreferenceUtils.getUserProfileFromShared(App.getAppBaseContext()) != null;
    }

    @Override
    public void checkUserExist(UserExistCallBack userExistCallBack, String userId) {
        this.userExistCallBack = userExistCallBack;
        DatabaseReference reference = database.getReference(USERS_REF_KEY).child(userId);
        reference.addListenerForSingleValueEvent(getUserFromBase);
    }

    private void saveUserToShared(UserModel userModel) {
        PreferenceUtils.saveUserProfileToShared(App.getAppBaseContext(), userModel);
    }
}

