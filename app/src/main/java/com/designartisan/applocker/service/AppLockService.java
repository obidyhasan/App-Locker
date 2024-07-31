package com.designartisan.applocker.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.designartisan.applocker.Helper.SharedPreferencesHelper;
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
        List<String> retrievedList = SharedPreferencesHelper.getArrayList(this);
        return retrievedList.contains(packageName);
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
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                    PixelFormat.TRANSLUCENT);

            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            windowManager.addView(lockScreenView, layoutParams);

            setupLockScreenView(packageName);

        }
    }


    private void setupLockScreenView(String packageName) {
        EditText editText = lockScreenView.findViewById(R.id.editText);
        RelativeLayout one = lockScreenView.findViewById(R.id.oneNumber);
        RelativeLayout two = lockScreenView.findViewById(R.id.twoNumber);
        RelativeLayout three = lockScreenView.findViewById(R.id.threeNumber);
        RelativeLayout four = lockScreenView.findViewById(R.id.fourNumber);
        RelativeLayout five = lockScreenView.findViewById(R.id.fiveNumber);
        RelativeLayout six = lockScreenView.findViewById(R.id.sixNumber);
        RelativeLayout seven = lockScreenView.findViewById(R.id.sevenNumber);
        RelativeLayout eight = lockScreenView.findViewById(R.id.eightNumber);
        RelativeLayout nine = lockScreenView.findViewById(R.id.nineNumber);
        RelativeLayout zero = lockScreenView.findViewById(R.id.zeroNumber);
        RelativeLayout delete = lockScreenView.findViewById(R.id.deleteBtn);


        one.setOnClickListener(v -> editText.append("1"));
        two.setOnClickListener(v -> editText.append("2"));
        three.setOnClickListener(v -> editText.append("3"));
        four.setOnClickListener(v -> editText.append("4"));
        five.setOnClickListener(v -> editText.append("5"));
        six.setOnClickListener(v -> editText.append("6"));
        seven.setOnClickListener(v -> editText.append("7"));
        eight.setOnClickListener(v -> editText.append("8"));
        nine.setOnClickListener(v -> editText.append("9"));
        zero.setOnClickListener(v -> editText.append("0"));
        delete.setOnClickListener(v -> {
            editText.setText(null);
        });

        lockScreenView.findViewById(R.id.unlockedButton).setOnClickListener(v -> {
            if (editText.getText().toString().equals("4310")) {
                removeLockScreen();
            } else {
                editText.setError("Invalid code");
                editText.setText(null);
            }

        });

        lockScreenView.findViewById(R.id.closeBtn).setOnClickListener(v -> {
            handleBackPress();
        });

        lockScreenView.setOnTouchListener((v, event) -> true);

        // Handle the back button press by simulating a home action
        lockScreenView.setFocusableInTouchMode(true);
        lockScreenView.requestFocus();
        lockScreenView.setOnKeyListener((v, keyCode, event) -> {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK && event.getAction() == android.view.KeyEvent.ACTION_UP) {
        handleBackPress();
        return true;
        }
        return false;
        });

    }


    private void handleBackPress() {
        performGlobalAction(GLOBAL_ACTION_HOME);
        removeLockScreen();
    }


    private void removeLockScreen() {
        if (lockScreenView != null && windowManager != null) {
            windowManager.removeView(lockScreenView);
            lockScreenView = null;
        }
    }


}
