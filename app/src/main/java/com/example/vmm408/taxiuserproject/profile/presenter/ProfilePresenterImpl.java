package com.example.vmm408.taxiuserproject.profile.presenter;

import com.example.vmm408.taxiuserproject.constants.MyKeys;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.example.vmm408.taxiuserproject.profile.model.ProfileModel;
import com.example.vmm408.taxiuserproject.profile.view.ProfileView;
import com.example.vmm408.taxiuserproject.utils.ValidationUtil;

public class ProfilePresenterImpl implements ProfilePresenter {
    private ProfileView profileView;
    private ProfileModel profileModel;
    private UserModel userModel;

    public ProfilePresenterImpl(ProfileView profileView, ProfileModel profileModel) {
        this.profileView = profileView;
        this.profileModel = profileModel;
        if (!profileView.selfPermissionGranted()) {
            profileView.requestPermissions();
        }
        showUserData();
    }

    private void showUserData() {
        userModel = profileModel.getUserProfile();
        if (userModel == null) {
            userModel = new UserModel();
            profileView.showDataCreateProfile();
        } else {
            profileView.showDataInWidgets(
                    userModel.getAvatarUser(),
                    userModel.getFullNameUser(),
                    userModel.getPhoneUser(),
                    userModel.getAgeUser());
        }
    }

    @Override
    public void onClickAvatar() {
        profileView.showAvatarMenuDialog();
    }

    @Override
    public void onSelectedAvatarMenu(int key) {
        if (key == MyKeys.IMAGE_CAPTURE_KEY) {
            profileView.getAvatarFromCamera(key);
        } else if (key == MyKeys.PICK_PHOTO_KEY) {
            profileView.getAvatarFromGallery(key);
        } else if (key == MyKeys.DELETE_PHOTO_KEY) {
            profileView.showDefaultAvatar();
        }
    }

    @Override
    public void onActivityResult(int requestCode) {
        if (requestCode == MyKeys.IMAGE_CAPTURE_KEY) {
            profileView.showAvatarFromCamera();
        } else if (requestCode == MyKeys.PICK_PHOTO_KEY) {
            profileView.showAvatarFromGallery();
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
        if (ValidationUtil.isNullOrEmpty(profileView.getFullName())) {
            profileView.showEmptyFieldsError();
            return;
        }
        if (ValidationUtil.isPhoneWrong(profileView.getPhone())) {
            profileView.showWrongPhoneError();
            return;
        }
        getDataFromWidgets();
        profileModel.saveUserProfile(userModel);
        profileView.navigateToMapActivity();
    }

    private void getDataFromWidgets() {
        userModel.setIdUser(profileView.getUserId());
        userModel.setAvatarUser(profileView.getAvatar());
        userModel.setFullNameUser(profileView.getFullName());
        userModel.setPhoneUser(profileView.getPhone());
        userModel.setGenderUser(profileView.getGender());
        userModel.setAgeUser(profileView.getAge());
    }

    @Override
    public void onClickCancel() {
        if (userModel == null) {
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
