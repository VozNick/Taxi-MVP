package com.example.vmm408.taxiuserproject.profile.view;

public interface ProfileView {
    boolean selfPermissionGranted();

    void requestPermissions();

    void showDataCreateProfile();

    void showDataInWidgets(String avatar, String fullName, String phone, String age);

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

    void navigateToSignInActivity();
}
