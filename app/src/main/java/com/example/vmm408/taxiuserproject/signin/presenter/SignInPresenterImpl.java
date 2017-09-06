package com.example.vmm408.taxiuserproject.signin.presenter;

import com.example.vmm408.taxiuserproject.models.UserModel;
import com.example.vmm408.taxiuserproject.signin.model.SignInModel;
import com.example.vmm408.taxiuserproject.signin.view.SignInView;

public class SignInPresenterImpl implements SignInPresenter {
    private SignInView signInView;
    private SignInModel signInModel;

    public SignInPresenterImpl(SignInView signInView, SignInModel signInModel) {
        this.signInView = signInView;
        this.signInModel = signInModel;
    }

    @Override
    public void signIn() {
        signInView.showProgress();
        signInView.startActivityForResult();
    }

    @Override
    public void resultIsSuccess() {
        signInModel.findUserInDataBase(signInView.getUserId());
    }

    @Override
    public void resultFailed() {
        signInView.hideProgress();
        signInView.onResultFailed();
    }

    @Override
    public void getEvent(UserModel userModel) {
        if (userModel != null) {
            signInModel.saveUserToSharedPreference(signInView.getUserId());
            signInView.navigateToMapActivity();
        } else {
            signInView.navigateToProfileActivity();
        }
        signInView.hideProgress();
    }

    @Override
    public void onDestroy() {
        signInView = null;
        signInModel = null;
    }
}
