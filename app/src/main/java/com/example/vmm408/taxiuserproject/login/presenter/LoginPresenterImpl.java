package com.example.vmm408.taxiuserproject.login.presenter;

import com.example.vmm408.taxiuserproject.login.google.GoogleAuthService;
import com.example.vmm408.taxiuserproject.login.model.LoginModel;
import com.example.vmm408.taxiuserproject.login.view.LoginView;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;
    private LoginModel loginModel;
    private GoogleAuthService googleAuthService;

    public LoginPresenterImpl(LoginView loginView,
                              LoginModel loginModel,
                              GoogleAuthService googleAuthService) {
        this.loginView = loginView;
        this.loginModel = loginModel;
        this.googleAuthService = googleAuthService;
        if (loginModel.userSignedInApp()) {
            loginView.navigateToMapActivity();
        }
    }

    @Override
    public void onSignInClick() {
        loginView.showLoading(true);
        googleAuthService.signInWithGoogle();
    }

    @Override
    public void onConnectionFailed() {
        loginView.showConnectionErrorMessage();
    }

    @Override
    public void onSignInResult(GoogleSignInResult result) {
        if (googleAuthService.signInResultIsSuccess(result)) {
            googleAuthService.getSignInAccount();
            loginModel.findUserProfile(googleAuthService.getUserId(), this::onUserExist);
        } else {
            loginView.showResultErrorMessage();
        }
    }

    private void onUserExist(UserModel model) {
        if (model == null) {
            loginView.navigateToProfileActivity(googleAuthService.getUserId(), googleAuthService.getUserFullName());
        } else {
            loginModel.saveUser(model);
            loginView.navigateToMapActivity();
        }
        loginView.showLoading(false);
    }

    @Override
    public void onDestroy() {
        loginView = null;
        loginModel = null;
    }
}
