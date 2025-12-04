package com.example.myapplication.ui.Tickets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class TicketsViewModel extends ViewModel {
    private final MutableLiveData<List<Ticket>> tickets = new MutableLiveData<>();

    public LiveData<List<Ticket>> getTickets() {
        return tickets;
    }

    public void loadTickets() {
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(new Ticket("NOV", "03", "Mon 5:00 p.m.", "VerTech Gala", true));
        ticketList.add(new Ticket("NOV", "15", "Sat 5:00 p.m.", "c2 Hacks", false));
        tickets.setValue(ticketList);
    }

    public void markTicketAsBooked(String ticketId) {
        List<Ticket> currentTickets = tickets.getValue();
        if (currentTickets != null) {
            for (Ticket ticket : currentTickets) {
                if (ticket.getId().equals(ticketId)) {
                    ticket.setBooked(true);
                    break;
                }
            }
            tickets.setValue(new ArrayList<>(currentTickets)); // Trigger update
        }
    }
}
