package com.example.myapplication.ui.Tickets;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketsViewModel extends ViewModel {

    private static final String TAG = "TicketsViewModel";
    private final MutableLiveData<List<Ticket>> tickets = new MutableLiveData<>();
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private boolean isUsingMockData = false;

    public LiveData<List<Ticket>> getTickets() {
        return tickets;
    }

    private List<Ticket> createMockTickets() {
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(new Ticket("1", "VerTech Gala Normal", 0.00, 1, "OCT", "24", "7:00 PM", "VerTech Gala", false));
        ticketList.add(new Ticket("2", "VerTech Gala Premium", 1.99, 1, "OCT", "24", "7:00 PM", "VerTech Gala", false));
        ticketList.add(new Ticket("3", "c2 Hacks", 5.00, 1, "NOV", "15", "5:00 PM", "c2 Hacks", false));
        return ticketList;
    }

    public void loadTickets() {
        db.child("tickets").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Ticket> ticketList = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    isUsingMockData = false;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Ticket ticket = snapshot.getValue(Ticket.class);
                        if (ticket != null) {
                            ticket.setId(snapshot.getKey());
                            ticketList.add(ticket);
                        }
                    }
                    Log.d(TAG, "Loaded " + ticketList.size() + " tickets from Realtime Database.");
                    tickets.setValue(ticketList);
                } else {
                    Log.d(TAG, "No tickets found in Realtime Database. Loading mock data.");
                    isUsingMockData = true;
                    tickets.setValue(createMockTickets());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Listen failed.", databaseError.toException());
                isUsingMockData = true;
                tickets.setValue(createMockTickets());
            }
        });
    }

    public void markTicketAsBooked(String ticketId) {
        if (ticketId == null || ticketId.isEmpty()) return;

        if (isUsingMockData) {
            List<Ticket> currentList = tickets.getValue();
            if (currentList == null) return;

            Map<String, Object> ticketMap = new HashMap<>();
            for (Ticket ticket : currentList) {
                if (ticket.getId().equals(ticketId)) {
                    ticket.setBooked(true);
                }
                ticketMap.put(ticket.getId(), ticket);
            }
            db.child("tickets").setValue(ticketMap)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Mock data promoted to Firebase."))
                    .addOnFailureListener(e -> Log.w(TAG, "Error promoting mock data.", e));
        } else {
            db.child("tickets").child(ticketId).child("booked").setValue(true)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Ticket successfully booked: " + ticketId))
                .addOnFailureListener(e -> Log.w(TAG, "Error booking ticket", e));
        }
    }
}
