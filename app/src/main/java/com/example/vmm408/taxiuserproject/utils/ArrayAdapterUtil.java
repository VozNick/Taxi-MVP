package com.example.vmm408.taxiuserproject.utils;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class ArrayAdapterUtil {
    public static ArrayAdapter initAdapter(Context context, List objects) {
        return new ArrayAdapter(context, android.R.layout.simple_list_item_1, objects);
    }
}
