package com.iniongun.dashboards.utilities;

import android.content.Context;

public class Utils {

    public static SecurePreferences getSecurePreferences(Context context){
        return new SecurePreferences(context, Constants.PREFERENCE_NAME, Constants.DASHBOARD_PREFERENCE_KEY, true);
    }

}
