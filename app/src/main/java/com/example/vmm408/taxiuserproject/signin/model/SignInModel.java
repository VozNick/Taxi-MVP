package com.example.vmm408.taxiuserproject.signin.model;

public interface SignInModel {
    String userSignedInApp();

    void saveUser(String userId, String userPhotoUrl, String userFullName);
}
