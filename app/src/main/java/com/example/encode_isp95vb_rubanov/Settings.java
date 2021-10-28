package com.example.encode_isp95vb_rubanov;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import java.util.Locale;

public class Settings {
    private static Integer mAudioVolume = 90;
    private static Locale mLocale = new Locale("ru");
    private static int mCurrLocaleResId = R.string.RussianLang;
    private static boolean isLoaded = false;

    @IntRange(from = 0, to = 100)
    public static int getAudioVolume(){
        return mAudioVolume;
    }

    public static void setAudioVolume(@NonNull Integer audioVolume){
        mAudioVolume = audioVolume;
    }

    public static Locale getLocale(){
        return mLocale;
    }

    public static void setLocale(@NonNull Locale currentLocale){
        mLocale = currentLocale;
    }

    public static int getCurrLocaleResId(){
        return mCurrLocaleResId;
    }

    public static void setCurrLocaleResId(@NonNull Integer currentLocale){
        mCurrLocaleResId = currentLocale;
    }

    public static boolean isLoaded() {
        return isLoaded;
    }

    public static void setLoaded(boolean b) {
        isLoaded = b;
    }
}
