package com.example.vmm408.taxiuserproject.signin.model;
public interface SignInModel {
    void findUserInDataBase(String userId);

    void saveUserToSharedPreference(String userKey);
}
