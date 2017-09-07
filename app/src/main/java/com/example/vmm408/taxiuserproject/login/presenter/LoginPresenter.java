package com.example.vmm408.taxiuserproject.login.presenter;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;

public interface LoginPresenter {
    void onClickSignIn();

    void connectionFailed();

    void handleSignInResult(GoogleSignInResult result);

    void onDestroy();
}
