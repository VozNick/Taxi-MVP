package com.example.vmm408.taxiuserproject.signin.google;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface GoogleSignIn {
//    GoogleApiClient createGoogleApiClient();

    void signInWithGoogle();

    void setUserData(GoogleSignInAccount userData);

    String getUserId();

    String getUserPhotoUrl();

    String getUserFullName();
}
