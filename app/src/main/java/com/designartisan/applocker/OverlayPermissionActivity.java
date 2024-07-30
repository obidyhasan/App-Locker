package com.designartisan.applocker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.designartisan.applocker.Utils.PermissionUtils;

public class OverlayPermissionActivity extends AppCompatActivity {

    ImageView appImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_overlay_permission);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        appImg = findViewById(R.id.appIconOverlay);
        appImg.setClipToOutline(true);

        if (PermissionUtils.checkDrawOverlayPermission(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (PermissionUtils.checkDrawOverlayPermission(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

    }

    public void overlayPermissionBtn(View view) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getApplicationContext().getPackageName()));
        startActivity(intent);
    }




}