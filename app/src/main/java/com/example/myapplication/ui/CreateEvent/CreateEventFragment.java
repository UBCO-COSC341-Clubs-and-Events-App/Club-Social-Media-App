package com.example.myapplication.ui.CreateEvent;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentCreateEventBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateEventFragment extends Fragment {

    private FragmentCreateEventBinding binding;
    private final Calendar myCalendar = Calendar.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateEventBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupDateAndTimePickers();
        setupClickListeners();
        setupFreeEventCheckbox();

        return root;
    }

    private void setupDateAndTimePickers() {
        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateLabel();
        };

        TimePickerDialog.OnTimeSetListener time = (view, hourOfDay, minute) -> {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
            updateTimeLabel();
        };

        binding.dateEditText.setOnClickListener(v -> new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        binding.timeEditText.setOnClickListener(v -> new TimePickerDialog(getContext(), time, myCalendar
                .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false).show());
    }

    private void updateDateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        binding.dateEditText.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateTimeLabel() {
        String myFormat = "h:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        binding.timeEditText.setText(sdf.format(myCalendar.getTime()));
    }

    private void setupClickListeners() {
        binding.backButton.setOnClickListener(v -> NavHostFragment.findNavController(this).navigateUp());
        binding.createButton.setOnClickListener(v -> saveEventToRealtimeDatabase());
        binding.referencePastEventButton.setOnClickListener(v -> fetchLastEvent());
    }

    private void fetchLastEvent() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("events");
        Query lastEventQuery = databaseReference.orderByKey().limitToLast(1);

        lastEventQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        binding.eventNameEditText.setText(snapshot.child("name").getValue(String.class));
                        binding.locationEditText.setText(snapshot.child("location").getValue(String.class));
                        binding.detailsEditText.setText(snapshot.child("details").getValue(String.class));
                        binding.durationEditText.setText(snapshot.child("duration").getValue(String.class));

                        if (snapshot.hasChild("maxTickets")) {
                            binding.maxTicketsEditText.setText(String.valueOf(snapshot.child("maxTickets").getValue(Long.class)));
                        }
                        if (snapshot.hasChild("ticketPrice")) {
                            binding.ticketPriceEditText.setText(String.valueOf(snapshot.child("ticketPrice").getValue(Double.class)));
                        }
                        if (snapshot.hasChild("isFree")) {
                            binding.freeEventCheckbox.setChecked(snapshot.child("isFree").getValue(Boolean.class));
                        }
                        if (snapshot.hasChild("date")) {
                            long timestamp = snapshot.child("date").getValue(Long.class);
                            myCalendar.setTimeInMillis(timestamp);
                            updateDateLabel();
                            updateTimeLabel();
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "No past events to reference.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to fetch past event.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupFreeEventCheckbox() {
        binding.freeEventCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.ticketPriceEditText.setEnabled(!isChecked);
            if (isChecked) {
                binding.ticketPriceEditText.setText("0.0");
            }
        });
    }

    private void saveEventToRealtimeDatabase() {
        String eventName = binding.eventNameEditText.getText().toString().trim();
        String location = binding.locationEditText.getText().toString().trim();
        String details = binding.detailsEditText.getText().toString().trim();
        String date = binding.dateEditText.getText().toString().trim();
        String time = binding.timeEditText.getText().toString().trim();
        String duration = binding.durationEditText.getText().toString().trim();
        String maxTickets = binding.maxTicketsEditText.getText().toString().trim();
        String ticketPrice = binding.ticketPriceEditText.getText().toString().trim();

        if (eventName.isEmpty() || location.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("events");
        String eventId = databaseReference.push().getKey();

        Map<String, Object> event = new HashMap<>();
        event.put("name", eventName);
        event.put("location", location);
        event.put("details", details);
        event.put("date", myCalendar.getTimeInMillis());
        event.put("duration", duration);
        if (!maxTickets.isEmpty()) {
            event.put("maxTickets", Integer.parseInt(maxTickets));
        }
        if (!ticketPrice.isEmpty()) {
            event.put("ticketPrice", Double.parseDouble(ticketPrice));
        }
        event.put("isFree", binding.freeEventCheckbox.isChecked());

        if (eventId != null) {
            databaseReference.child(eventId).setValue(event)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "Event created successfully", Toast.LENGTH_SHORT).show();
                        NavHostFragment.findNavController(this).navigateUp();
                    })
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Error creating event", Toast.LENGTH_SHORT).show());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
