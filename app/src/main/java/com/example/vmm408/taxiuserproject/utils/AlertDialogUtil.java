package com.example.vmm408.taxiuserproject.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.example.vmm408.taxiuserproject.R;

public class AlertDialogUtil {
    public static AlertDialog menuAvatar(Context context, AlertDialog.OnClickListener listener) {
        return new AlertDialog.Builder(context).setItems(R.array.menu_new_avatar, listener).create();
    }
}
