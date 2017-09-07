package com.example.vmm408.taxiuserproject.eventbus;

import android.content.Intent;

import com.example.vmm408.taxiuserproject.models.UserModel;

public class EventSignInApi {
    private Intent intent;

    public EventSignInApi(Intent intent) {
        this.intent = intent;
    }

    public Intent getIntent() {
        return intent;
    }
}
