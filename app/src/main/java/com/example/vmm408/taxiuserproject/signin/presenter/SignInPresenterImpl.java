package com.example.vmm408.taxiuserproject.signin.presenter;
import com.example.vmm408.taxiuserproject.signin.model.SignInModel;
import com.example.vmm408.taxiuserproject.signin.view.SignInView;

public class SignInPresenterImpl implements SignInPresenter {
    private SignInView signInView;
    private SignInModel signInModel;

    public SignInPresenterImpl(SignInView signInView, SignInModel signInModel) {
        this.signInView = signInView;
        this.signInModel = signInModel;
        if (signInModel.userSignedInApp() != null) {
            signInView.navigateToMapActivity();
            return;
        }
        signInView.initProgressDialog();
        signInView.createGoogleApiClient();
    }

    @Override
    public void signIn() {
        signInView.showProgress();
        signInView.startActivityForResult();
    }

    @Override
    public void resultFailed() {
        signInView.hideProgress();
        signInView.onResultFailed();
    }

    @Override
    public void resultIsSuccess() {
        signInModel.saveUser(
                    signInView.getUserId(), signInView.getUserPhotoUrl(), signInView.getUserFullName());
        signInView.navigateToProfileActivity();
        signInView.hideProgress();
    }

    @Override
    public void onDestroy() {
        signInView = null;
        signInModel = null;
    }
}
