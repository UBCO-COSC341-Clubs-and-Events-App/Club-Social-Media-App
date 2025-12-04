package com.example.myapplication.ui.Tickets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentTicketReceiptBinding;
import java.text.NumberFormat;
import java.util.ArrayList;

public class TicketReceiptFragment extends Fragment {

    private FragmentTicketReceiptBinding binding;
    private static final double TAX_RATE = 0.10; // 10% tax
    private String ticketId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ticketId = getArguments().getString("ticketId");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTicketReceiptBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<TicketType> selectedTickets = getArguments().getParcelableArrayList("selectedTickets");

        setupRecyclerView(selectedTickets);
        calculateAndDisplayTotal(selectedTickets);

        binding.leaveButton.setOnClickListener(v -> {
            Bundle result = new Bundle();
            result.putString("ticketId", ticketId);
            getParentFragmentManager().setFragmentResult("purchase_completed", result);
            NavHostFragment.findNavController(this).popBackStack(R.id.ticketsFragment, false);
        });
    }

    private void setupRecyclerView(ArrayList<TicketType> tickets) {
        binding.rvTicketReceipts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvTicketReceipts.setAdapter(new TicketTypeReceiptAdapter(tickets));
    }

    private void calculateAndDisplayTotal(ArrayList<TicketType> tickets) {
        double subtotal = 0.0;
        for (TicketType ticket : tickets) {
            double price = Double.parseDouble(ticket.getPrice().replace("$", ""));
            subtotal += price * ticket.getQuantity();
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
