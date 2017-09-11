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
        UserModel model = getUserFromStatic();
        model.setAvatarUser(avatar);
        model.setFullNameUser(fullName);
        model.setPhoneUser(phone);
        model.setGenderUser(gender);
        model.setAgeUser(age);
        SignedUser.setUserModel(model);
        saveUserToRemoteDataBase(model.getIdUser());
        saveUserToShared();
    }

    private void saveUserToRemoteDataBase(String userId) {
        DatabaseReference reference = database.getReference(USERS_REF_KEY).child(userId);
        reference.setValue(getUserFromStatic());
    }

    private void saveUserToShared() {
        PreferenceUtils.saveUserProfileToShared(App.getAppBaseContext(), getUserFromStatic());
    }
}
