package com.example.myapplication.ui.Tickets;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.databinding.ItemTicketTypesBinding;
import java.util.List;
import java.util.Locale;

public class TicketTypeAdapter extends RecyclerView.Adapter<TicketTypeAdapter.TicketTypeViewHolder> {

    private final List<TicketType> ticketTypes;
    private final OnQuantityChangedListener listener;

    public interface OnQuantityChangedListener {
        void onQuantityChanged();
    }

    public TicketTypeAdapter(List<TicketType> ticketTypes, OnQuantityChangedListener listener) {
        this.ticketTypes = ticketTypes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TicketTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTicketTypesBinding binding = ItemTicketTypesBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new TicketTypeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketTypeViewHolder holder, int position) {
        holder.bind(ticketTypes.get(position));
    }

    @Override
    public int getItemCount() {
        return ticketTypes != null ? ticketTypes.size() : 0;
    }

    // Inner class ViewHolder to access adapter's members
    public class TicketTypeViewHolder extends RecyclerView.ViewHolder {

        private final ItemTicketTypesBinding binding;

        public TicketTypeViewHolder(ItemTicketTypesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            // Set OnClickListener for increasing quantity
            binding.btnPlus.setOnClickListener(v -> {
                int pos = getBindingAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    TicketType item = ticketTypes.get(pos);
                    item.setQuantity(item.getQuantity() + 1);
                    notifyItemChanged(pos);
                    if (listener != null) listener.onQuantityChanged();
                }
            });

            // Set OnClickListener for decreasing quantity
            binding.btnMinus.setOnClickListener(v -> {
                int pos = getBindingAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    TicketType item = ticketTypes.get(pos);
                    if (item.getQuantity() > 0) {
                        item.setQuantity(item.getQuantity() - 1);
                        notifyItemChanged(pos);
                        if (listener != null) listener.onQuantityChanged();
                    }
                }
            });
        }

        void bind(TicketType ticketType) {
            binding.tvTicketTitle.setText(ticketType.getName());
            binding.tvTicketPrice.setText(String.format(Locale.US, "$%.2f", ticketType.getPrice()));
            binding.tvQuantity.setText(String.valueOf(ticketType.getQuantity()));

            // Optional: visually disable minus button when quantity is 0
            binding.btnMinus.setAlpha(ticketType.getQuantity() == 0 ? 0.3f : 1.0f);
            binding.btnMinus.setEnabled(ticketType.getQuantity() > 0);
        }
    }
}
