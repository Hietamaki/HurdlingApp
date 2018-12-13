package com.example.sloth.hurdlingapp;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.content.Context;

public class IdManager {

    //Can be used to recognise devices apart.
    public static String getUniqueId(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return androidId;
    }
}
