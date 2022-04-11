package com.sabikrahat.notekeeper.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sabikrahat.notekeeper.R;
import com.sabikrahat.notekeeper.model.Note;

import java.util.ArrayList;

public class CustomNoteAdapter extends ArrayAdapter<Note> {

    private final Context context;
    private final ArrayList<Note> values;


    public CustomNoteAdapter(@NonNull Context context, @NonNull ArrayList<Note> objects) {
        super(context, -1, objects);
        this.context = context;
        this.values = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.note_list_item, parent, false);

        TextView topic = rowView.findViewById(R.id.lstTopicId);
        TextView course = rowView.findViewById(R.id.lstCourseId);
        TextView description = rowView.findViewById(R.id.lstDescriptionId);

        topic.setText(values.get(position).getNoteTopic());
        course.setText(values.get(position).getCourseId());
        description.setText(values.get(position).getNoteDescription());

        return rowView;
    }
}
