package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// Import the Event class
import com.example.myapplication.ui.EventsClub.Event;

import java.util.List;

// FIX 1: Change the adapter's generic type from <String> to <Event>
public class SearchResultAdapter extends ArrayAdapter<Event> {

    // The inflater is no longer needed as a member variable since we can get it from context
    // private final LayoutInflater inflater;

    // FIX 2: Update the constructor to accept a List of Event objects
    public SearchResultAdapter(@NonNull Context context, @NonNull List<Event> objects) {
        super(context, 0, objects);
        // inflater = LayoutInflater.from(context); // This is not strictly necessary
    }

    // The ViewHolder remains useful for performance
    static class ViewHolder {
        ImageView icon;
        TextView title;
        TextView subtitle;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.search_result_item, parent, false);
            holder = new ViewHolder();
            holder.icon = convertView.findViewById(R.id.icon);
            holder.title = convertView.findViewById(R.id.title);
            holder.subtitle = convertView.findViewById(R.id.subtitle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Event event = getItem(position);

        if (event != null) {
            holder.title.setText(event.getName());

            holder.subtitle.setText(event.getName());

            holder.icon.setImageResource(R.drawable.test_club_pfp); // Make sure you have an 'ic_event' drawable
        }

        return convertView;
    }
}
