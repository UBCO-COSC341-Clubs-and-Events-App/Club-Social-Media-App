package com.example.myapplication.ui.Tickets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class TicketsViewModel extends ViewModel {
    private final MutableLiveData<List<Ticket>> tickets = new MutableLiveData<>();

    private final MutableLiveData<List<Event>> events;

    public TicketsViewModel() {
        events = new MutableLiveData<>();
        List<Event> eventList = new ArrayList<>();
        eventList.add(new Event("NOV", "03", "VerTech Gala", "Mon 5:00 p.m.", false));
        eventList.add(new Event("NOV", "15", "c2 Hacks", "Sat 5:00 p.m.", true));
        events.setValue(eventList);
    }

    public LiveData<List<Event>> getEvents() {
        return events;
    }
}
