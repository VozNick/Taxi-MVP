package com.example.vmm408.taxiuserproject.signin.presenter;

public interface SignInPresenter {
    void onClickSignIn();

    void connectionFailedListener();

    void resultIsSuccess(boolean flag);

    void onDestroy();
}
