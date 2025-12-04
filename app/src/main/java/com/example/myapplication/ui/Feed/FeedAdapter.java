package com.example.myapplication.ui.Feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemFeedBinding;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ItemViewHolder>{

    private ArrayList<FeedItem> dataList;

    public FeedAdapter(ArrayList<FeedItem> dataList) {
        this.dataList = dataList;
    }

    public void filterList(ArrayList<FeedItem> filteredList) {
        this.dataList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFeedBinding binding = ItemFeedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        FeedItem item = dataList.get(position);
        holder.binding.clubImage.setImageResource(item.getClubImage());
        holder.binding.clubName.setText(item.getClubName());
        holder.binding.description.setText(item.getDescription());
        holder.binding.postImage.setImageResource(item.getPostImage());
        holder.binding.likeButton.setActivated(checkLiked());

        holder.binding.likeButton.setOnClickListener(view -> {
            int pos = holder.getBindingAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;
            addLikedPost(holder.binding.likeButton.isActivated(), dataList.get(pos).getPostId());
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addLikedPost(boolean isLiked, int postId){
        //check if in liked posts
        //remove if not liked, add if liked, else nothing
    }
    public boolean checkLiked(){
        //check if liked post
        return false;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder{

        ItemFeedBinding binding;

        public ItemViewHolder(ItemFeedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
