package com.example.myapplication.ui.Tickets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentTicketDetailsBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class TicketDetailsFragment extends Fragment implements TicketTypeAdapter.OnQuantityChangedListener {

    private FragmentTicketDetailsBinding binding;
    private ArrayList<TicketType> ticketTypes;
    private String ticketId;

    public TicketDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ticketId = getArguments().getString("ticketId");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTicketDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up the RecyclerView
        binding.rvTicketTypes.setLayoutManager(new LinearLayoutManager(getContext()));
        ticketTypes = new ArrayList<>();
        ticketTypes.add(new TicketType("Early Bird", "$25.00"));
        ticketTypes.add(new TicketType("General Admission", "$40.00"));
        TicketTypeAdapter adapter = new TicketTypeAdapter(ticketTypes);
        adapter.setOnQuantityChangedListener(this);
        binding.rvTicketTypes.setAdapter(adapter);

        // Set up the Spinner
        String[] paymentMethods = {"Credit Card", "PayPal", "Google Pay"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new ArrayList<>(Arrays.asList(paymentMethods)));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPaymentMethod.setAdapter(spinnerAdapter);

        // Set up the button click listener
        binding.btnGetTickets.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            ArrayList<TicketType> selectedTickets = ticketTypes.stream().filter(t -> t.getQuantity() > 0).collect(Collectors.toCollection(ArrayList::new));
            bundle.putParcelableArrayList("selectedTickets", selectedTickets);
            bundle.putString("ticketId", ticketId);

            NavHostFragment.findNavController(TicketDetailsFragment.this)
                    .navigate(R.id.action_ticketDetailsFragment_to_ticketReceiptFragment, bundle);
        });

        updateTotalPrice();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onQuantityChanged() {
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double subtotal = 0;
        for (TicketType ticketType : ticketTypes) {
            double price = Double.parseDouble(ticketType.getPrice().replace("$", ""));
            subtotal += price * ticketType.getQuantity();
        }

        double tax = subtotal * 0.10;
        double total = subtotal + tax;

        binding.tvTotalPrice.setText(String.format(Locale.getDefault(), "$%.2f", total));
    }
}
