package com.designartisan.applocker.fragment;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.designartisan.applocker.Helper.SharedPreferencesHelper;
import com.designartisan.applocker.Model.ItemModel;
import com.designartisan.applocker.R;
import com.designartisan.applocker.adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class LockedFragment extends Fragment {

    RecyclerView recyclerView;
    ItemAdapter adapter;
    List<ItemModel> itemModels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locked, container, false);


        // Recycler View
        recyclerView = view.findViewById(R.id.lockedRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ItemAdapter(itemModels);
        recyclerView.setAdapter(adapter);

        getLockedApp(view.getContext());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        itemModels.clear();
        getLockedApp(getContext());
    }


    public void getLockedApp(Context context){


        List<String> retrievedList = SharedPreferencesHelper.getArrayList(context);

        List<ApplicationInfo> packages = context.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages){

            String name = packageInfo.loadLabel(context.getPackageManager()).toString();
            String packageName = packageInfo.packageName;
            Drawable icon = packageInfo.loadIcon(context.getPackageManager());

            // Retrieve the ArrayList from SharedPreferences
            for (String item : retrievedList) {

                if (packageName.equals(item)) {
                    itemModels.add(new ItemModel(name, packageName, 1, icon));
                }

            }


        }


        adapter.notifyDataSetChanged();


    }

}