package com.example.vmm408.taxiuserproject.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.profile.view.ProfileActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

public class ImageLoader {
    private static ImageView imageView;
    private static Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            imageView.setImageBitmap(bitmap);
            EventBus.getDefault().post(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    public static void loadImage(Context context, String uri, ImageView imageView) {
        ImageLoader.imageView = imageView;
        Picasso.with(context)
                .load(uri)
                .placeholder(R.drawable.ic_person_black_24dp)
                .into(target);
    }

    public static void loadImage(Context context, Uri uri, ImageView imageView) {
        ImageLoader.imageView = imageView;
        Picasso.with(context)
                .load(new File(getPathFromURL(context, uri)))
                .placeholder(R.drawable.ic_person_black_24dp)
                .into(target);
    }

    private static String getPathFromURL(Context context, Uri url) {
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
