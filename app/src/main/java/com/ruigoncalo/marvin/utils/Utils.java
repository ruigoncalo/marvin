package com.ruigoncalo.marvin.utils;

import android.support.annotation.NonNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import timber.log.Timber;

/**
 * Created by ruigoncalo on 21/04/16.
 */
public class Utils {

    private Utils(){

    }

    public static int getTimestamp() {
        return (int) System.currentTimeMillis();
    }

    /**
     * From: http://stackoverflow.com/questions/4846484/md5-hashing-in-android
     * @param raw string to digest
     * @return raw md5 hash
     */
    @NonNull
    public static String md5(@NonNull String raw){
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(raw.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            Timber.e("Calculating md5 failure");
            return "";
        }
    }
}
