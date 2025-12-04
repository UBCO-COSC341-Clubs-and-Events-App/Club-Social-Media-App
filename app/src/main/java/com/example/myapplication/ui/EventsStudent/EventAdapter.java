package com.example.myapplication.ui.EventsStudent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ItemEventBinding;

import java.util.Calendar;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> events;
    private OnFeedbackButtonClickListener listener;

    public interface OnFeedbackButtonClickListener {
        void onFeedbackButtonClick(Event event);
    }

    public EventAdapter(List<Event> events, OnFeedbackButtonClickListener listener) {
        this.events = events;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEventBinding binding = ItemEventBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EventViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);
        holder.bind(event, listener);
    }

    @Override
    public int getItemCount() {
        return events != null ? events.size() : 0;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        private final ItemEventBinding binding;

        public EventViewHolder(ItemEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Event event, OnFeedbackButtonClickListener listener) {
            binding.eventTimeTextView.setText(event.getTime());
            binding.eventDurationTextView.setText(event.getDuration());
            binding.eventTitleTextView.setText(event.getName());
            binding.eventLocationTextView.setText(event.getLocation());
            binding.feedbackButton.setOnClickListener(v -> listener.onFeedbackButtonClick(event));

            // Get today's date, but without the time part
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);

            // Check if the event's date is before today
            if (event.getDate() < today.getTimeInMillis()) {
                binding.feedbackButton.setVisibility(View.VISIBLE);
            } else {
                binding.feedbackButton.setVisibility(View.GONE);
            }
        }
    }
}
