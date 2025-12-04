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

import java.util.ArrayList;

public class TicketReceiptFragment extends Fragment {

    private FragmentTicketReceiptBinding binding;
    private String ticketId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTicketReceiptBinding.inflate(inflater, container, false);
        if (getArguments() != null) {
            ticketId = getArguments().getString("ticketId");
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            double totalWithTax = arguments.getDouble("totalWithTax");
            String paymentMethod = arguments.getString("paymentMethod");
            ArrayList<TicketType> purchasedTickets = arguments.getParcelableArrayList("purchasedTickets");

            binding.tvTotalAmount.setText(String.format(java.util.Locale.US, "$%.2f", totalWithTax));
            binding.tvPaymentUsed.setText(paymentMethod);

            binding.rvReceiptTickets.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvReceiptTickets.setAdapter(new TicketReceiptAdapter(purchasedTickets));
        }

        binding.btnLeave.setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigate(R.id.action_ticketReceiptFragment_to_nav_tickets)
        );

        binding.btnPurchase.setOnClickListener(v -> {
            Bundle result = new Bundle();
            result.putString("ticketId", ticketId);
            getParentFragmentManager().setFragmentResult("purchase_completed", result);
            NavHostFragment.findNavController(this).navigate(R.id.action_ticketReceiptFragment_to_nav_tickets);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}