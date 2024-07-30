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
import android.widget.EditText;
import android.widget.RelativeLayout;
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


            EditText editText = lockScreenView.findViewById(R.id.editText);
            RelativeLayout one, two, three, four, five, six, seven, eight, nine, zero, delete;

            one = lockScreenView.findViewById(R.id.oneNumber);
            two = lockScreenView.findViewById(R.id.twoNumber);
            three = lockScreenView.findViewById(R.id.threeNumber);
            four = lockScreenView.findViewById(R.id.fourNumber);
            five = lockScreenView.findViewById(R.id.fiveNumber);
            six = lockScreenView.findViewById(R.id.sixNumber);
            seven = lockScreenView.findViewById(R.id.sevenNumber);
            eight = lockScreenView.findViewById(R.id.eightNumber);
            nine = lockScreenView.findViewById(R.id.nineNumber);
            zero = lockScreenView.findViewById(R.id.zeroNumber);
            delete = lockScreenView.findViewById(R.id.deleteBtn);

            one.setOnClickListener(v -> {

                editText.setText(editText.getText().toString()+"1");
            });

            two.setOnClickListener(v -> {
                editText.setText(editText.getText().toString()+"2");
            });

            three.setOnClickListener(v -> {
                editText.setText(editText.getText().toString()+"3");
            });

            four.setOnClickListener(v -> {
                editText.setText(editText.getText().toString()+"4");
            });

            five.setOnClickListener(v -> {
                editText.setText(editText.getText().toString()+"5");
            });

            six.setOnClickListener(v -> {
                editText.setText(editText.getText().toString()+"6");
            });

            seven.setOnClickListener(v -> {
                editText.setText(editText.getText().toString()+"7");
            });

            eight.setOnClickListener(v -> {
                editText.setText(editText.getText().toString()+"8");
            });

            nine.setOnClickListener(v -> {
                editText.setText(editText.getText().toString()+"9");
            });

            zero.setOnClickListener(v -> {
                editText.setText(editText.getText().toString()+"0");
            });

            delete.setOnClickListener(v -> {
                editText.setText(null);
            });

            lockScreenView.findViewById(R.id.unlockedButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (editText.getText().toString().equals("4310")){
                        removeLockScreen();
                    }
                    else{
                        editText.setError("নাটক কম করো পিও");
                        editText.setText(null);
                    }

//                    removeLockScreen();

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
