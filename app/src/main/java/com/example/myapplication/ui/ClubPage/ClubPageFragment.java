package com.example.myapplication.ui.ClubPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentClubPageBinding;

public class ClubPageFragment extends Fragment {

    private FragmentClubPageBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ClubPageViewModel clubPageViewModel =
                new ViewModelProvider(this).get(ClubPageViewModel.class);

        binding = FragmentClubPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textClubPage;
        clubPageViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}