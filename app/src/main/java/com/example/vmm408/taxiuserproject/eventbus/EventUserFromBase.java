package com.example.vmm408.taxiuserproject.eventbus;

import com.example.vmm408.taxiuserproject.models.UserModel;

public class EventUserFromBase {
    private UserModel userModel;

    public EventUserFromBase(UserModel userModel) {
        this.userModel = userModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }
}
