package com.udacity.garuolis.bakingapp.utils;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.URLUtil;

public class ApiUtils {
    public final static String API_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    private static final String[] VALID_IMAGE_EXTENSIONS = new String[]{"jpg", "jpeg", "png"};
    private static final String[] VALID_VIDEO_EXTENSIONS = new String[]{"mp4"};

    public static boolean ValidImageUrl(String url) {
        return ValidUrl(url, VALID_IMAGE_EXTENSIONS);
    }

    public static boolean ValidVideoUrl(String url) {
        return ValidUrl(url, VALID_VIDEO_EXTENSIONS);
    }

    public static boolean ValidUrl(String url, String[] extArray) {
        if (!TextUtils.isEmpty(url) && URLUtil.isValidUrl(url)) {
            String extension = url.substring(url.lastIndexOf(".") + 1);
            for (int i = 0; i < extArray.length; i++) {
                if (extension.equalsIgnoreCase(extArray[i])) return true;
            }
        }
        return false;
    }
}
