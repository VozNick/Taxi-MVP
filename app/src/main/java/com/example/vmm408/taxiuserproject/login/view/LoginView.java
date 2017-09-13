package com.example.vmm408.taxiuserproject.login.view;

public interface LoginView {
    void showLoading(boolean flag);

    void showConnectionErrorMessage();

    void showResultErrorMessage();

    void navigateToProfileActivity(String userId, String userFullName);

    void navigateToMapActivity();
}
