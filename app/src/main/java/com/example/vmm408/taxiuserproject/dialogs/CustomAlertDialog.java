package com.example.vmm408.taxiuserproject.dialogs;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.profile.view.ProfileActivity;

public class CustomAlertDialog implements CustomAlertDialogInterface {
    private Context context;
    private AlertDialog.Builder dialog;
    private Integer position;

    public CustomAlertDialog(Context context) {
        this.context = context;
        dialog = new AlertDialog.Builder(context);
    }

    public void showAvatarMenuDialog() {
        dialog.setItems(R.array.menu_new_avatar, (ProfileActivity) context).show();
    }

    public Integer showMenuProfileDialog() {
        dialog.setItems(R.array.menu_profile, (dialogInterface, i) -> position = i).show();
        return position;
    }

    public void showNewOrderDialog() {

    }

    public void showCurrentOrderDialog() {

    }

    public void showConfirmDialog() {

    }
}
