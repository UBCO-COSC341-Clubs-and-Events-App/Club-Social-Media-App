package com.example.myapplication.ui.TicketSales;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentTicketSalesBinding;
import com.example.myapplication.ui.TicketSales.TicketSalesViewModel;

public class TicketSalesFragment extends Fragment {

    private FragmentTicketSalesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TicketSalesViewModel TicketSalesViewModel =
                new ViewModelProvider(this).get(TicketSalesViewModel.class);

        binding = FragmentTicketSalesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTicketSales;
        TicketSalesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}