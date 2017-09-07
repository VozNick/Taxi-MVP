package com.example.vmm408.taxiuserproject.login.model;

public interface LoginModel {
    String userSignedInApp();

    void saveUser(String userId, String userPhotoUrl, String userFullName);
}
