package com.example.myapplication.ui.Tickets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemTicketsBinding;
import com.google.android.material.button.MaterialButton;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> events;

    public EventAdapter(List<Event> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTicketsBinding binding = ItemTicketsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EventViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);
        holder.bind(event);
    }

    @Override
    public int getItemCount() {
        return events != null ? events.size() : 0;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        private final ItemTicketsBinding binding;

        public EventViewHolder(ItemTicketsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final Event event) {
            binding.tvMonth.setText(event.getMonth());
            binding.tvDay.setText(event.getDay());
            binding.tvTitle.setText(event.getTitle());
            binding.tvTime.setText(event.getTime());

            if (event.isPurchased()) {
                binding.btnPurchase.setVisibility(View.GONE);
                binding.btnPurchased.setVisibility(View.VISIBLE);
            } else {
                binding.btnPurchase.setVisibility(View.VISIBLE);
                binding.btnPurchased.setVisibility(View.GONE);
            }

            binding.btnPurchase.setOnClickListener(v -> {
                // Here you would handle the purchase logic, then update the UI
                event.setPurchased(true);
                binding.btnPurchase.setVisibility(View.GONE);
                binding.btnPurchased.setVisibility(View.VISIBLE);
            });
        }
    }
}