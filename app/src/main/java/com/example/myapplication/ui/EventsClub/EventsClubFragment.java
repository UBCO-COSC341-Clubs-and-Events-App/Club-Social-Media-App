package com.example.myapplication.ui.EventsClub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventsClubFragment extends Fragment {

    private RecyclerView eventsRecyclerView;
    private TextView textContentView;
    private EventsClubAdapter eventsAdapter;
    private List<Event> eventList;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_club, container, false);

        eventsRecyclerView = view.findViewById(R.id.events_recycler_view);
        textContentView = view.findViewById(R.id.text_content_view);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        eventList = new ArrayList<>();
        eventsAdapter = new EventsClubAdapter(eventList);
        eventsRecyclerView.setAdapter(eventsAdapter);

        ExtendedFloatingActionButton fab = view.findViewById(R.id.fab_create_event);
        fab.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_eventsClubFragment_to_createEventFragment);
        });

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Events"));
        tabLayout.addTab(tabLayout.newTab().setText("About"));
        tabLayout.addTab(tabLayout.newTab().setText("FAQ"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: // Events
                        eventsRecyclerView.setVisibility(View.VISIBLE);
                        textContentView.setVisibility(View.GONE);
                        break;
                    case 1: // About
                        eventsRecyclerView.setVisibility(View.GONE);
                        textContentView.setVisibility(View.VISIBLE);
                        textContentView.setText("This is the About section. You can add a description of your club here.");
                        break;
                    case 2: // FAQ
                        eventsRecyclerView.setVisibility(View.GONE);
                        textContentView.setVisibility(View.VISIBLE);
                        textContentView.setText("This is the FAQ section. You can add frequently asked questions and answers here.");
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        fetchEventsFromFirebase();

        return view;
    }

    private void fetchEventsFromFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("events");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event event = snapshot.getValue(Event.class);
                    if (event != null) {
                        eventList.add(event);
                    }
                }
                eventsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load events.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
