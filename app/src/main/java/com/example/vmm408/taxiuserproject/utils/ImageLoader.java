package com.example.vmm408.taxiuserproject.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.ImageView;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.profile.view.ProfileActivity;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ImageLoader {
    private Context context;

    public ImageLoader(Context context) {
        this.context = context;
    }

    public void loadImage(String uri, ImageView imageView) {
        Picasso.with(context)
                .load(uri)
                .placeholder(R.drawable.ic_person_black_24dp)
                .into(imageView);
    }

    public void loadImage(Uri uri, ImageView imageView) {
        Picasso.with(context)
                .load(new File(getPathFromURL(uri)))
                .placeholder(R.drawable.ic_person_black_24dp)
                .into(imageView);
    }

    private String getPathFromURL(Uri url) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(url, new String[]{"_data"}, null, null, null);
            if (cursor != null && cursor.moveToFirst())
                return cursor.getString(cursor.getColumnIndexOrThrow("_data"));
        } catch (NullPointerException e) {
            ((ProfileActivity) context).makeToast(context.getResources().getString(R.string.toast_load_photo_error));
        }
        if (cursor != null)
            cursor.close();
        return null;
    }
}