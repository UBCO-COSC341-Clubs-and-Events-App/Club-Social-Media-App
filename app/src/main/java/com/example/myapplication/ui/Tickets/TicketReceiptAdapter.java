package com.example.myapplication.ui.Tickets;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.databinding.ItemTicketReceiptBinding;
import java.util.List;

public class TicketReceiptAdapter extends RecyclerView.Adapter<TicketReceiptAdapter.TicketReceiptViewHolder> {

    private final List<TicketType> ticketTypes;

    public TicketReceiptAdapter(List<TicketType> ticketTypes) {
        this.ticketTypes = ticketTypes;
    }

    @NonNull
    @Override
    public TicketReceiptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTicketReceiptBinding binding = ItemTicketReceiptBinding.inflate(inflater, parent, false);
        return new TicketReceiptViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketReceiptViewHolder holder, int position) {
        TicketType ticketType = ticketTypes.get(position);
        holder.bind(ticketType);
    }

    @Override
    public int getItemCount() {
        return ticketTypes.size();
    }

    static class TicketReceiptViewHolder extends RecyclerView.ViewHolder {
        private final ItemTicketReceiptBinding binding;

        public TicketReceiptViewHolder(ItemTicketReceiptBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TicketType ticketType) {
            binding.tvQuantity.setText("x" + ticketType.getQuantity());
            binding.tvTicketName.setText(ticketType.getName());
            binding.tvTicketPrice.setText(String.format(java.util.Locale.US, "$%.2f", ticketType.getPrice()));
        }
    }
}