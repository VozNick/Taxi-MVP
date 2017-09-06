package com.example.vmm408.taxiuserproject.signin.view;

public interface SignInView {
    void showProgress();

    void hideProgress();

    void onConnectionFailedListener();

    void onResultFailed();

    void navigateToProfileActivity(String userId, String userPhotoUrl, String userFullName);

    void navigateToMapActivity();
}
