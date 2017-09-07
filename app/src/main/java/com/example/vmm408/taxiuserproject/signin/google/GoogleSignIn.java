package com.example.vmm408.taxiuserproject.signin.google;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;

public interface GoogleSignIn {
    void signInWithGoogle();

    boolean signInResultIsSuccess(GoogleSignInResult result);

    void getSignInAccount();

    String getUserId();

    String getUserPhotoUrl();

    String getUserFullName();
}
