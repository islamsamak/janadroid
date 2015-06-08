/**
 *
 */
package com.jana.android.core.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;

/**
 * @author isamak
 */
public class DeviceInfo {

    public static final String WIFI_NETWORK_INTERFACE = "wlan0";

    public static final String ETHERNET_NETWORK_INTERFACE = "eth0";

    public static String getUniqueId() {

        String uniqueId = getMACAddress(ETHERNET_NETWORK_INTERFACE);

        if (!TextUtils.isEmpty(uniqueId)) {
            return hashId(uniqueId);
        }

        uniqueId = getMACAddress(WIFI_NETWORK_INTERFACE);

        if (!TextUtils.isEmpty(uniqueId)) {
            return hashId(uniqueId);
        }

        if (TextUtils.isEmpty(Build.SERIAL)) {
            return hashId(Build.SERIAL);
        }

        return Settings.Secure.ANDROID_ID;
    }

    protected static String hashId(String id) {
        String hash = id;
        try {
            hash = HashingUtils.toSha1(id);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

    /**
     * Returns MAC address of the given interface name.
     *
     * @param interfaceName eth0, wlan0 or NULL=use first interface
     * @return mac address or empty string
     */
    public static String getMACAddress(String interfaceName) {

        try {
            List<NetworkInterface> interfaces = Collections
                    .list(NetworkInterface.getNetworkInterfaces());

            for (NetworkInterface intf : interfaces) {

                if (interfaceName != null) {

                    if (!intf.getName().equalsIgnoreCase(interfaceName)) {
                        continue;
                    }
                }

                byte[] mac = intf.getHardwareAddress();

                if (mac == null) {
                    return "";
                }

                StringBuilder buf = new StringBuilder();

                for (byte item : mac) {
                    buf.append(String.format("%02X:", item));
                }

                if (buf.length() > 0) {
                    buf.deleteCharAt(buf.length() - 1);
                }

                return buf.toString();
            }
        } catch (Exception ex) {
            return "";
        }
        return "";
    }

    private static PackageInfo getPackageInfo(final Context context)
            throws NameNotFoundException {

        PackageManager pkgManager = context.getPackageManager();

        return pkgManager.getPackageInfo(context.getPackageName(), 0);
    }

    public static String getPackageVersionName(final Context context)
            throws NameNotFoundException {

        PackageInfo info = getPackageInfo(context);

        return info.versionName;
    }

    public static int getPackageVersionCode(final Context context)
            throws NameNotFoundException {

        PackageInfo info = getPackageInfo(context);

        return info.versionCode;
    }

    public static String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getDeviceName() {
        return Build.DEVICE;
    }

    public static String getDeviceModel() {
        return Build.MODEL;
    }

    public static String getProduct() {
        return Build.PRODUCT;
    }

    public static String getPlatformVersion() {
        return Build.VERSION.RELEASE;
    }

}
