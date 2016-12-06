package com.mateus.popularmovies.main.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mateus on 04/12/16.
 */
public abstract class Utility {

    public static boolean addFavoriteItem(Activity activity,String favoriteItem){
        //Get previous favorite items
        String favoriteList = getStringFromPreferences(activity,null,"favorites");
        // Append new Favorite item
        if(favoriteList!=null){
            favoriteList = favoriteList+","+favoriteItem;
        }else{
            favoriteList = favoriteItem;
        }
        // Save in Shared Preferences
        return putStringInPreferences(activity,favoriteList,"favorites");
    }

    public static void removeFavoriteItem(Activity ac, String favoriteItem) {
        //Get previous favorite items
        String favoriteList = getStringFromPreferences(ac,null,"favorites");

        String[] newFavoriteList = ArrayUtils.removeElement(convertStringToArray(favoriteList),favoriteItem);

        putStringInPreferences(ac,newFavoriteList.toString(),"favorites");
    }
    private static String[] getFavoriteList(Activity activity){
        String favoriteList = getStringFromPreferences(activity,null,"favorites");
        if (favoriteList!=null) {
            return convertStringToArray(favoriteList);
        }
        return null;
    }
    private static boolean putStringInPreferences(Activity activity,String nick,String key){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, nick);
        editor.commit();
        return true;
    }
    private static String getStringFromPreferences(Activity activity,String defaultValue,String key){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String temp = sharedPreferences.getString(key, defaultValue);
        return temp;
    }

    private static String[] convertStringToArray(String str){
        String[] arr = str.split(",");
        return arr;
    }

    public static boolean isFavorited(Activity ac, String movieID) {
        String[] list = getFavoriteList(ac);
        if (list!=null) {
            return Arrays.asList(list).contains(movieID);
        }
        return false;
    }

}

