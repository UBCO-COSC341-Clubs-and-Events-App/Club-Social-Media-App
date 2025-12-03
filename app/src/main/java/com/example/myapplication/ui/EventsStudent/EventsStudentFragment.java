package com.example.myapplication.ui.EventsStudent;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentEventsStudentBinding;

public class EventsStudentFragment extends Fragment {

    private FragmentEventsStudentBinding binding;

    // Store which mood is selected
    private String selectedMood = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        EventsStudentViewModel eventsStudentViewModel =
                new ViewModelProvider(this).get(EventsStudentViewModel.class);

        binding = FragmentEventsStudentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Load the existing textView from ViewModel
        final TextView textView = binding.textEventsStudent;
        eventsStudentViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // ------------------------------
        //  EMOJI SELECTION HANDLERS
        // ------------------------------

        binding.emojiSad.setOnClickListener(v -> selectEmoji("sad"));
        binding.emojiNeutral.setOnClickListener(v -> selectEmoji("neutral"));
        binding.emojiHappy.setOnClickListener(v -> selectEmoji("happy"));

        return root;
    }

    // Highlight logic
    private void selectEmoji(String mood) {

        selectedMood = mood;

        // Reset all backgrounds
        binding.emojiSad.setBackgroundColor(Color.TRANSPARENT);
        binding.emojiNeutral.setBackgroundColor(Color.TRANSPARENT);
        binding.emojiHappy.setBackgroundColor(Color.TRANSPARENT);

        // Highlight the selected one
        switch (mood) {
            case "sad":
                binding.emojiSad.setBackgroundColor(Color.parseColor("#FFCDD2"));
                break;

            case "neutral":
                binding.emojiNeutral.setBackgroundColor(Color.parseColor("#FFF9C4"));
                break;

            case "happy":
                binding.emojiHappy.setBackgroundColor(Color.parseColor("#C8E6C9"));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
