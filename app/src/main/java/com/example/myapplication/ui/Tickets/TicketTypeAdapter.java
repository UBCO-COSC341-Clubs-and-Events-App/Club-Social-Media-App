package com.example.myapplication.ui.Tickets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;

import java.util.ArrayList;

public class TicketTypeAdapter extends RecyclerView.Adapter<TicketTypeAdapter.ViewHolder> {

    private final ArrayList<TicketType> ticketTypes;
    private OnQuantityChangedListener onQuantityChangedListener;

    public interface OnQuantityChangedListener {
        void onQuantityChanged();
    }

    public void setOnQuantityChangedListener(OnQuantityChangedListener listener) {
        this.onQuantityChangedListener = listener;
    }

    public TicketTypeAdapter(ArrayList<TicketType> ticketTypes) {
        this.ticketTypes = ticketTypes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_types, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TicketType ticketType = ticketTypes.get(position);
        holder.ticketName.setText(ticketType.getName());
        holder.ticketPrice.setText(ticketType.getPrice());
        holder.quantity.setText(String.valueOf(ticketType.getQuantity()));

        holder.plusButton.setOnClickListener(v -> {
            int newQuantity = ticketType.getQuantity() + 1;
            ticketType.setQuantity(newQuantity);
            notifyItemChanged(position);
            if (onQuantityChangedListener != null) {
                onQuantityChangedListener.onQuantityChanged();
            }
        });

        holder.minusButton.setOnClickListener(v -> {
            int newQuantity = ticketType.getQuantity() - 1;
            if (newQuantity >= 0) {
                ticketType.setQuantity(newQuantity);
                notifyItemChanged(position);
                if (onQuantityChangedListener != null) {
                    onQuantityChangedListener.onQuantityChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ticketTypes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ticketName;
        TextView ticketPrice;
        TextView quantity;
        ImageButton plusButton;
        ImageButton minusButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ticketName = itemView.findViewById(R.id.tv_ticket_title);
            ticketPrice = itemView.findViewById(R.id.tv_ticket_price);
            quantity = itemView.findViewById(R.id.tv_quantity);
            plusButton = itemView.findViewById(R.id.btn_plus);
            minusButton = itemView.findViewById(R.id.btn_minus);
        }
    }
}
