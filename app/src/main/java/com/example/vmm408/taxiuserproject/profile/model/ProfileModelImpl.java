package com.example.vmm408.taxiuserproject.profile.model;

import com.example.vmm408.taxiuserproject.App;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.example.vmm408.taxiuserproject.utils.PreferenceUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.vmm408.taxiuserproject.models.UserModel.SignedUser;
import static com.example.vmm408.taxiuserproject.utils.keys.FirebaseDataBaseKeys.USERS_REF_KEY;

public class ProfileModelImpl implements ProfileModel {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private UserModel userModel = getUserProfile();

    @Override
    public boolean userSignedInApp() {
        return PreferenceUtils.getUserProfileFromShared(App.getAppBaseContext()) != null;
    }

    @Override
    public UserModel getUserProfile() {
        return SignedUser.getUserModel();
    }

    @Override
    public void saveUserProfile(String avatar,
                                String fullName,
                                String phone,
                                String gender,
                                String age) {
        userModel.setAvatarUser(avatar);
        userModel.setFullNameUser(fullName);
        userModel.setPhoneUser(phone);
        userModel.setGenderUser(gender);
        userModel.setAgeUser(age);
        saveUserToFirebase();
        saveUserToShared();
        SignedUser.setUserModel(userModel);
    }

    private void saveUserToFirebase() {
        DatabaseReference databaseReference = database.getReference(USERS_REF_KEY).child(userModel.getIdUser());
        databaseReference.setValue(getUserProfile());
    }

    private void saveUserToShared() {
        PreferenceUtils.saveUserProfileToShared(App.getAppBaseContext(), userModel);
    }
}
