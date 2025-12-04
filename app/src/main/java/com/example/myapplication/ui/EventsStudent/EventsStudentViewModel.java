package com.example.myapplication.ui.EventsStudent;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class EventsStudentViewModel extends ViewModel {

    private static final String TAG = "EventsStudentViewModel";
    private final MutableLiveData<List<Event>> filteredEvents = new MutableLiveData<>();
    private final List<Event> allEvents = new ArrayList<>();
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("events");

    public EventsStudentViewModel() {
        fetchEventsFromRealtimeDatabase();
    }

    public LiveData<List<Event>> getFilteredEvents() {
        return filteredEvents;
    }

    private void fetchEventsFromRealtimeDatabase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allEvents.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event event = snapshot.getValue(Event.class);
                    if (event != null) {
                        allEvents.add(event);
                    }
                }
                filterEventsByDate(Calendar.getInstance().getTimeInMillis());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    public void filterEventsByDate(long selectedDate) {
        Calendar selectedCal = Calendar.getInstance();
        selectedCal.setTimeInMillis(selectedDate);

        List<Event> filtered = allEvents.stream()
                .filter(event -> {
                    Calendar eventCal = Calendar.getInstance();
                    eventCal.setTimeInMillis(event.getDate());
                    return eventCal.get(Calendar.YEAR) == selectedCal.get(Calendar.YEAR) &&
                            eventCal.get(Calendar.DAY_OF_YEAR) == selectedCal.get(Calendar.DAY_OF_YEAR);
                })
                .collect(Collectors.toList());
        filteredEvents.setValue(filtered);
    }
}
