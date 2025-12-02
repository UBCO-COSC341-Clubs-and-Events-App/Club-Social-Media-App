package com.example.myapplication.ui.EventsClub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentEventsClubBinding;

public class EventsClubFragment extends Fragment {

    private FragmentEventsClubBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EventsClubViewModel eventsClubViewModel =
                new ViewModelProvider(this).get(EventsClubViewModel.class);

        binding = FragmentEventsClubBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textEventsClub;
        eventsClubViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}