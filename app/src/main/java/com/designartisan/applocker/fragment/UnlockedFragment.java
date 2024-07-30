package com.designartisan.applocker.fragment;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.designartisan.applocker.Helper.SharedPreferencesHelper;
import com.designartisan.applocker.Model.ItemModel;
import com.designartisan.applocker.R;
import com.designartisan.applocker.adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class UnlockedFragment extends Fragment {

    RecyclerView recyclerView;
    List<ItemModel> itemModels = new ArrayList<>();
    ItemAdapter adapter;

    List<String> retrievedList = new ArrayList<>();
    SharedPreferencesHelper helper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unlocked, container, false);

        // Recycler View
        recyclerView = view.findViewById(R.id.unlockedRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ItemAdapter(itemModels);
        recyclerView.setAdapter(adapter);

        // Progress Bar
        getInstalledApp(view.getContext());

        return view;
    }



    public void getInstalledApp(Context context){



        List<ApplicationInfo> packages = context.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages){

            String name = packageInfo.loadLabel(context.getPackageManager()).toString();
            String packageName = packageInfo.packageName;
            Drawable icon = packageInfo.loadIcon(context.getPackageManager());

            if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0 || packageInfo.packageName.equals("com.android.settings")) {

                boolean check = true;

               retrievedList = SharedPreferencesHelper.getArrayList(context);

               for (String item : retrievedList){
                   if (packageName.equals(item)){
                       itemModels.add(new ItemModel(name, packageName, 1, icon));
                       check = false;
                       break;
                   }
               }

               if (check){
                   itemModels.add(new ItemModel(name, packageName, 0, icon));
               }

            }

        }


        adapter.notifyDataSetChanged();


    }
}