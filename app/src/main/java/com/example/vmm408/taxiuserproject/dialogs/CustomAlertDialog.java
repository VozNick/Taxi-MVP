package com.example.vmm408.taxiuserproject.dialogs;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.example.vmm408.taxiuserproject.R;

public class CustomAlertDialog implements CustomAlertDialogInterface {
    private Context context;
    private Integer position;

    public CustomAlertDialog(Context context) {
        this.context = context;
    }

    @Override
    public Integer showMenuProfileDialog() {
        dialog().setItems(R.array.menu_profile, (dialogInterface, i) -> position = i).show();
        return position;
    }

    @Override
    public void showNewOrderDialog() {

    }

    @Override
    public void showCurrentOrderDialog() {

    }

    @Override
    public void showConfirmDialog() {

    }

    private AlertDialog.Builder dialog() {
        return new AlertDialog.Builder(context);
    }
}
