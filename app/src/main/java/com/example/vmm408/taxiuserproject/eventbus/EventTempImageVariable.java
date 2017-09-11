package com.example.vmm408.taxiuserproject.eventbus;

import android.graphics.Bitmap;

public class EventTempImageVariable {
    private Bitmap bitmap;

    public EventTempImageVariable(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
