package com.example.vmm408.taxiuserproject.profile.presenter;

public interface ProfilePresenter {
    void onAvatarClick();

    void onAvatarMenuSelected(int key);

    void onActivityResult(int requestCode);

    void onAgeWidgetClick();

    void onDateSelected(int dayOfMonth, int month, int year);

    void onSaveProfileClick();

    void onCancelProfileClick();

    void onConfirmExitClick();

    void onBackPressed();

    void onDestroy();
}
