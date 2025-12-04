package com.example.myapplication.ui.Tickets;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemTicketBinding;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private final List<Ticket> tickets;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Ticket ticket);
    }

    public TicketAdapter(List<Ticket> tickets, OnItemClickListener listener) {
        this.tickets = tickets;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTicketBinding binding = ItemTicketBinding.inflate(inflater, parent, false);
        return new TicketViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket ticket = tickets.get(position);
        holder.bind(ticket, listener);
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        private final ItemTicketBinding binding;

        public TicketViewHolder(ItemTicketBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Ticket ticket, OnItemClickListener listener) {
            binding.tvDateMonth.setText(ticket.getMonth());
            binding.tvDateDay.setText(ticket.getDay());
            binding.tvEventTime.setText(ticket.getTime());
            binding.tvEventTitle.setText(ticket.getTitle());

            if (ticket.isBooked()) {
                binding.btnAction.setText("Booked");
                binding.btnAction.setEnabled(false);
            } else {
                binding.btnAction.setText("Add to Cart");
                binding.btnAction.setEnabled(true);
                binding.btnAction.setOnClickListener(v -> listener.onItemClick(ticket));
            }
        }
    }
}
