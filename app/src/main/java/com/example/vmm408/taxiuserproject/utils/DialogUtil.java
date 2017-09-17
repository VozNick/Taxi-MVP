package com.example.vmm408.taxiuserproject.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.example.vmm408.taxiuserproject.R;

public class DialogUtil {
    public static AlertDialog menuAvatar(Context context, AlertDialog.OnClickListener listener) {
        return new AlertDialog.Builder(context)
                .setItems(R.array.menu_new_avatar, listener)
                .create();
    }

    public static AlertDialog confirmExitApp(Context context, AlertDialog.OnClickListener listener) {
        return new AlertDialog.Builder(context)
                .setMessage(R.string.text_confirm_exit_app)
                .setPositiveButton(R.string.btn_confirm_dialog, listener)
                .setNegativeButton(R.string.btn_cancel_dialog, null)
                .create();
    }

    public static AlertDialog menuProfile(Context context, AlertDialog.OnClickListener listener) {
        return new AlertDialog.Builder(context)
                .setItems(R.array.menu_profile, listener)
                .create();
    }

    public static ProgressDialog progressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.progress_dialog_msg));
        progressDialog.setCancelable(false);
        return progressDialog;
    }
}
