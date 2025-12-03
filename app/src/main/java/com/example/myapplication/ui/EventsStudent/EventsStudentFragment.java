package com.example.myapplication.ui.EventsStudent;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentEventsStudentBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EventsStudentFragment extends Fragment implements EventAdapter.OnFeedbackButtonClickListener {

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
        eventAdapter = new EventAdapter(new ArrayList<>(), this);
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
        SimpleDateFormat todayFormat = new SimpleDateFormat("'Today' - EEE MMM dd", Locale.getDefault());
        binding.todayTextView.setText(todayFormat.format(date));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onFeedbackButtonClick(Event event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_feedback, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        Button submitBtn = view.findViewById(R.id.submitBtn);
        Button discardBtn = view.findViewById(R.id.discardBtn);

        TextView emojiSad = view.findViewById(R.id.emojiSad);
        TextView emojiNeutral = view.findViewById(R.id.emojiNeutral);
        TextView emojiHappy = view.findViewById(R.id.emojiHappy);

        emojiSad.setOnClickListener(v -> selectEmoji("sad", emojiSad, emojiNeutral, emojiHappy));
        emojiNeutral.setOnClickListener(v -> selectEmoji("neutral", emojiSad, emojiNeutral, emojiHappy));
        emojiHappy.setOnClickListener(v -> selectEmoji("happy", emojiSad, emojiNeutral, emojiHappy));

        submitBtn.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Your feedback has been submitted", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        discardBtn.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Feedback discarded", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }

    // Highlight logic
    private void selectEmoji(String mood, TextView emojiSad, TextView emojiNeutral, TextView emojiHappy) {

        selectedMood = mood;

        // Reset all backgrounds
        emojiSad.setBackgroundColor(Color.TRANSPARENT);
        emojiNeutral.setBackgroundColor(Color.TRANSPARENT);
        emojiHappy.setBackgroundColor(Color.TRANSPARENT);

        // Highlight the selected one
        switch (mood) {
            case "sad":
                emojiSad.setBackgroundColor(Color.parseColor("#FFCDD2"));
                break;

            case "neutral":
                emojiNeutral.setBackgroundColor(Color.parseColor("#FFF9C4"));
                break;

            case "happy":
                emojiHappy.setBackgroundColor(Color.parseColor("#C8E6C9"));
                break;
        }
    }
}
