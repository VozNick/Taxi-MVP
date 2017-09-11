package com.example.vmm408.taxiuserproject.login.model;

import com.example.vmm408.taxiuserproject.models.UserModel;

public interface LoginModel {
    UserModel userSignedInApp();

    void saveUser(String userId, String userPhotoUrl, String userFullName);
}
