package com.example.vmm408.taxiuserproject.signin.model;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface SignInModel {
    void handleSignInResult(GoogleSignInAccount googleSignInAccount);

    void saveUserToSharedPreference(String userKey);
}
