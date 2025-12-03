package com.example.myapplication.ui.EventsStudent;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.databinding.FragmentEventsStudentBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EventsStudentFragment extends Fragment {

    private FragmentEventsStudentBinding binding;
    private EventAdapter eventAdapter;
    private EventsStudentViewModel eventsStudentViewModel;

    // Store which mood is selected
    private String selectedMood = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        eventsStudentViewModel =
                new ViewModelProvider(this).get(EventsStudentViewModel.class);

        binding = FragmentEventsStudentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupRecyclerView();
        setupCalendar();
        updateDateViews(System.currentTimeMillis());

        eventsStudentViewModel.getFilteredEvents().observe(getViewLifecycleOwner(), events -> {
            eventAdapter.setEvents(events);
        });

        return root;
    }

    private void setupRecyclerView() {
        binding.eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        eventAdapter = new EventAdapter(new ArrayList<>());
        binding.eventsRecyclerView.setAdapter(eventAdapter);
    }

    private void setupCalendar() {
        binding.calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            long selectedDate = calendar.getTimeInMillis();
            eventsStudentViewModel.filterEventsByDate(selectedDate);
            updateDateViews(selectedDate);
        });
    }

    private void updateDateViews(long selectedDate) {
        Date date = new Date(selectedDate);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.getDefault());
        SimpleDateFormat todayFormat = new SimpleDateFormat("'Today' - EEE MMM dd", Locale.getDefault());
        binding.todayTextView.setText(todayFormat.format(date));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
