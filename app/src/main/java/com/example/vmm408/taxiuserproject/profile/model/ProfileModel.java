package com.example.vmm408.taxiuserproject.profile.model;

import com.example.vmm408.taxiuserproject.models.UserModel;

public interface ProfileModel {
    boolean userSignedInApp();

    UserModel getUserProfile();

    void saveUserProfile(String avatar, String fullName, String phone, String gender, String age);
}
