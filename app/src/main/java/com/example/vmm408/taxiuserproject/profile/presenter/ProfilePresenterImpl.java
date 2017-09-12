package com.example.vmm408.taxiuserproject.profile.presenter;

import com.example.vmm408.taxiuserproject.models.UserModel;
import com.example.vmm408.taxiuserproject.profile.model.ProfileModel;
import com.example.vmm408.taxiuserproject.profile.view.ProfileView;
import com.example.vmm408.taxiuserproject.utils.keys.MyKeys;

public class ProfilePresenterImpl implements ProfilePresenter {
    private ProfileView profileView;
    private ProfileModel profileModel;

    public ProfilePresenterImpl(ProfileView profileView, ProfileModel profileModel) {
        this.profileView = profileView;
        this.profileModel = profileModel;
        showUserData();
    }

    private void showUserData() {
        UserModel model = profileModel.getUserProfile();
        profileView.showDataInWidgets(
                model.getAvatarUser(),
                model.getFullNameUser(),
                model.getPhoneUser(),
                model.getAgeUser());
    }

    @Override
    public void onClickAvatar() {
        profileView.showAvatarMenuDialog();
    }

    @Override
    public void onSelectedAvatarMenu(int key) {
        if (key == MyKeys.IMAGE_CAPTURE_KEY) {
            profileView.showAvatarFromCamera(key);
        } else if (key == MyKeys.PICK_PHOTO_KEY) {
            profileView.showAvatarFromGallery(key);
        } else if (key == MyKeys.DELETE_PHOTO_KEY) {
            profileView.showDefaultAvatar();
        }
    }

    @Override
    public void onClickAgeWidget() {
        profileView.showDatePickerDialog();
    }

    @Override
    public void onSelectedDate(int year, int month, int dayOfMonth) {
        profileView.showAge(dayOfMonth + "." + (month + 1) + "." + year);
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
        profileModel.saveUserProfile(
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
        if (profileModel.userSignedInApp()) {
            profileView.navigateToMapActivity();
        } else {
            profileView.navigateToSignInActivity();
        }
    }

    @Override
    public void onDestroy() {
        profileView = null;
        profileModel = null;
    }
}
