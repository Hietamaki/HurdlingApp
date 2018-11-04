package com.example.sloth.hurdlingapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class StoredData {
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    /**
     * Create an instance for saving/loading SharedPreferences.
     * Currenly SharedPreferences holds one integer.
     * @param activity Current Activity
     */
    public StoredData(Activity activity) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = sharedPreferences.edit();
    }

    /**
     * Get a index that should be unique. Use {@link #changeVideoIndex()} after actual usage.
     * @return Index that should be unique. Use {@link #changeVideoIndex()} after actual usage.
     */
    public int getVideoIndex() {
        return sharedPreferences.getInt(Constants.INDEX_S, 0);
    }

    /**
     * Increase videoIndex by one and store it. So nothing will use the same index.
     */
    public void changeVideoIndex() {
        int oldVideoIndex = sharedPreferences.getInt(Constants.INDEX_S, 0);
        oldVideoIndex++;
        editor.putInt(Constants.INDEX_S, oldVideoIndex);
        editor.apply();
    }

}
