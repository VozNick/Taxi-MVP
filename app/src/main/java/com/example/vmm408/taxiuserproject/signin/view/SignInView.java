package com.example.vmm408.taxiuserproject.signin.view;

public interface SignInView {
    void initProgressDialog();

    void showProgress();

    void hideProgress();

    void createGoogleApiClient();

    void onResultFailed();

    void startActivityForResult();

    String getUserId();

    String getUserPhotoUrl();

    String getUserFullName();

    void navigateToProfileActivity();

    void navigateToMapActivity();
}
