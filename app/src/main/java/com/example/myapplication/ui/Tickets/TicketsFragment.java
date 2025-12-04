package com.example.myapplication.ui.Tickets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.myapplication.databinding.FragmentTicketsBinding;
import java.util.ArrayList;

public class TicketsFragment extends Fragment {

    private FragmentTicketsBinding binding;
    private TicketsViewModel ticketsViewModel;
    private EventAdapter eventAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTicketsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        ticketsViewModel = new ViewModelProvider(this).get(TicketsViewModel.class);

        // Setup RecyclerView
        setupRecyclerView();

        // Observe data from ViewModel
        ticketsViewModel.getEvents().observe(getViewLifecycleOwner(), events -> {
            if (events != null) {
                eventAdapter.setEvents(events);
            }
        });
    }

    private void setupRecyclerView() {
        binding.recyclerEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        eventAdapter = new EventAdapter(new ArrayList<>());
        binding.recyclerEvents.setAdapter(eventAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Avoid memory leaks
    }
}