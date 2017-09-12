package com.example.vmm408.taxiuserproject.profile.presenter;

public interface ProfilePresenter {
    void onClickAvatar();

    void onSelectedAvatarMenu(int key);

    void onClickAgeWidget();

    void onSelectedDate(int dayOfMonth, int month, int year);

    void onClickSaveProfile();

    void onClickCancel();

    void onDestroy();
}
