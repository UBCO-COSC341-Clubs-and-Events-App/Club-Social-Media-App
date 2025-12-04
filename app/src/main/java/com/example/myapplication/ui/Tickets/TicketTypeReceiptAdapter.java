package com.example.myapplication.ui.Tickets;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.databinding.ItemTicketTypeReceiptBinding;
import java.text.NumberFormat;
import java.util.ArrayList;

public class TicketTypeReceiptAdapter extends RecyclerView.Adapter<TicketTypeReceiptAdapter.ViewHolder> {

    private final ArrayList<TicketType> tickets;

    public TicketTypeReceiptAdapter(ArrayList<TicketType> tickets) {
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTicketTypeReceiptBinding binding = ItemTicketTypeReceiptBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TicketType ticket = tickets.get(position);
        holder.binding.ticketQuantityTextView.setText("x" + ticket.getQuantity());
        holder.binding.ticketNameTextView.setText(ticket.getName());
        double price = Double.parseDouble(ticket.getPrice().replace("$", ""));
        holder.binding.ticketPriceTextView.setText(NumberFormat.getCurrencyInstance().format(price));
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemTicketTypeReceiptBinding binding;

        public ViewHolder(ItemTicketTypeReceiptBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
