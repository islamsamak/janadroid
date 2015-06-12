/**
 *
 */
package com.jana.android.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.lang.reflect.Constructor;

/**
 * @author Islam Samak | islamsamak01@gmail.com
 */
public class UserAgentUtils {

    // You may uncomment next line if using Android Annotations library,
    // otherwise just be sure to run it in the UI thread
    // @UiThread
    public static String getDefaultUserAgent(final Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return getUserAgent(context);
        }

        try {
            Constructor<WebSettings> constructor = WebSettings.class
                    .getDeclaredConstructor(Context.class, WebView.class);

            constructor.setAccessible(true);

            try {

                WebSettings settings = constructor.newInstance(context, null);

                return settings.getUserAgentString();

            } finally {
                constructor.setAccessible(false);
            }
        } catch (Exception e) {
            return new WebView(context).getSettings().getUserAgentString();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    protected static String getUserAgent(final Context context) {
        return WebSettings.getDefaultUserAgent(context);
    }
}
