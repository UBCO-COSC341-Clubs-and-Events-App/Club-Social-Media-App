package com.example.myapplication.ui.EventsClub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;

import java.util.List;

public class EventsClubAdapter extends RecyclerView.Adapter<EventsClubAdapter.EventViewHolder> {

    private List<Event> eventList;

    public EventsClubAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_club, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        // Gets the current event from the list
        Event currentEvent = eventList.get(position);

        // Sets the data to the views
        holder.textMonth.setText(currentEvent.getMonth());
        holder.textDay.setText(currentEvent.getDay());
        holder.textEventTime.setText(currentEvent.getTime());
        holder.textEventName.setText(currentEvent.getName());

        holder.btnCancelEvent.setOnClickListener(v -> {
            // space to delete event from firebase and remove from list while notifying the adapter
            Toast.makeText(v.getContext(), "Cancel clicked for " + currentEvent.getName(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView textMonth;
        public TextView textDay;
        public TextView textEventTime;
        public TextView textEventName;
        public Button btnCancelEvent;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            textMonth = itemView.findViewById(R.id.text_month);
            textDay = itemView.findViewById(R.id.text_day);
            textEventTime = itemView.findViewById(R.id.text_event_time);
            textEventName = itemView.findViewById(R.id.text_event_name);
            btnCancelEvent = itemView.findViewById(R.id.btn_cancel_event);
        }
    }
}
