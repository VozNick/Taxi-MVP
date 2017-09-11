package com.example.vmm408.taxiuserproject.profile.view;

public interface ProfileView {
    void fillDataToWidgets(String avatar, String fullName, String phone, String age);

    void setAvatar(int key);

    void setAge(String dateOfBirth);

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
