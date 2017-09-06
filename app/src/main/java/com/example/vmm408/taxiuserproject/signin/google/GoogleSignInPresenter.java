package com.example.vmm408.taxiuserproject.signin.google;

import com.example.vmm408.taxiuserproject.signin.view.SignInActivity;
import com.google.android.gms.common.api.GoogleApiClient;

public interface GoogleSignInPresenter {
    GoogleApiClient createGoogleClient(SignInActivity signInActivity);
}
