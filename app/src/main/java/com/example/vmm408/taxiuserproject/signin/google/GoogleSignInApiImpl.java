package com.example.vmm408.taxiuserproject.signin.google;

import android.content.Context;

import com.example.vmm408.taxiuserproject.signin.view.SignInActivity;
import com.example.vmm408.taxiuserproject.signin.view.SignInView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

public class GoogleSignInApiImpl implements GoogleSignInApi {
    private SignInView signInView;
    private GoogleSignInAccount googleSignInAccount;
    private GoogleApiClient.OnConnectionFailedListener failedListener;
//            connectionResult -> signInPresenter.connectionFailedListener();

    public GoogleSignInApiImpl(SignInView signInView) {
        this.signInView = signInView;
    }

    @Override
    public GoogleApiClient createGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();
        return new GoogleApiClient
                .Builder((Context) signInView)
                .enableAutoManage((SignInActivity) signInView, failedListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void setUserData(GoogleSignInAccount userData) {
        googleSignInAccount = userData;
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
}
