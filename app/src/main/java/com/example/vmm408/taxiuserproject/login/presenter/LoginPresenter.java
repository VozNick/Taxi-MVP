package com.example.vmm408.taxiuserproject.login.presenter;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;

public interface LoginPresenter {
    void onSignInClick();

    void onConnectionFailed();

    void onSignInResult(GoogleSignInResult result);

    void onDestroy();
}
