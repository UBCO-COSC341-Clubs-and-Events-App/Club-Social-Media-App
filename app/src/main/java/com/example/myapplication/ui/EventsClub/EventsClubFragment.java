package com.example.myapplication.ui.EventsClub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R; // Make sure this is your app's R file
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class EventsClubFragment extends Fragment {

    private RecyclerView eventsRecyclerView;
    private EventsClubAdapter eventsAdapter;
    private List<Event> eventList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_club, container, false);

        eventsRecyclerView = view.findViewById(R.id.events_recycler_view);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ExtendedFloatingActionButton fab = view.findViewById(R.id.fab_create_event);
        fab.setOnClickListener(v -> {
            // get to create event activity
            Toast.makeText(getContext(), "Create New Event Clicked", Toast.LENGTH_SHORT).show();
        });

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Events"));
        tabLayout.addTab(tabLayout.newTab().setText("About"));
        tabLayout.addTab(tabLayout.newTab().setText("FAQ"));

        // tab selections
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //change to correct screen
                Toast.makeText(getContext(), tab.getText() + " selected", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        loadSampleData();

        eventsAdapter = new EventsClubAdapter(eventList);
        eventsRecyclerView.setAdapter(eventsAdapter);

        return view;
    }

    private void loadSampleData() {

        eventList = new ArrayList<>();
        eventList.add(new Event("NOV", "03", "Mon 5:00 p.m.", "VerTech Gala"));
        eventList.add(new Event("NOV", "15", "Sat 5:00 p.m.", "c2 Hacks"));
        eventList.add(new Event("DEC", "01", "Fri 7:00 p.m.", "Winter Code Jam"));
        eventList.add(new Event("DEC", "12", "Tue 6:00 p.m.", "End of Semester Social"));
    }
}
