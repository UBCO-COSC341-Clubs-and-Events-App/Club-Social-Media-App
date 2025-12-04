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
        // This is where you would load your tickets from a repository, database, or network.
        // For now, I'll create a dummy list.
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(new Ticket("1", "VerTech Gala Normal", 0.00, 1, "OCT", "24", "7:00 PM", "VerTech Gala", false));
        ticketList.add(new Ticket("2", "VerTech Gala Premium", 1.99, 1, "OCT", "24", "7:00 PM", "VerTech Gala", false));
        ticketList.add(new Ticket("3", "c2 Hacks", 5.00, 1, "NOV", "15", "5:00 PM", "c2 Hacks", true));
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
            tickets.setValue(currentTickets); // Trigger observers
        }
    }
}
