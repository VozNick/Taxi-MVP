package com.example.vmm408.taxiuserproject.signin.presenter;

import android.content.Intent;

import com.example.vmm408.taxiuserproject.signin.view.SignInActivity;

public interface GoogleSignInPresenter {
    void createGoogleClient(SignInActivity signInActivity);

    void signIn(SignInActivity signInActivity);

    void onActivityResult(SignInActivity signInActivity, int requestCode, int resultCode, Intent data);

    void onStart();

    void onStop();

    void onDestroy();
}
