package com.example.vmm408.taxiuserproject.signin.google;

import android.content.Context;

import com.example.vmm408.taxiuserproject.signin.view.SignInActivity;
import com.example.vmm408.taxiuserproject.utils.MyKeys;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class GoogleSignInImpl implements GoogleSignIn {
    private Context context;
    private GoogleSignInResult googleSignInResult;
    private GoogleSignInAccount googleSignInAccount;

    public GoogleSignInImpl(Context context) {
        this.context = context;
    }

    @Override
    public void signInWithGoogle() {
        ((SignInActivity) context).startActivityForResult(
                Auth.GoogleSignInApi.getSignInIntent(createGoogleApiClient()), MyKeys.SIGN_IN_KEY);
    }

    private GoogleApiClient createGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();
        return new GoogleApiClient
                .Builder(context)
                .enableAutoManage((SignInActivity) context, (SignInActivity) context)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public boolean signInResultIsSuccess(GoogleSignInResult result) {
        return (googleSignInResult = result).isSuccess();
    }

    @Override
    public void getSignInAccount() {
        googleSignInAccount = googleSignInResult.getSignInAccount();
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
