package com.jana.android.utils;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.text.TextUtils;

import com.jana.android.app.AbstractApplication;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

public abstract class AbstractTextStyle {

    protected static final String FONTS_PATH = "fonts/";

    protected static final String RTL_FONTS_PATH = FONTS_PATH + "rtl/";

    protected static final String LTR_FONTS_PATH = FONTS_PATH + "ltr/";

    private static NumberFormat sNumberFormat;

    protected HashMap<String, Typeface> typefaceMap;

    protected String defaultTypeface;

    protected AbstractTextStyle() {
        typefaceMap = new HashMap<String, Typeface>(10);
    }

    public static NumberFormat getNumberFormatInstance() {

        String locale = Locale.getDefault().getLanguage();

        if (sNumberFormat == null) {
            sNumberFormat = NumberFormat.getInstance(new Locale(locale));
        }

        return sNumberFormat;
    }

    public static NumberFormat getNumberFormatInstance(String locale) {

        if (sNumberFormat == null) {
            sNumberFormat = NumberFormat.getInstance(new Locale(locale));
        }

        return sNumberFormat;
    }

    public static String toArabicNumber(double number) {

        NumberFormat formatter = getNumberFormatInstance();

        String value = formatter.format(number);

        return value;
    }

    public static String toArabicNumber(String number) {

        NumberFormat formatter = getNumberFormatInstance();

        double num = Double.NaN;

        try {
            num = Double.parseDouble(number);
        } catch (NumberFormatException e) {
        }

        if (Double.isNaN(num)) {
            return number;
        }

        String value = formatter.format(num);

        return value;
    }

    public Typeface getDefaultTypeface() {
        if (TextUtils.isEmpty(defaultTypeface)) {
            Logger.w("WARNING: No default typeface is set");
            return null;
        }

        return typefaceMap.get(defaultTypeface);
    }

    public void setDefaultTypeface(String typefaceName) {
        this.defaultTypeface = typefaceName;
    }

    public Typeface getTypeface(String typefaceName) {

        Typeface typeface = typefaceMap.get(typefaceName);

        return typeface;
    }

    public void addTypeface(String key, String path) {
        Typeface typeface = createTypeface(path);
        typefaceMap.put(key, typeface);
    }

    public void addTypeface(String key, Typeface typeface) {
        typefaceMap.put(key, typeface);
    }

    public Typeface removeTypeface(String key) {
        return typefaceMap.remove(key);
    }

    protected final void addRtlTypeface(String typeface) {
        addTypeface(typeface, RTL_FONTS_PATH + typeface);
    }

    protected final void addLtrTypeface(String typeface) {
        addTypeface(typeface, LTR_FONTS_PATH + typeface);
    }

    public Typeface createTypeface(String path) {
        AssetManager assets = AbstractApplication.getApplication().getAssets();
        Typeface typeface = Typeface.createFromAsset(assets, path);
        return typeface;
    }
}
