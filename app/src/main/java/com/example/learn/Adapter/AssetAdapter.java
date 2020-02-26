package com.example.learn.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.learn.Helper.ItemClick;
import com.example.learn.R;
import com.example.learn.bean.Asset;
import com.example.learn.bean.Format;

import java.util.List;

public class AssetAdapter extends RecyclerView.Adapter<AssetAdapter.ViewHolder>  {
    Context context;
    List<Asset> assetList;
    ItemClick mlistner;


    public AssetAdapter(Context context, List<Asset> assetList , ItemClick listner) {
        this.context = context;
        this.assetList = assetList;
        this.mlistner = listner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item_asset , parent , false);
        ViewHolder viewHolder = new ViewHolder(v , mlistner);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Asset asset = assetList.get(position);

      //  checkAsset(asset , position);
        holder.textView.setText(asset.getDisplayName());
        Glide.with(context)
                .load(asset.getThumbnail().getUrl())
                .into(holder.imageView);


    }

    private void checkAsset(Asset asset , int position) {

        List<Format> formatLists = asset.getFormatList();
        for (Format format : formatLists) {
            if (!(format.getFormatType().contains("GLTF"))) {
                assetList.remove(position);
                notifyDataSetChanged();

            }
        }
    }

    @Override
    public int getItemCount() {
        return assetList.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView;
        ItemClick listner;
        public ViewHolder(@NonNull View itemView , ItemClick listner) {
            super(itemView);
            imageView = itemView.findViewById(R.id.assetImage);
            textView = itemView.findViewById(R.id.assetName);
            this.listner = listner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.e("item " , "item is clicked");
            listner.ItemClick(assetList.get(getAdapterPosition()));
        }
    }


}
