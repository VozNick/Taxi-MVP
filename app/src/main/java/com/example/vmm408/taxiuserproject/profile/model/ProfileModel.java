package com.example.vmm408.taxiuserproject.profile.model;

import com.example.vmm408.taxiuserproject.models.UserModel;

public interface ProfileModel {
    UserModel getUserProfile();

    void saveUserProfile(UserModel userModel);
}
