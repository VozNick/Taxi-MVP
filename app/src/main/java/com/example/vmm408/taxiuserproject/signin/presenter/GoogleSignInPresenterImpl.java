package com.example.vmm408.taxiuserproject.signin.presenter;

import android.content.Intent;
import android.util.Log;

import com.example.vmm408.taxiuserproject.eventbus.EventMessage;
import com.example.vmm408.taxiuserproject.signin.model.SignInModel;
import com.example.vmm408.taxiuserproject.signin.view.SignInActivity;
import com.example.vmm408.taxiuserproject.signin.view.SignInView;
import com.example.vmm408.taxiuserproject.utils.MyKeys;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class GoogleSignInPresenterImpl implements GoogleSignInPresenter {
    private SignInView signInView;
    private SignInModel signInModel;
    private GoogleSignInAccount signInAccount;
    private GoogleApiClient googleApiClient;
    private GoogleApiClient.OnConnectionFailedListener failedListener =
            connectionResult -> signInView.onConnectionFailedListener();

    public GoogleSignInPresenterImpl(SignInView signInView, SignInModel signInModel) {
        this.signInView = signInView;
        this.signInModel = signInModel;
    }

    @Override
    public void createGoogleClient(SignInActivity signInActivity) {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient
                .Builder(signInActivity)
                .enableAutoManage(signInActivity, failedListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void signIn(SignInActivity signInActivity) {

        Log.d("tag", "2");
        signInView.showProgress();
        signInActivity.startActivityForResult(
                Auth.GoogleSignInApi.getSignInIntent(googleApiClient), MyKeys.SIGN_IN_KEY);
    }

    @Override
    public void onActivityResult(SignInActivity signInActivity, int requestCode, int resultCode, Intent data) {

        Log.d("tag", "3");
        if (requestCode == MyKeys.SIGN_IN_KEY) {

            Log.d("tag", "4");
            handleSignInResult(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {

        Log.d("tag", "4.1");
        if (result.isSuccess()) {

            Log.d("tag", "5");
            signInAccount = result.getSignInAccount();
            signInModel.handleSignInResult(signInAccount);
        } else {
            signInView.hideProgress();
            signInView.onResultFailed();

        }
    }

    @Subscribe
    public void getEvent(EventMessage eventMessage) {
        Log.d("tag", "getEvent");
        if (eventMessage.getDataSnapshot().hasChildren()) {
            Log.d("tag", "hasChildren");
            signInModel.saveUserToSharedPreference(eventMessage.getDataSnapshot().getKey());
            signInView.navigateToMapActivity();
        } else {
            Log.d("tag", "null");
            signInView.navigateToProfileActivity(
                    signInAccount.getId(),
                    String.valueOf(signInAccount.getPhotoUrl()),
                    signInAccount.getGivenName() + " " + signInAccount.getFamilyName());
        }
        Log.d("tag", "hideProgress");
        signInView.hideProgress();
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        signInView = null;
        signInModel = null;
    }
}
