package com.example.vmm408.taxiuserproject.utils;

import android.app.DatePickerDialog;
import android.content.Context;

import java.util.Calendar;

public class DatePickerDialogUtil {
    public static DatePickerDialog userAge(Context context, DatePickerDialog.OnDateSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        return new DatePickerDialog(context,
                android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }
}
