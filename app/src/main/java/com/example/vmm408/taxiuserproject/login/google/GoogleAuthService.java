package com.example.vmm408.taxiuserproject.login.google;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.example.vmm408.taxiuserproject.constants.MyKeys;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class GoogleAuthService {
    private Context context;
    private GoogleApiClient googleApiClient;
    private GoogleSignInResult googleSignInResult;
    private GoogleSignInAccount googleSignInAccount;

    public GoogleAuthService(Context context) {
        this.context = context;
    }

    public void signInWithGoogle() {
        ((AppCompatActivity) context).startActivityForResult(
                Auth.GoogleSignInApi.getSignInIntent(createGoogleApiClient()), MyKeys.SIGN_IN_KEY);
    }

    private GoogleApiClient createGoogleApiClient() {
        if (googleApiClient == null) {
            GoogleSignInOptions gso = new GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .build();
            googleApiClient = new GoogleApiClient
                    .Builder(context)
                    .enableAutoManage((AppCompatActivity) context, (GoogleApiClient.OnConnectionFailedListener) context)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
        return googleApiClient;
    }

    public boolean signInResultIsSuccess(GoogleSignInResult result) {
        return (googleSignInResult = result).isSuccess();
    }

    public void getSignInAccount() {
        googleSignInAccount = googleSignInResult.getSignInAccount();
    }

    public String getUserId() {
        return googleSignInAccount.getId();
    }

    public String getUserFullName() {
        return googleSignInAccount.getGivenName() + " " + googleSignInAccount.getFamilyName();
    }
}
