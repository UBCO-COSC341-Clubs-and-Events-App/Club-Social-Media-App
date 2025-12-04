package com.example.myapplication.ui.Tickets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentTicketDetailsBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TicketDetailsFragment extends Fragment implements TicketTypeAdapter.OnQuantityChangedListener {

    private FragmentTicketDetailsBinding binding;
    private List<TicketType> ticketTypes;
    private String ticketId;
    private static final double TAX_RATE = 0.05; // 5%

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTicketDetailsBinding.inflate(inflater, container, false);
        if (getArguments() != null) {
            ticketId = getArguments().getString("ticketId");
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        setupPaymentSpinner();
        updateTotal();

        binding.btnGetTickets.setOnClickListener(v -> {
            if (calculateTotal() <= 0) {
                Toast.makeText(getContext(), "Please select at least one ticket.", Toast.LENGTH_SHORT).show();
                return;
            }

            double subtotal = calculateTotal();
            double totalWithTax = subtotal * (1 + TAX_RATE);

            String paymentMethod = "Cash"; // default
            if (binding.spinnerPaymentMethod.getSelectedItem() != null) {
                paymentMethod = binding.spinnerPaymentMethod.getSelectedItem().toString();
            }

            ArrayList<TicketType> purchasedTickets = ticketTypes.stream()
                    .filter(t -> t.getQuantity() > 0)
                    .collect(Collectors.toCollection(ArrayList::new));

            Bundle bundle = new Bundle();
            bundle.putString("ticketId", ticketId);
            bundle.putDouble("subtotal", subtotal);
            bundle.putDouble("totalWithTax", totalWithTax);
            bundle.putParcelableArrayList("purchasedTickets", purchasedTickets);
            bundle.putString("paymentMethod", paymentMethod);

            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_ticketDetailsFragment_to_ticketReceiptFragment, bundle);
        });
    }

    private void setupPaymentSpinner() {
        List<String> paymentMethods = new ArrayList<>();
        paymentMethods.add("Credit Card");
        paymentMethods.add("PayPal");
        paymentMethods.add("Google Pay");
        paymentMethods.add("Apple Pay");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                paymentMethods
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPaymentMethod.setAdapter(adapter);

        // Pre-select first item so getSelectedItem() is never null
        binding.spinnerPaymentMethod.setSelection(0);
    }

    private void setupRecyclerView() {
        ticketTypes = new ArrayList<>();
        ticketTypes.add(new TicketType("VerTech Gala Normal", 0.00));
        ticketTypes.add(new TicketType("VerTech Gala Premium", 1.99));

        binding.rvTicketTypes.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvTicketTypes.setAdapter(new TicketTypeAdapter(ticketTypes, this));
    }

    @Override
    public void onQuantityChanged() {
        updateTotal();
    }

    private void updateTotal() {
        double subtotal = calculateTotal();
        double totalWithTax = subtotal * (1 + TAX_RATE);
        binding.tvTotalPrice.setText(String.format(java.util.Locale.US, "$%.2f", totalWithTax));
    }

    private double calculateTotal() {
        return ticketTypes.stream()
                .mapToDouble(t -> t.getPrice() * t.getQuantity())
                .sum();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}