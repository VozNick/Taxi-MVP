package com.example.vmm408.taxiuserproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.vmm408.taxiuserproject.models.UserModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PreferenceUtils {
    private static Gson gson = new GsonBuilder().create();
    private static final String SHARED_KEY = "TaxiPerfect";
    private static final String SHARED_USER_PROFILE_KEY = "userProfile";

    public static UserModel getUserProfileFromShared(Context context) {
        return gson.fromJson(context.getSharedPreferences(SHARED_KEY, Context.MODE_APPEND)
                .getString(SHARED_USER_PROFILE_KEY, ""), UserModel.class);
    }

    public static void saveUserProfileToShared(Context context, UserModel userProfile) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_KEY, Context.MODE_APPEND);
        preferences.edit().putString(SHARED_USER_PROFILE_KEY, gson.toJson(userProfile)).apply();
    }

    public static void deleteUserProfielFromShared(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_KEY, Context.MODE_APPEND);
        preferences.edit().remove(SHARED_USER_PROFILE_KEY).apply();
    }
}
