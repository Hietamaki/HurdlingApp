package com.example.sloth.hurdlingapp;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.content.Context;

public class IdManager {


    public static IdManager Instance = new IdManager();
    public static final String INDEX_PREFERENCE = "index";

    public IdManager()
    {
        if(Instance==null) {
        Instance = this;
        }
    }
public String getUniqueId(Context context)
{
    String androidId = Settings.Secure.getString(context.getContentResolver(),
            Settings.Secure.ANDROID_ID);
    return androidId;
}
}
