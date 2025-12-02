package com.example.myapplication.ui.Posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentPostsBinding;
import com.example.myapplication.ui.Posts.PostsViewModel;

public class PostsFragment extends Fragment {

    private FragmentPostsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PostsViewModel postsViewModel =
                new ViewModelProvider(this).get(PostsViewModel.class);

        binding = FragmentPostsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPosts;
        postsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}