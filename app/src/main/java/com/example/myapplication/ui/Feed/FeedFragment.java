package com.example.myapplication.ui.Feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentFeedBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FeedFragment extends Fragment {


    private FragmentFeedBinding binding;
    private FeedAdapter feedAdapter;
    private ArrayList<FeedItem> allPosts = new ArrayList<>();
    private DatabaseReference databaseReference;
    // FIX 1: Add a member variable for the listener
    private ValueEventListener firebaseListener;

    private final String[] localClubNames = {"VerTech", "C2", "Tech n' Stuff", "Book Club", "Art Club"};


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FeedViewModel feedViewModel = new ViewModelProvider(this).get(FeedViewModel.class);

        binding = FragmentFeedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupRecyclerView();
        fetchFeedDataFromFirebase();

        binding.filterGroup.setOnCheckedChangeListener((group, checkedId) -> {
            filterData(checkedId);
        });

        return root;
    }

    private void fetchFeedDataFromFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("events");
        // FIX 2: Initialize the listener here
        firebaseListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // FIX 3: THE CRITICAL CHECK - Make sure the view is still available
                if (binding == null) {
                    return; // Exit the method if the view is gone
                }

                allPosts.clear();
                int clubNameIndex = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String eventName = snapshot.child("name").getValue(String.class);
                    String eventDescription = snapshot.child("details").getValue(String.class);

                    SimpleDateFormat date = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                    String eventDate = date.format(new Date(snapshot.child("date").getValue(Long.class)));



                    if (eventName != null) {
                        // Your existing logic to create FeedItem
                        FeedItem item = new FeedItem(
                                R.drawable.test_club_pfp,
                                localClubNames[clubNameIndex % localClubNames.length] + " - " + eventName,
                                eventDescription, // Using eventName as description as per previous request
                                R.drawable.ic_launcher_background,
                                eventDate
                        );
                        allPosts.add(item);
                        clubNameIndex++;
                    }
                }
                // Now it's safe to access binding
                filterData(binding.filterGroup.getCheckedRadioButtonId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Failed to load feed data.", Toast.LENGTH_SHORT).show();
                }
            }
        };
        // Attach the listener
        databaseReference.addValueEventListener(firebaseListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // FIX 4: Detach the listener to prevent memory leaks and crashes
        if (databaseReference != null && firebaseListener != null) {
            databaseReference.removeEventListener(firebaseListener);
        }
        binding = null;
    }

    public void filterData(int checkedId) {
        ArrayList<FeedItem> filteredPosts = new ArrayList<>();

        if (checkedId == R.id.filterFollowing) {
            for (FeedItem item : allPosts) {
                if (checkFollowing(item)) {
                    filteredPosts.add(item);
                }
            }
        } else if (checkedId == R.id.filterFriends) {
            for (FeedItem item : allPosts) {
                if (checkFriendsFollowing(item)) {
                    filteredPosts.add(item);
                }
            }
        } else { // Default to "Latest"
            filteredPosts.addAll(allPosts);
        }

        if (feedAdapter != null) {
            feedAdapter.filterList(filteredPosts);
        }
    }

    boolean checkFollowing(FeedItem item){
        return item.getClubName().contains("VerTech");
    }

    boolean checkFriendsFollowing(FeedItem item){
        return item.getClubName().contains("C2");
    }

    private void setupRecyclerView(){
        RecyclerView rv = binding.feedRecyclerView;
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        feedAdapter = new FeedAdapter(new ArrayList<>());
        rv.setAdapter(feedAdapter);
    }

}