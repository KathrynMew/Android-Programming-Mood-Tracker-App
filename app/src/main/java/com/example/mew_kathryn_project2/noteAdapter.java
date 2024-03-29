package com.example.mew_kathryn_project2;

import android.text.method.ScrollingMovementMethod;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;

public class noteAdapter extends RecyclerView.Adapter<noteAdapter.ViewHolder> {
    private static ArrayList<String> notes;

    private static OnClickListener deleteListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView note;
        private Button delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            note = itemView.findViewById(R.id.noteView);
            // Make TextView scrollable
            note.setMovementMethod(new ScrollingMovementMethod());
            delete = itemView.findViewById(R.id.deleteBtn);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteListener.onPositionClick(notes, getAdapterPosition());
                }
            });
        }
    }

    public noteAdapter(ArrayList<String> list, OnClickListener listener) {
        notes = list;
        deleteListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_view,parent,false);
        // return holder
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.note.setText(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public interface OnClickListener {
        void onPositionClick(ArrayList<String> notes_list, int position);
    }
}
