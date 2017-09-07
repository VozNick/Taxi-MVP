package com.example.vmm408.taxiuserproject.signin.view;

public interface SignInView {

    void showLoading(boolean flag);

    void onConnectionFailed();

    void onResultFailed();

    void navigateToProfileActivity();

    void navigateToMapActivity();
}
