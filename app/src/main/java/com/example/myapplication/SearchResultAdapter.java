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

import java.util.List;

public class SearchResultAdapter extends ArrayAdapter<String> {

    private final LayoutInflater inflater;

    public SearchResultAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context, 0, objects);
        inflater = LayoutInflater.from(context);
    }

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
            convertView = inflater.inflate(R.layout.search_result_item, parent, false);
            holder = new ViewHolder();
            holder.icon = convertView.findViewById(R.id.icon);
            holder.title = convertView.findViewById(R.id.title);
            holder.subtitle = convertView.findViewById(R.id.subtitle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String item = getItem(position);
        if (item != null) {
            // main text
            holder.title.setText(item);

            // simple example subtitle – you can customize this
            holder.subtitle.setText("Tap to view details");

            // optional: change icon depending on content
            // if (item.contains("ClubName1")) { ... }
        }

        return convertView;
    }
}
