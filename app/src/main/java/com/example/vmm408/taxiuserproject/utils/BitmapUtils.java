package com.example.vmm408.taxiuserproject.utils;

import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class BitmapUtils {
    public static String getFileToString(ImageView imageView) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageView.getDrawingCache().compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }
}
