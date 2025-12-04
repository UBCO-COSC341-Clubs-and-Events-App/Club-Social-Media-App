package com.example.myapplication.ui.Tickets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.myapplication.databinding.FragmentTicketReceiptBinding;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class TicketReceiptFragment extends Fragment {

    private FragmentTicketReceiptBinding binding;
    private static final double TAX_RATE = 0.05; // 5% tax

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTicketReceiptBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // You would pass the list of tickets to this fragment, e.g., through arguments
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket("VerTech Gala Normal", 0.00, 1));
        tickets.add(new Ticket("VerTech Gala Premium", 1.99, 1));

        setupRecyclerView(tickets);
        calculateAndDisplayTotal(tickets);
    }

    private void setupRecyclerView(List<Ticket> tickets) {
        binding.rvTicketReceipts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvTicketReceipts.setAdapter(new TicketReceiptAdapter(tickets));
    }

    private void calculateAndDisplayTotal(List<Ticket> tickets) {
        double subtotal = 0.0;
        for (Ticket ticket : tickets) {
            subtotal += ticket.getPrice() * ticket.getQuantity();
        }
        double tax = subtotal * TAX_RATE;
        double total = subtotal + tax;

        binding.totalPriceTextView.setText(NumberFormat.getCurrencyInstance().format(total));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
