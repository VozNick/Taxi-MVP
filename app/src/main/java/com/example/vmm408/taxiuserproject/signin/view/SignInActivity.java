package com.example.vmm408.taxiuserproject.signin.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.eventbus.EventUserFromBase;
import com.example.vmm408.taxiuserproject.map.MapActivity;
import com.example.vmm408.taxiuserproject.profile.ProfileActivity;
import com.example.vmm408.taxiuserproject.signin.google.GoogleSignInPresenterImpl;
import com.example.vmm408.taxiuserproject.signin.presenter.SignInPresenterImpl;
import com.example.vmm408.taxiuserproject.signin.model.SignInModelImpl;
import com.example.vmm408.taxiuserproject.signin.presenter.SignInPresenter;
import com.example.vmm408.taxiuserproject.utils.MyKeys;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity implements SignInView {
    private ProgressDialog progressDialog;
    private SignInPresenter signInPresenter;
    private GoogleApiClient googleApiClient;
    private GoogleSignInAccount googleSignInAccount;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_google_sign_in);
        ButterKnife.bind(this);
        initProgressDialog();
        if (signInPresenter == null) {
            signInPresenter = new SignInPresenterImpl(this, new SignInModelImpl());
        }
        googleApiClient = new GoogleSignInPresenterImpl().createGoogleClient(this);
    }

    private void initProgressDialog() {
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
    public void onConnectionFailedListener() {
        makeToast(getResources().getString(R.string.toast_connection_failed));
    }

    @Override
    public void onResultFailed() {
        makeToast("result failed");
    }

    @Override
    public void startActivityForResult() {
        startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(googleApiClient), MyKeys.SIGN_IN_KEY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
    public String getUserId() {
        return googleSignInAccount.getId();
    }

    @Subscribe
    public void getEvent(EventUserFromBase eventUserFromBase) {
        signInPresenter.getEvent(eventUserFromBase.getUserModel());
    }

    @Override
    public void navigateToProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("userId", googleSignInAccount.getId());
        intent.putExtra("userPhotoUrl", googleSignInAccount.getPhotoUrl());
        intent.putExtra("userFullName", googleSignInAccount.getGivenName() + " " + googleSignInAccount.getFamilyName());
        startActivity(intent);
    }

    @Override
    public void navigateToMapActivity() {
        startActivity(new Intent(this, MapActivity.class));
    }

    protected void makeToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        signInPresenter.onDestroy();
        super.onDestroy();
    }
}
