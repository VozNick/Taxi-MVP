package com.example.vmm408.taxiuserproject.signin.google;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;

public interface GoogleSignInApi {
    GoogleApiClient createGoogleApiClient();

    void setUserData(GoogleSignInAccount userData);

    String getUserId();

    String getUserPhotoUrl();

    String getUserFullName();
}
