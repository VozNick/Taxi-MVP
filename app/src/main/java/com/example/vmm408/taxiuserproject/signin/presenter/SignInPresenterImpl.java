package com.example.vmm408.taxiuserproject.signin.presenter;

import com.example.vmm408.taxiuserproject.signin.google.GoogleSignIn;
import com.example.vmm408.taxiuserproject.signin.model.SignInModel;
import com.example.vmm408.taxiuserproject.signin.view.SignInView;

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
        if (signInModel.userSignedInApp() != null) {
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
    public void resultIsSuccess(boolean flag) {
        if (flag) {
            signInModel.saveUser(
                    googleSignIn.getUserId(),
                    googleSignIn.getUserPhotoUrl(),
                    googleSignIn.getUserFullName());
            signInView.navigateToProfileActivity();
        } else {
            signInView.onResultFailed();
        }
        signInView.showLoading(false);
    }

    @Override
    public void onDestroy() {
        signInView = null;
        signInModel = null;
    }
}
