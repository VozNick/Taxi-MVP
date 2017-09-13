package com.example.vmm408.taxiuserproject.profile.model;

import com.example.vmm408.taxiuserproject.App;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.example.vmm408.taxiuserproject.utils.PreferenceUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import static com.example.vmm408.taxiuserproject.utils.keys.FirebaseDataBaseKeys.USERS_REF_KEY;

public class ProfileModelImpl implements ProfileModel {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    public UserModel getUserProfile() {
        return PreferenceUtils.getUserProfileFromShared(App.getAppBaseContext());
    }

    @Override
    public void saveUserProfile(UserModel userModel) {
        saveUserToShared(userModel);
        saveUserToFirebase(userModel);
    }

    private void saveUserToFirebase(UserModel userModel) {
        DatabaseReference reference = database.getReference(USERS_REF_KEY).child(userModel.getIdUser());
        reference.setValue(userModel);
    }

    private void saveUserToShared(UserModel userModel) {
        PreferenceUtils.saveUserProfileToShared(App.getAppBaseContext(), userModel);
    }
}
