package com.rtovehicleinformation.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import java.util.Locale;

public class Const {
    public static String PREF_LAST_CITY = "PREF_LAST_CITY";
    public static String PREF_LAST_COUNTRY = "PREF_LAST_COUNTRY";

    private static Context get(final Context context, final String s) {
        final Locale locale = new Locale(s);
        Locale.setDefault(locale);
        final Resources resources = context.getResources();
        final Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }

    public static Context getSet(final Context context, final String s) {
        set(context, s);
        return get(context, s);
    }

    private static void set(final Context context, final String s) {
        final SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putString("Locale.Helper.Selected.Language", s);
        edit.apply();
    }
}
