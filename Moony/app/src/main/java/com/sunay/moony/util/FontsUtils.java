package com.sunay.moony.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Created by krasimir.karamazov on 7/14/2015.
 */
public class FontsUtils {
    public static final String ROBOTO_PATH_FORMAT = "fonts/Roboto_%s.ttf";
    public static final String GOTHAM_PATH_FORMAT = "fonts/GothamRnd-%s.otf";
    public static final String OPEN_SANS_PATH_FORMAT = "fonts/OpenSans-%s.ttf";
    public static final String COMFORTAA_PATH_FORMAT = "fonts/Comfortaa-%s.ttf";

    public static final String GOTHAM_VARIANT_BOLD = "Bold";
    public static final String GOTHAM_VARIANT_BOLD_ITALIC = "BoldIta";
    public static final String GOTHAM_VARIANT_BOOK = "Book";
    public static final String GOTHAM_VARIANT_BOOK_ITALIC = "BookIta";
    public static final String GOTHAM_VARIANT_BOOK_LIGHT = "Light";
    public static final String GOTHAM_VARIANT_BOOK_LIGHT_ITALIC = "LightIta";
    public static final String GOTHAM_VARIANT_BOOK_LIGHT_MEDIUM_ITALIC = "MedIta";
    public static final String GOTHAM_VARIANT_BOOK_LIGHT_MEDIUM = "Medium";

    public static final String OPEN_SANS_VARIANT_BOLD = "Bold";
    public static final String OPEN_SANS_VARIANT_REGULAR = "Regular";

    public static final String COMFORTAA_VARIANT_BOLD = "Bold";
    public static final String COMFORTAA_VARIANT_REGULAR = "Regular";

    public static final String ROBOTO_VARIANT_BLACK = "Black";
    public static final String ROBOTO_VARIANT_BLACK_ITALIC = "BlackItalic";
    public static final String ROBOTO_VARIANT_BOLD = "Bold";
    public static final String ROBOTO_VARIANT_BOLD_ITALIC = "BoldItalic";
    public static final String ROBOTO_VARIANT_BOLD_CONDENSED = "BoldCondensed";
    public static final String ROBOTO_VARIANT_BOLD_CONDENSED_ITALIC = "BoldCondensedItalic";
    public static final String ROBOTO_VARIANT_CONDENSED = "Condensed";
    public static final String ROBOTO_VARIANT_CONDENSED_ITALIC = "CondensedItalic";
    public static final String ROBOTO_VARIANT_REGULAR= "Regular";
    public static final String ROBOTO_VARIANT_REGULAR_ITALIC = "Italic";
    public static final String ROBOTO_VARIANT_LIGHT = "Light";
    public static final String ROBOTO_VARIANT_LIGHT_ITALIC = "LightItalic";
    public static final String ROBOTO_VARIANT_MEDIUM = "Medium";
    public static final String ROBOTO_VARIANT_MEDIUM_ITALIC = "MediumItalic";
    public static final String ROBOTO_VARIANT_THIN = "Thin";
    public static final String ROBOTO_VARIANT_THIN_ITALIC = "ThinItalic";

    private final AssetManager assets;
    private Context appContext;
    private static HashMap<String, Typeface> cache = new HashMap<String, Typeface>();
    private static FontsUtils sInstance;
    public static FontsUtils getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new FontsUtils(context);
        }

        return sInstance;
    }

    private FontsUtils(Context context) {
        this.appContext = context;
        this.assets = appContext.getAssets();
    }

    public Typeface get(String assetPath, String variant) {
        synchronized (cache) {
            if (!cache.containsKey(assetPath)) {
                try {
                    Typeface t = Typeface.createFromAsset(this.assets,
                            String.format(assetPath, variant));
                    cache.put(assetPath, t);
                } catch (Exception e) {
                    Logger.e("Could not get typeface '" + assetPath
                            + "' because " + e.getMessage());
                    return null;
                }
            }
            return cache.get(assetPath);
        }
    }
}
