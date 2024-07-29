package com.designartisan.applocker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.designartisan.applocker.Model.ItemModel;
import com.designartisan.applocker.R;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    List<ItemModel> modelList = new ArrayList<>();

    public ItemAdapter(List<ItemModel> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {

        ItemModel itemModel = modelList.get(position);

        holder.appName.setText(itemModel.getAppName());
        holder.appIcon.setImageDrawable(itemModel.getAppIcon());
        holder.packageName.setText(itemModel.getPackageName());

        if (itemModel.getStatus() == 0){
            holder.statusImage.setImageResource(R.drawable.unlock);
        }
        else {
            holder.statusImage.setImageResource(R.drawable.lock);
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView appName, packageName;
        ImageView appIcon, statusImage;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            appName = itemView.findViewById(R.id.itemAppName);
            packageName = itemView.findViewById(R.id.itemPackageName);
            appIcon = itemView.findViewById(R.id.itemIconImg);
            statusImage = itemView.findViewById(R.id.itemStatusImg);

        }
    }
}
