package com.bandung.disiplin.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtil {

    private static final String PREF_LOGIN = "PREF_LOGIN";

    private static final String PREF_ACCESS_TOKEN = "PREF_ACCESS_TOKEN";


    private static SharedPreferences getSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void saveString(Context activity, String key, String value) {
        getSharedPreference(activity).edit().putString(key,value).commit();
    }

    public static String getString(Context activity, String key) {
        return getSharedPreference(activity).getString(key, null);
    }



    public static void setLogin(Context context, boolean isLogin) {
        getSharedPreference(context).edit().putBoolean(PREF_LOGIN, isLogin).commit();
    }

    public static boolean isLogin(Context context) {
        return getSharedPreference(context).getBoolean(PREF_LOGIN, false);
    }

    public static void logout(Context context) {
        getSharedPreference(context).edit().clear().apply();
    }


}
