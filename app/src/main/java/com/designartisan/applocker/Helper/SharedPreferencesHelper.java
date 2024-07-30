package com.designartisan.applocker.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SharedPreferencesHelper {

    private static final String PREF_NAME = "locked_apps";
    private static final String KEY_ARRAY_LIST = "locked_app_list";

    public static List<String> list = new ArrayList<>();


    public static void saveArrayList(Context context, String packageName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        list.add(packageName);

        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(KEY_ARRAY_LIST, json);
        editor.apply();

        for (String item : list){
            Toast.makeText(context, ""+item, Toast.LENGTH_SHORT).show();
        }

    }

    public static void deleteArrayList(Context context, String packageName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        list.remove(packageName);

        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(KEY_ARRAY_LIST, json);
        editor.apply();

        for (String item : list){
            Toast.makeText(context, ""+item, Toast.LENGTH_SHORT).show();
        }



    }

    public static List<String> getArrayList(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_ARRAY_LIST, null);

        if (json == null) {
            // If no data is found, return an empty list
            return new ArrayList<>();
        }

        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(json, type);
    }



}
