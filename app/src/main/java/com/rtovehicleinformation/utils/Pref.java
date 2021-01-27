package com.rtovehicleinformation.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Pref {
    public static String MY_PREFS = "MY_PREFS";
    private static SharedPreferences sharedPreferences;

    public static boolean getBooleanValue(final Context context, final String s, final boolean b) {
        openPref(context);
        final boolean boolean1 = Pref.sharedPreferences.getBoolean(s, b);
        Pref.sharedPreferences = null;
        return boolean1;
    }

    public static int getIntValue(final Context context, final String s, int int1) {
        openPref(context);
        int1 = Pref.sharedPreferences.getInt(s, int1);
        Pref.sharedPreferences = null;
        return int1;
    }

    public static String getStringValue(final Context context, final String s, final String s2) {
        openPref(context);
        final String string = Pref.sharedPreferences.getString(s, s2);
        Pref.sharedPreferences = null;
        return string;
    }

    public static void openPref(final Context context) {
        Pref.sharedPreferences = context.getSharedPreferences(Pref.MY_PREFS, 0);
    }

    public static void removeValue(final Context context, final String s) {
        openPref(context);
        final SharedPreferences.Editor edit = Pref.sharedPreferences.edit();
        edit.remove(s);
        edit.apply();
        Pref.sharedPreferences = null;
    }

    public static void setBooleanValue(final Context context, final String s, final boolean b) {
        openPref(context);
        final SharedPreferences.Editor edit = Pref.sharedPreferences.edit();
        edit.putBoolean(s, b);
        edit.apply();
        Pref.sharedPreferences = null;
    }

    public static void setIntValue(final Context context, final String s) {
    }

    public static void setIntValue(final Context context, final String s, final int n) {
        openPref(context);
        final SharedPreferences.Editor edit = Pref.sharedPreferences.edit();
        edit.putInt(s, n);
        edit.apply();
        Pref.sharedPreferences = null;
    }

    public static void setStringValue(final Context context, final String s, final String s2) {
        openPref(context);
        final SharedPreferences.Editor edit = Pref.sharedPreferences.edit();
        edit.putString(s, s2);
        edit.apply();
        Pref.sharedPreferences = null;
    }
}
