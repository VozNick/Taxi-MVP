package com.example.vmm408.taxiuserproject.profile.model;

import com.example.vmm408.taxiuserproject.models.UserModel;

public interface ProfileModel {
    UserModel userSignedInApp();

    UserModel getUserFromStatic();

    void saveUserToDataBase(String avatar, String fullName, String phone, String gender, String age);
}
