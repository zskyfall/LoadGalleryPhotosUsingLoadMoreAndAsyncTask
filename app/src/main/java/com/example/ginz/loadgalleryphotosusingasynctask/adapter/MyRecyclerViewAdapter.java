package com.example.ginz.loadgalleryphotosusingasynctask.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.ginz.loadgalleryphotosusingasynctask.R;
import com.example.ginz.loadgalleryphotosusingasynctask.base.BaseRecyclerViewAdapter;
import com.example.ginz.loadgalleryphotosusingasynctask.base.LoadMoreRecyclerViewAdapter;
import com.example.ginz.loadgalleryphotosusingasynctask.model.Item;
import com.example.ginz.loadgalleryphotosusingasynctask.base.BaseRecyclerViewAdapter;
import com.example.ginz.loadgalleryphotosusingasynctask.base.LoadMoreRecyclerViewAdapter;
import com.example.ginz.loadgalleryphotosusingasynctask.model.Item;
import com.squareup.picasso.Picasso;

import java.io.File;

public class MyRecyclerViewAdapter extends LoadMoreRecyclerViewAdapter<Item> {
    private static final int TYPE_ITEM = 1;
    private Context mContext;

    public MyRecyclerViewAdapter(Context context, BaseRecyclerViewAdapter.ItemClickListener itemClickListener,
                                 RetryLoadMoreListener retryLoadMoreListener) {
        super(context, itemClickListener, retryLoadMoreListener);
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
            return new ItemViewHolder(view);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Item item = mDataList.get(position);
            Picasso.with(mContext).load(new File(item.getmPath())).into(((ItemViewHolder) holder).mImage);
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    protected int getCustomItemViewType(int position) {
        return TYPE_ITEM;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImage;

        ItemViewHolder(View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.image_gallery);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
}