package com.example.vmm408.taxiuserproject.signin.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.map.MapActivity;
import com.example.vmm408.taxiuserproject.profile.view.ProfileActivity;
import com.example.vmm408.taxiuserproject.signin.presenter.SignInPresenterImpl;
import com.example.vmm408.taxiuserproject.signin.model.SignInModelImpl;
import com.example.vmm408.taxiuserproject.signin.presenter.SignInPresenter;
import com.example.vmm408.taxiuserproject.utils.MyKeys;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity implements SignInView {
    private SignInPresenter signInPresenter;
    private ProgressDialog progressDialog;
    private GoogleApiClient googleApiClient;
    private GoogleSignInAccount googleSignInAccount;
    private GoogleApiClient.OnConnectionFailedListener failedListener =
            connectionResult -> makeToast(getResources().getString(R.string.toast_connection_failed));

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_google_sign_in);
        ButterKnife.bind(this);
        initProgressDialog();
        if (signInPresenter == null) {
            signInPresenter = new SignInPresenterImpl(this, new SignInModelImpl());
        }
    }

    @Override
    public void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.progress_dialog_msg));
        progressDialog.setCancelable(false);
    }

    @OnClick(R.id.sign_in_button)
    void signIn() {
        signInPresenter.signIn();
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void createGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();
        googleApiClient = new GoogleApiClient
                .Builder(this)
                .enableAutoManage(this, failedListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void startActivityForResult() {
        startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(googleApiClient), MyKeys.SIGN_IN_KEY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MyKeys.SIGN_IN_KEY) {
            handleSignInResult(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            googleSignInAccount = result.getSignInAccount();
            signInPresenter.resultIsSuccess();
        } else {
            signInPresenter.resultFailed();
        }
    }

    @Override
    public void onResultFailed() {
        makeToast(getResources().getString(R.string.toast_result_failed));
    }

    @Override
    public String getUserId() {
        return googleSignInAccount.getId();
    }

    @Override
    public String getUserPhotoUrl() {
        return String.valueOf(googleSignInAccount.getPhotoUrl());
    }

    @Override
    public String getUserFullName() {
        return googleSignInAccount.getGivenName() + " " + googleSignInAccount.getFamilyName();
    }

    @Override
    public void navigateToProfileActivity() {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    @Override
    public void navigateToMapActivity() {
        startActivity(new Intent(this, MapActivity.class));
    }

    protected void makeToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        signInPresenter.onDestroy();
        super.onDestroy();
    }
}
