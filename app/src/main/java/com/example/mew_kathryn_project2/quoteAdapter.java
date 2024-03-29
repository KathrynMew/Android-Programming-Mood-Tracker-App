package com.example.mew_kathryn_project2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class quoteAdapter extends RecyclerView.Adapter<quoteAdapter.ViewHolder> {
    private static ArrayList<String> quotes;
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView quote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            quote = itemView.findViewById(R.id.quoteView);
        }
    }

    public quoteAdapter(ArrayList<String> list) {
        quotes = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quotes_view,parent,false);
        // return holder
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.quote.setText(quotes.get(position));
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }
}
