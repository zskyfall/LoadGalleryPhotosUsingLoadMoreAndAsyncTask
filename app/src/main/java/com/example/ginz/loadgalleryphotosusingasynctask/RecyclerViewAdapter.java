package com.example.ginz.loadgalleryphotosusingasynctask;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private List<File> mPhotos = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context context, List<File> mPhoto) {
        this.mContext = context;
        this.mPhotos = mPhoto;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.RecyclerViewHolder holder, int position) {
        //holder.image.setImageResource(mPhotos.get(position));
        Picasso.with(mContext).load(mPhotos.get(position)).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_gallery);
        }
    }
}
