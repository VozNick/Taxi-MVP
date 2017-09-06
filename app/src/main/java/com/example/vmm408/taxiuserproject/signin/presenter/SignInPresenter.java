package com.example.vmm408.taxiuserproject.signin.presenter;

import com.example.vmm408.taxiuserproject.models.UserModel;

public interface SignInPresenter {
    void signIn();

    void resultIsSuccess();

    void resultFailed();

    void getEvent(UserModel userModel);

    void onDestroy();
}
