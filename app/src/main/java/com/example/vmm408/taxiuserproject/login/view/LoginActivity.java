package com.example.vmm408.taxiuserproject.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.map.view.MapActivity;
import com.example.vmm408.taxiuserproject.profile.view.ProfileActivity;
import com.example.vmm408.taxiuserproject.login.google.GoogleAuthService;
import com.example.vmm408.taxiuserproject.login.presenter.LoginPresenterImpl;
import com.example.vmm408.taxiuserproject.login.model.LoginModelImpl;
import com.example.vmm408.taxiuserproject.login.presenter.LoginPresenter;
import com.example.vmm408.taxiuserproject.constants.MyKeys;
import com.example.vmm408.taxiuserproject.utils.DialogUtil;
import com.example.vmm408.taxiuserproject.utils.ToasterUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.vmm408.taxiuserproject.constants.MyKeys.FULL_NAME_KEY;
import static com.example.vmm408.taxiuserproject.constants.MyKeys.USER_ID_KEY;

public class LoginActivity extends AppCompatActivity implements LoginView, GoogleApiClient.OnConnectionFailedListener {
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_google_sign_in);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenterImpl(this, new LoginModelImpl(), new GoogleAuthService(this));
    }

    @OnClick(R.id.sign_in_button)
    void btnSignIn() {
        loginPresenter.onSignInClick();
    }

    @Override
    public void showLoading(boolean isShowing) {
        if (isShowing) {
            DialogUtil.progressDialog(this).show();
        } else {
            DialogUtil.progressDialog(this).dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MyKeys.SIGN_IN_KEY) {
            loginPresenter.onSignInResult(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        loginPresenter.onConnectionFailed();
    }

    @Override
    public void showConnectionErrorMessage() {
        ToasterUtil.makeToast(this, getResources().getString(R.string.toast_connection_failed));
    }

    @Override
    public void showResultErrorMessage() {
        ToasterUtil.makeToast(this, getResources().getString(R.string.toast_result_failed));
    }

    @Override
    public void navigateToProfileActivity(String userId, String userFullName) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        intent.putExtra(FULL_NAME_KEY, userFullName);
        startActivity(intent);
        finish();
    }

    @Override
    public void navigateToMapActivity() {
        startActivity(new Intent(this, MapActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        loginPresenter.onDestroy();
        super.onDestroy();
    }
}
