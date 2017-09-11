package com.example.vmm408.taxiuserproject.profile.presenter;

import com.example.vmm408.taxiuserproject.models.UserModel;
import com.example.vmm408.taxiuserproject.profile.model.ProfileModel;
import com.example.vmm408.taxiuserproject.profile.view.ProfileView;

public class ProfilePresenterImpl implements ProfilePresenter {
    private ProfileView profileView;
    private ProfileModel profileModel;

    public ProfilePresenterImpl(ProfileView profileView, ProfileModel profileModel) {
        this.profileView = profileView;
        this.profileModel = profileModel;
        showUserData();
    }

    private void showUserData() {
        UserModel model = profileModel.getUserFromStatic();
        profileView.fillDataToWidgets(
                model.getAvatarUser(),
                model.getFullNameUser(),
                model.getPhoneUser(),
                model.getAgeUser());
    }

    @Override
    public void onClickAvatar(int key) {
        profileView.setAvatar(key);
    }

    @Override
    public void validateAge(int dayOfMonth, int month, int year) {
        profileView.setAge(dayOfMonth + "." + (month + 1) + "." + year);
    }

    @Override
    public void onClickSaveProfile() {
        if (profileView.getFullName().isEmpty() || profileView.getFullName().trim().length() == 0) {
            profileView.showEmptyFieldsError();
            return;
        }
        if (!profileView.getPhone().matches("\\d{10}")) {
            profileView.showWrongPhoneError();
            return;
        }
        profileModel.saveUserToDataBase(
                profileView.getAvatar(),
                profileView.getFullName(),
                profileView.getPhone(),
                profileView.getGender(),
                profileView.getAge()
        );
        profileView.navigateToMapActivity();

    }

    @Override
    public void onClickCancel() {
        if (profileModel.userSignedInApp() == null) {
            profileView.navigateToSignInActivity();
        } else {
            profileView.navigateToMapActivity();
        }
    }

    @Override
    public void onDestroy() {
        profileView = null;
        profileModel = null;
    }
}
