package com.example.vmm408.taxiuserproject.signin.model;

import com.example.vmm408.taxiuserproject.App;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.example.vmm408.taxiuserproject.utils.UserSharedUtils;
import static com.example.vmm408.taxiuserproject.models.UserModel.SignedUser;

public class SignInModelImpl implements SignInModel {
    @Override
    public String userSignedInApp() {
        return UserSharedUtils.userSignedInApp(new App().appBaseContext);
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
