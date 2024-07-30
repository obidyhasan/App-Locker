package com.designartisan.applocker.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import com.designartisan.applocker.Helper.SharedPreferencesHelper;
import com.designartisan.applocker.LockScreenActivity;
import com.designartisan.applocker.R;

import java.util.List;

public class AppLockService extends AccessibilityService {

    private static final String TAG = "AppLockService";

    private WindowManager windowManager;
    private View lockScreenView;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            String packageName = event.getPackageName().toString();
            if (shouldLockApp(packageName)) {
                showLockScreen(packageName);
            }
        }
    }

    @Override
    public void onInterrupt() {
        // Handle interrupt
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
    }

    private boolean shouldLockApp(String packageName) {
        // Example: Lock only Facebook app
        List<String> retrievedList = SharedPreferencesHelper.getArrayList(this);
        for (String item : retrievedList){

            if(packageName.equals(item)){
                return true;
            }

        }

        return false;

    }

    private void showLockScreen(String packageName) {
        if (lockScreenView == null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            lockScreenView = inflater.inflate(R.layout.activity_lock_screen, null);

            int layoutParamsType;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                layoutParamsType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                layoutParamsType = WindowManager.LayoutParams.TYPE_PHONE;
            }

            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    layoutParamsType,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSLUCENT);

            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            windowManager.addView(lockScreenView, layoutParams);

            lockScreenView.findViewById(R.id.unlockedButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeLockScreen();
                }
            });
        }
    }

    private void removeLockScreen() {
        if (lockScreenView != null && windowManager != null) {
            windowManager.removeView(lockScreenView);
            lockScreenView = null;
        }
    }

//    private void showLockScreen(String packageName) {
//        // Launch the lock screen activity
//        Intent intent = new Intent(this, LockScreenActivity.class);
//        intent.putExtra("package_name", packageName);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }


}
