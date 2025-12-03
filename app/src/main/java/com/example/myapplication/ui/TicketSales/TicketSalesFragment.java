package com.example.myapplication.ui.TicketSales;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentTicketSalesBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class TicketSalesFragment extends Fragment {

    private FragmentTicketSalesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TicketSalesViewModel ticketSalesViewModel =
                new ViewModelProvider(this).get(TicketSalesViewModel.class);

        binding = FragmentTicketSalesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTicketSales;
        ticketSalesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        setupEventSpinner();

        // Initial load (will default to index 0 inside the spinner listener)
        return root;
    }

    private void setupEventSpinner() {
        Spinner spinner = binding.spinnerEvents;

        // Create data for the dropdown
        String[] eventOptions = {"2025 Hackathon", "Kickoff Event"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                eventOptions
        );

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateData(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void updateData(int index) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        int goalAmount;
        int currentAmount;

        if (index == 0) {
            // Option 1: 2025 Hackathon (Original Data)
            entries.add(new BarEntry(0, 7f));  // Mon
            entries.add(new BarEntry(1, 5f));  // Tue
            entries.add(new BarEntry(2, 0f));  // Wed
            entries.add(new BarEntry(3, 10f)); // Thurs
            entries.add(new BarEntry(4, 16f)); // Fri
            entries.add(new BarEntry(5, 3f));  // Sat
            entries.add(new BarEntry(6, 0f));  // Sun

            goalAmount = 100;
            currentAmount = 67;

        } else {
            // Option 2: Kickoff Event
            entries.add(new BarEntry(0, 2f));  // Mon
            entries.add(new BarEntry(1, 0f));  // Tue
            entries.add(new BarEntry(2, 5f));  // Wed
            entries.add(new BarEntry(3, 1f));  // Thurs
            entries.add(new BarEntry(4, 0f));  // Fri
            entries.add(new BarEntry(5, 2f));  // Sat
            entries.add(new BarEntry(6, 8f));  // Sun

            goalAmount = 50;
            currentAmount = 55;
        }

        // Update Bar Chart
        updateBarChart(entries);

        // Update Monthly Goal UI
        binding.textMonthlyGoal.setText("Monthly Goal: $" + goalAmount);
        binding.progressMonthlyGoal.setMax(goalAmount);
        binding.progressMonthlyGoal.setProgress(currentAmount);
        binding.textCurrentProgress.setText("Current: $" + currentAmount);
    }

    private void updateBarChart(ArrayList<BarEntry> entries) {
        BarChart barChart = binding.barChart;

        BarDataSet dataSet = new BarDataSet(entries, "Sales");
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(10f);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        // Configure X Axis (Only needs to be done once ideally, but safe here)
        String[] days = {"Mon", "Tue", "Wed", "Thurs", "Fri", "Sat", "Sun"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        // Configure Y Axis
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisLeft().setAxisMaximum(20f); // Keeping scale consistent
        barChart.getAxisRight().setEnabled(false);

        // Other settings
        barChart.getDescription().setEnabled(false);
        barChart.animateY(1000);
        barChart.invalidate(); // Refresh chart
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
