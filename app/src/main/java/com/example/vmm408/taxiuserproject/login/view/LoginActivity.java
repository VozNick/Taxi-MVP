package com.example.vmm408.taxiuserproject.login.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.map.MapActivity;
import com.example.vmm408.taxiuserproject.profile.view.ProfileActivity;
import com.example.vmm408.taxiuserproject.login.google.GoogleAuthService;
import com.example.vmm408.taxiuserproject.login.presenter.LoginPresenterImpl;
import com.example.vmm408.taxiuserproject.login.model.LoginModelImpl;
import com.example.vmm408.taxiuserproject.login.presenter.LoginPresenter;
import com.example.vmm408.taxiuserproject.utils.MyKeys;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity
        implements LoginView, GoogleApiClient.OnConnectionFailedListener {
    private LoginPresenter loginPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_google_sign_in);
        ButterKnife.bind(this);
        createDialog();
        if (loginPresenter == null) {
            loginPresenter = new LoginPresenterImpl(this, new LoginModelImpl(), new GoogleAuthService(this));
        }
    }

    private void createDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.progress_dialog_msg));
        progressDialog.setCancelable(false);
    }

    @OnClick(R.id.sign_in_button)
    void btnSignIn() {
        loginPresenter.onClickSignIn();
    }

    @Override
    public void showLoading(boolean flag) {
        if (flag) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MyKeys.SIGN_IN_KEY) {
            loginPresenter.handleSignInResult(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        loginPresenter.connectionFailed();
    }

    @Override
    public void showConnectionErrorMessage() {
        makeToast(getResources().getString(R.string.toast_connection_failed));
    }

    @Override
    public void showResultErrorMessage() {
        makeToast(getResources().getString(R.string.toast_result_failed));
    }

    @Override
    public void navigateToProfileActivity() {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    @Override
    public void navigateToMapActivity() {
        startActivity(new Intent(this, MapActivity.class));
    }

    private void makeToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        loginPresenter.onDestroy();
        super.onDestroy();
    }
}
