package com.example.myapplication.ui.EventsStudent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class EventsStudentViewModel extends ViewModel {

    private final MutableLiveData<List<Event>> filteredEvents = new MutableLiveData<>();
    private final List<Event> allEvents = new ArrayList<>();

    public EventsStudentViewModel() {
        Calendar today = Calendar.getInstance();

        // Add events for today
        allEvents.add(new Event("9:00 AM", "1h", "VerTech Gala - Coding Club SUO", "UNC 200", today.getTimeInMillis()));
        allEvents.add(new Event("1:00 PM", "45m", "The Confidence Blueprint - Girls In Tech", "ASC 140", today.getTimeInMillis()));
        allEvents.add(new Event("3:45 PM", "1h 30m", "Mini Pumpkin Painting - ggc.suo", "ART 109", today.getTimeInMillis()));
        allEvents.add(new Event("5:30 PM", "15m", "Power Lifting - Weightlifting Club SUO", "Hangar", today.getTimeInMillis()));

        // Add events for tomorrow
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_YEAR, 1);
        allEvents.add(new Event("10:00 AM", "2h", "Android Workshop", "Room 101", tomorrow.getTimeInMillis()));

        //filter for the current date @first (initial setup/default setup)
        filterEventsByDate(today.getTimeInMillis());
    }

    public LiveData<List<Event>> getFilteredEvents() {
        return filteredEvents;
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
