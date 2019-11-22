package com.yin.hy.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SharedRefrenceUtl {
    public static void saveOnlyString(Context context, String fileName, int mode, String setName, Set<String> set){
        SharedPreferences.Editor editor = context.getSharedPreferences(fileName,mode).edit();
        editor.putStringSet(setName,set);
        editor.apply();
    }
}
