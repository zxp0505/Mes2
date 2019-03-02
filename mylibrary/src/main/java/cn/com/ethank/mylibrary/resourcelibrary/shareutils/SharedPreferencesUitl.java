package cn.com.ethank.mylibrary.resourcelibrary.shareutils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import cn.com.ethank.mylibrary.resourcelibrary.application.DefaultApplication;


public class SharedPreferencesUitl {
    private static SharedPreferences sharedPreferences;
    private static Editor editor = null;

    public static void saveStringData(String key, String value) {
        initSharePreference();
        editor.putString(key, value).commit();
    }

    public static void saveIntData(String key, int value) {
        initSharePreference();
        editor.putInt(key, value).commit();
    }

    public static void saveBooleanData(String key, boolean value) {
        initSharePreference();
        editor.putBoolean(key, value).commit();
    }

    public static boolean getBooleanData(String key) {
        initSharePreference();
        return sharedPreferences.getBoolean(key, false);
    }

    public static String getStringData(String key) {
        initSharePreference();
        return sharedPreferences.getString(key, "");
    }

    public static int getIntData(String key) {
        initSharePreference();
        return sharedPreferences.getInt(key, 0);
    }

    public static void deleteData(String key) {
        initSharePreference();
        editor.remove(key).commit();
    }

    public static void clearAllData() {
        initSharePreference();
        editor.clear().commit();
        // Map<String, ?> map = sharedPreferences.getAll();
        // for (Map.Entry<String, ?> kv : map.entrySet()) {
        // sharedPreferences.edit().remove(kv.getKey()).commit();
        // }
    }

    @SuppressLint("CommitPrefEdits")
    private static void initSharePreference() {
        if (sharedPreferences == null) {
            sharedPreferences = DefaultApplication.getInstance().getApplicationContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        if (editor == null) {
            editor = sharedPreferences.edit();
        }
    }
}
