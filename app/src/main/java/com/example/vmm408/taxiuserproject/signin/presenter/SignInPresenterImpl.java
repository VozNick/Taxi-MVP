package com.example.vmm408.taxiuserproject.signin.presenter;

import com.example.vmm408.taxiuserproject.signin.google.GoogleSignIn;
import com.example.vmm408.taxiuserproject.signin.model.SignInModel;
import com.example.vmm408.taxiuserproject.signin.view.SignInView;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

public class SignInPresenterImpl implements SignInPresenter {
    private SignInView signInView;
    private SignInModel signInModel;
    private GoogleSignIn googleSignIn;

    public SignInPresenterImpl(SignInView signInView,
                               SignInModel signInModel,
                               GoogleSignIn googleSignIn) {
        this.signInView = signInView;
        this.signInModel = signInModel;
        this.googleSignIn = googleSignIn;
        if (signInModel.userSignedInApp().length() > 0) {
            signInView.navigateToMapActivity();
        }
    }

    @Override
    public void onClickSignIn() {
        signInView.showLoading(true);
        googleSignIn.signInWithGoogle();
    }

    @Override
    public void connectionFailedListener() {
        signInView.onConnectionFailed();
    }

    @Override
    public void handleSignInResult(GoogleSignInResult result) {
        if (googleSignIn.signInResultIsSuccess(result)) {
            googleSignIn.getSignInAccount();
            saveUserProfile();
            signInView.navigateToProfileActivity();
        } else {
            signInView.onResultFailed();
        }
        signInView.showLoading(false);
    }

    private void saveUserProfile() {
        signInModel.saveUser(
                googleSignIn.getUserId(),
                googleSignIn.getUserPhotoUrl(),
                googleSignIn.getUserFullName());
    }

    @Override
    public void onDestroy() {
        signInView = null;
        signInModel = null;
    }
}
