/*
 * Copyright (C) 2013 OneTeam (IslamSamak : islamsamak01@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jana.android.utils;

import android.text.TextUtils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class ArabicNormalization {

    /**
     * Normalize Arabic string by removing Honorific Signs, Quaranic
     * Annotations, And Diacritics, Tatweel
     *
     * @param text is the non normalized string
     * @return a new normalized string
     */
    public static String normalizeArabic(String text) {

        if (TextUtils.isEmpty(text)) {
            return text;
        }

        StringBuilder sClearedString = new StringBuilder(50);

        char ch;

        boolean isSpecial;

        for (int i = 0; i < text.length(); i++) {

            ch = text.charAt(i);

            isSpecial = isHonorificSign(ch);
            isSpecial |= isQuranic(ch);
            isSpecial |= isTatweel(ch);
            isSpecial |= isDiacritics(ch);

            if (!isSpecial) {
                sClearedString.append(ch);
            }
        }

        String sOutString = null;

        if (sClearedString.length() > 0) {
            sOutString = sClearedString.toString();
        }

        return sOutString;
    }

    public static String injectArabicAlternatives(String text) {

        if (TextUtils.isEmpty(text)) {
            return text;
        }

        StringBuilder sClearedString = new StringBuilder(50);

        char ch;

        for (int i = 0; i < text.length(); i++) {
            ch = text.charAt(i);
            ch = getCharAlternatives(ch);
            sClearedString.append(ch);
        }

        String sOutString = sClearedString.toString();
        return sOutString;
    }

    /**
     * Remove diacritics from string
     *
     * @param text is the string with diacritics
     * @return a new string without diacritics
     */
    public static String removeDiacritics(String text) {

        if (TextUtils.isEmpty(text)) {
            return text;
        }

        StringBuilder sClearedString = new StringBuilder(50);

        char ch;

        boolean isSpecial;

        for (int i = 0; i < text.length(); i++) {

            ch = text.charAt(i);

            isSpecial = isDiacritics(ch);

            if (!isSpecial) {
                sClearedString.append(ch);
            }
        }

        String sOutString = null;

        if (sClearedString.length() > 0) {
            sOutString = sClearedString.toString();
        }

        return sOutString;
    }

    private static boolean isTatweel(char ch) {
        return (ch == 1600);
    }

    private static boolean isQuranic(char ch) {
        return (ch >= 1611 && ch <= 1631) || ch == 1648;
    }

    private static boolean isHonorificSign(char ch) {
        return (ch >= 1552 && ch <= 1556);
    }

    private static boolean isDiacritics(int ch) {

        return (ch >= 1611 && ch <= 1618) || ch == 65140
                || (ch >= 65142 && ch <= 65151) || (ch >= 64754 && ch <= 64756)
                || (ch >= 65136 && ch <= 65138);
    }

    /**
     * Remove diacritics from string
     *
     * @param sText is the string with diacritics
     * @return a new string without diacritics
     */
    public static String removeDiacriticsPattern(String sText) {

        String nfdNormalizedText = normalizeTextNFD(sText);

        Pattern pattern = Pattern.compile("\\p{Mn}+");

        return pattern.matcher(nfdNormalizedText).replaceAll("");
    }

    /**
     * Normalize the given text using NFKD
     *
     * @param sText is the raw string
     * @return a normalized string
     */
    public static String normalizeTextNFKD(String text) {

        String normalizedText = Normalizer
                .normalize(text, Normalizer.Form.NFKD);

        return normalizedText;
    }

    /**
     * Normalize the given text using NFD
     *
     * @param sText is the raw string
     * @return a normalized string
     */
    public static String normalizeTextNFD(String text) {

        String normalizedText = Normalizer.normalize(text, Normalizer.Form.NFD);

        return normalizedText;
    }

    public static String cleanText(String text) {

        if (isEmpty(text)) {
            return text;
        }

        String nText = normalizeTextNFD(text);

        // Remove diacritics
        nText = replaceDiacritics(nText, "");

        // Remove punctuations
        nText = replacePunctuations(nText, "");

        // Remove numbers
        nText = replaceNumbers(nText, "");

        // Remove symbols
        nText = replaceSymbols(nText, "");

        nText.trim();

        return nText;
    }

    public static String replaceSymbols(String normalizedText,
                                        String replaceWith) {

        String regex = "\\p{S}+";

        return applyRegex(normalizedText, replaceWith, regex);
    }

    public static String replaceNumbers(String normalizedText,
                                        String replaceWith) {

        String regex = "\\p{N}+";

        return applyRegex(normalizedText, replaceWith, regex);
    }

    public static String replacePunctuations(String normalizedText,
                                             String replaceWith) {

        String regex = "\\p{P}+";

        return applyRegex(normalizedText, replaceWith, regex);
    }

    public static String replaceDiacritics(String normalizedText,
                                           String replaceWith) {

        String regex = "\\p{Mn}+";

        return applyRegex(normalizedText, replaceWith, regex);
    }

    public static String replaceNewLines(String rawText, String replaceWith) {

        if (isEmpty(rawText) || replaceWith == null) {
            return rawText;
        }

        String cleanedText = rawText.replaceAll("\\r\\n", replaceWith);

        return cleanedText;
    }

    protected static String applyRegex(String normalizedText,
                                       String replaceWith, String regex) {

        if (isEmpty(normalizedText) || replaceWith == null || isEmpty(regex)) {
            return normalizedText;
        }

        Pattern pattern = Pattern.compile(regex);

        String cleanedText = pattern.matcher(normalizedText).replaceAll(
                replaceWith);

        return cleanedText;
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static char getCharAlternatives(char ch) {

        switch (ch) {

            // Alef
            case 0x0622:
            case 0x0623:
            case 0x0625:
            case 0x0627:
                // Return ALEF
                return 0x0627;

            // Hamza
            case 0x0621:
            case 0x0624:
            case 0x0626:
                // Return HAMZA
                return 0x0621;

            // Heh and Teh
            case 0x0647:
            case 0x0629:
                // Return TEH MARBUTA
                return 0x0629;

            // Yeh and Alef Maksura
            case 0x0649:
            case 0x064A:
                // Return ALEF MAKSURA
                return 0x0649;

            default:
                return ch;
        }
    }
}
