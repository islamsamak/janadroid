/**
 *
 */
package com.jana.android.core.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Islam Samak : islamsamak01@gmail.com
 */
public class HashingUtils {

    public static String toSha1(String text) throws UnsupportedEncodingException,
            NoSuchAlgorithmException, InvalidKeyException {

        MessageDigest digest = MessageDigest.getInstance("SHA-1");

        digest.reset();

        byte[] data = digest.digest(text.getBytes("UTF8"));

        StringBuilder hash = new StringBuilder();

        for (byte item : data) {

            String h = Integer.toHexString(0xFF & item);

            while (h.length() < 2) {
                h = "0" + h;
            }

            hash.append(h);
        }

        return hash.toString();
    }
}
