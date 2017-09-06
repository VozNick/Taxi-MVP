package com.example.vmm408.taxiuserproject.signin.google;

import com.example.vmm408.taxiuserproject.signin.view.SignInActivity;
import com.example.vmm408.taxiuserproject.signin.view.SignInView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

public class GoogleSignInPresenterImpl implements GoogleSignInPresenter {
    private SignInView signInView;
    private GoogleApiClient.OnConnectionFailedListener failedListener =
            connectionResult -> signInView.onConnectionFailedListener();

    @Override
    public GoogleApiClient createGoogleClient(SignInActivity signInActivity) {
        this.signInView = signInActivity;
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        return new GoogleApiClient
                .Builder(signInActivity)
                .enableAutoManage(signInActivity, failedListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }
}
