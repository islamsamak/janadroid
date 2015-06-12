/**
 *
 */
package com.jana.android.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * @author islam
 */
public class LocaleManager {

    public static void setLocale(Context context, String newLocale) {

        Resources res = context.getResources();

        DisplayMetrics dm = res.getDisplayMetrics();

        Configuration config = res.getConfiguration();

        config.locale = new Locale(newLocale.toLowerCase());

        res.updateConfiguration(config, dm);
    }

    public static String getLanguage(Context context) {

        Resources res = context.getResources();

        Configuration config = res.getConfiguration();

        String lang = config.locale.getLanguage();

        return lang;
    }

    public static boolean isRtL(Context context) {

        Resources res = context.getResources();

        Configuration config = res.getConfiguration();

        Locale locale = config.locale;

        final int directionality = Character.getDirectionality(locale
                .getDisplayName(locale).charAt(0));

        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT
                || directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }

}
