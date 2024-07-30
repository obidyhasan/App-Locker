package com.designartisan.applocker.Utils;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;

public class AccessibilityServiceUtils {

    public static boolean isAccessibilityServiceEnabled(Context context, Class<? extends AccessibilityService> service) {
        String serviceId = context.getPackageName() + "/" + service.getName();
        TextUtils.SimpleStringSplitter colonSplitter = new TextUtils.SimpleStringSplitter(':');
        String enabledServices = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

        if (enabledServices != null) {
            colonSplitter.setString(enabledServices);
            while (colonSplitter.hasNext()) {
                String componentName = colonSplitter.next();
                if (componentName.equalsIgnoreCase(serviceId)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void requestAccessibilityPermission(Context context) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        context.startActivity(intent);
    }

}
