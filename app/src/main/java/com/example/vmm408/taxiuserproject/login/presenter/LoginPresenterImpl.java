package com.example.vmm408.taxiuserproject.login.presenter;

import com.example.vmm408.taxiuserproject.login.google.GoogleAuthService;
import com.example.vmm408.taxiuserproject.login.model.LoginModel;
import com.example.vmm408.taxiuserproject.login.view.LoginView;
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
        if (loginModel.userSignedInApp() != null) {
            loginView.navigateToMapActivity();
        }
    }

    @Override
    public void onClickSignIn() {
        loginView.showLoading(true);
        googleAuthService.signInWithGoogle();
    }

    @Override
    public void connectionFailed() {
        loginView.showConnectionErrorMessage();
    }

    @Override
    public void handleSignInResult(GoogleSignInResult result) {
        if (googleAuthService.signInResultIsSuccess(result)) {
            googleAuthService.getSignInAccount();
            saveUserProfile();
            loginView.navigateToProfileActivity();
        } else {
            loginView.showResultErrorMessage();
        }
        loginView.showLoading(false);
    }

    private void saveUserProfile() {
        loginModel.saveUser(
                googleAuthService.getUserId(),
                googleAuthService.getUserPhotoUrl(),
                googleAuthService.getUserFullName());
    }

    @Override
    public void onDestroy() {
        loginView = null;
        loginModel = null;
    }
}