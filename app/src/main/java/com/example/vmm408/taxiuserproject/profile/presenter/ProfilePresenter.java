package com.example.vmm408.taxiuserproject.profile.presenter;

public interface ProfilePresenter {
    void onClickAvatar(int key);

    void validateAge(int dayOfMonth, int month, int year);

    void onClickSaveProfile();

    void onClickCancel();

    void onDestroy();
}
