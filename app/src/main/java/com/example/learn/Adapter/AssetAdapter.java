package com.example.learn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learn.R;
import com.example.learn.bean.Asset;

import org.w3c.dom.Text;

import java.util.List;

public class AssetAdapter extends RecyclerView.Adapter<AssetAdapter.ViewHolder>{
    Context context;
    List<Asset> assetList;

    public AssetAdapter(Context context, List<Asset> assetList) {
        this.context = context;
        this.assetList = assetList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item_asset , parent , false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Asset asset = assetList.get(position);
        holder.textView.setText(asset.getDisplayName());

    }

    @Override
    public int getItemCount() {
        return assetList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.assetImage);
            textView = itemView.findViewById(R.id.assetName);
        }
    }
}
