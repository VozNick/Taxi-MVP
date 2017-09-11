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
    private UserModel userModel = getUserFromStatic();

    @Override
    public UserModel userSignedInApp() {
        return PreferenceUtils.getUserProfileFromShared(App.getAppBaseContext());
    }

    @Override
    public UserModel getUserFromStatic() {
        return SignedUser.getUserModel();
    }

    @Override
    public void saveUserToDataBase(String avatar,
                                   String fullName,
                                   String phone,
                                   String gender,
                                   String age) {
        userModel.setAvatarUser(avatar);
        userModel.setFullNameUser(fullName);
        userModel.setPhoneUser(phone);
        userModel.setGenderUser(gender);
        userModel.setAgeUser(age);
        saveUserToRemoteDataBase();
        saveUserToShared();
        SignedUser.setUserModel(userModel);
    }

    private void saveUserToRemoteDataBase() {
        DatabaseReference databaseReference = database.getReference(USERS_REF_KEY).child(userModel.getIdUser());
        databaseReference.setValue(getUserFromStatic());
    }

    private void saveUserToShared() {
        PreferenceUtils.saveUserProfileToShared(App.getAppBaseContext(), userModel);
    }
}
