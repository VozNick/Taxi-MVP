package com.example.vmm408.taxiuserproject.login.model;

import com.example.vmm408.taxiuserproject.login.presenter.UserExistCallBack;

public interface LoginModel {
    boolean userSignedInApp();

    void checkUserExist(UserExistCallBack userExistCallBack, String userId);
}
