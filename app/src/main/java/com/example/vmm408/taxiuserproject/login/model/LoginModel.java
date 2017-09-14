package com.example.vmm408.taxiuserproject.login.model;

import com.example.vmm408.taxiuserproject.login.presenter.UserProfileCallBack;
import com.example.vmm408.taxiuserproject.models.UserModel;

public interface LoginModel {
    boolean userSignedInApp();

    void checkUserExist(String userId, UserProfileCallBack userProfileCallBack);

    void saveUser(UserModel model);
}
