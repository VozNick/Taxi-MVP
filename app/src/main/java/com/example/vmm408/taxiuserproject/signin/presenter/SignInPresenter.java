package com.example.vmm408.taxiuserproject.signin.presenter;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;

public interface SignInPresenter {
    void onClickSignIn();

    void connectionFailedListener();

    void handleSignInResult(GoogleSignInResult result);

    void onDestroy();
}
