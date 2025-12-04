package com.example.myapplication.ui.Tickets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentTicketsBinding;

import java.util.ArrayList;
import java.util.List;

public class TicketsFragment extends Fragment implements TicketAdapter.OnItemClickListener {

    private FragmentTicketsBinding binding;
    private TicketAdapter adapter;
    private List<Ticket> tickets = new ArrayList<>();
    private TicketsViewModel ticketsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTicketsBinding.inflate(inflater, container, false);
        ticketsViewModel = new ViewModelProvider(requireActivity()).get(TicketsViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();

        ticketsViewModel.getTickets().observe(getViewLifecycleOwner(), updatedTickets -> {
            if (updatedTickets != null) {
                tickets.clear();
                tickets.addAll(updatedTickets);
                adapter.notifyDataSetChanged();
            }
        });

        if (ticketsViewModel.getTickets().getValue() == null) {
            ticketsViewModel.loadTickets();
        }

        getParentFragmentManager().setFragmentResultListener("purchase_completed", getViewLifecycleOwner(), (requestKey, bundle) -> {
            String ticketId = bundle.getString("ticketId");
            if (ticketId != null) {
                ticketsViewModel.markTicketAsBooked(ticketId);
                Toast.makeText(getContext(), "You have successfully booked tickets for the event", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView() {
        binding.ticketsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TicketAdapter(tickets, this);
        binding.ticketsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(Ticket ticket) {
        Bundle bundle = new Bundle();
        bundle.putString("ticketId", ticket.getId());
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_ticketsFragment_to_ticketDetailsFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}