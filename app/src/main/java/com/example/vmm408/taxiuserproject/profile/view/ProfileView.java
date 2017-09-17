package com.example.vmm408.taxiuserproject.profile.view;

import com.example.vmm408.taxiuserproject.models.UserModel;

public interface ProfileView {
    boolean selfPermissionGranted();

    void requestPermissions();

    void showDataToCreateProfile();

    void showDataToEditProfile(UserModel userModel);

    void showAvatarMenuDialog();

    void getAvatarFromCamera(int key);

    void getAvatarFromGallery(int key);

    void showDefaultAvatar();

    void showAvatarFromCamera();

    void showAvatarFromGallery();

    void showDatePickerDialog();

    void showAge(String dateOfBirth);

    String getUserId();

    String getAvatar();

    String getFullName();

    String getPhone();

    String getGender();

    String getAge();

    void navigateToMapActivity();

    void showEmptyFieldsError();

    void showWrongPhoneError();

    void showConfirmExitDialog();

    void closeApp();
}
