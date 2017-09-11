package com.example.vmm408.taxiuserproject.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;

import java.util.Calendar;

public class CustomDatePickerDialog {
    private Context context;
    private Calendar calendar = Calendar.getInstance();
    private String dateOfBirth;

    public CustomDatePickerDialog(Context context) {
        this.context = context;
    }

    public String getDate() {
        Log.d("tag", calendar + "");
        createDatePickerDialog();
        return dateOfBirth;
    }

    private void createDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                ((view1, year, month, dayOfMonth) ->
                        dateOfBirth = dayOfMonth + "." + (month + 1) + "." + year),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getWindow();
        datePickerDialog.show();
    }
}
