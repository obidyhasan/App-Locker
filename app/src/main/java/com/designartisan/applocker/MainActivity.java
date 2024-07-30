package com.designartisan.applocker;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.designartisan.applocker.Utils.AccessibilityServiceUtils;
import com.designartisan.applocker.Utils.PermissionUtils;
import com.designartisan.applocker.fragment.LockedFragment;
import com.designartisan.applocker.fragment.UnlockedFragment;
import com.designartisan.applocker.service.AppLockService;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Check Accessibility Permission
        accessibilityPermission();


        tabLayout = findViewById(R.id.tabLayoutId);
        viewPager = findViewById(R.id.viewPagerId);

        adapter = new MainAdapter(getSupportFragmentManager());
        // Add Fragment
        adapter.AddFragment(new UnlockedFragment(), "Unlocked");
        adapter.AddFragment(new LockedFragment(), "Locked");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);
    }

    public class MainAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        ArrayList<String> stringArrayList = new ArrayList<>();

        // Create Constructor

        public void AddFragment(Fragment fragment, String string){
            fragmentArrayList.add(fragment);
            stringArrayList.add(string);
        }

        public MainAdapter(@NonNull FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return stringArrayList.get(position);
        }
    }


    public void accessibilityPermission(){

        if (!AccessibilityServiceUtils.isAccessibilityServiceEnabled(this, AppLockService.class)) {
            // Show a dialog to the user explaining why the permission is needed
            new AlertDialog.Builder(this)
                    .setTitle("Accessibility Permission Required")
                    .setMessage("This app needs accessibility permission to lock apps.")
                    .setPositiveButton("Grant Permission", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AccessibilityServiceUtils.requestAccessibilityPermission(MainActivity.this);
                        }
                    })
                    .setCancelable(false)
                    .show();
        }

        if (!PermissionUtils.checkDrawOverlayPermission(this)) {
            PermissionUtils.requestDrawOverlayPermission(this);
        }

    }

}