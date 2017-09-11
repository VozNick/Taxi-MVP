package com.example.vmm408.taxiuserproject.login.model;

import com.example.vmm408.taxiuserproject.App;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.example.vmm408.taxiuserproject.utils.PreferenceUtils;

import static com.example.vmm408.taxiuserproject.models.UserModel.SignedUser;

public class LoginModelImpl implements LoginModel {
    @Override
    public UserModel userSignedInApp() {
        return PreferenceUtils.getUserProfileFromShared(App.getAppBaseContext());
    }

    @Override
    public void saveUser(String userId, String userPhotoUrl, String userFullName) {
        UserModel model = new UserModel();
        model.setIdUser(userId);
        model.setAvatarUser(userPhotoUrl);
        model.setFullNameUser(userFullName);
        SignedUser.setUserModel(model);
    }
}
