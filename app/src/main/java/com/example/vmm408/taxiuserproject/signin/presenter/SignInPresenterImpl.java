package com.example.vmm408.taxiuserproject.signin.presenter;

import com.example.vmm408.taxiuserproject.signin.google.GoogleSignInApi;
import com.example.vmm408.taxiuserproject.signin.model.SignInModel;
import com.example.vmm408.taxiuserproject.signin.view.SignInView;

public class SignInPresenterImpl implements SignInPresenter {
    private SignInView signInView;
    private SignInModel signInModel;
    private GoogleSignInApi googleSignInApi;

    public SignInPresenterImpl(SignInView signInView,
                               SignInModel signInModel,
                               GoogleSignInApi googleSignInApi) {
        this.signInView = signInView;
        this.signInModel = signInModel;
        this.googleSignInApi = googleSignInApi;
        if (signInModel.userSignedInApp() != null) {
            signInView.navigateToMapActivity();
        }
    }

    @Override
    public void onClickSignIn() {
        signInView.showLoading(true);
        signInView.signInWithGoogle();
    }

    @Override
    public void connectionFailedListener() {
        signInView.onConnectionFailed();
    }

    @Override
    public void resultIsSuccess(boolean flag) {
        if (flag) {
            signInModel.saveUser(
                    googleSignInApi.getUserId(),
                    googleSignInApi.getUserPhotoUrl(),
                    googleSignInApi.getUserFullName());
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
