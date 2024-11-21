package com.malivasileva.librarian.presentation.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FullBookAdapter extends RecyclerView.Adapter<FullBookAdapter.FullBookViewHolder> {

    @NonNull
    @Override
    public FullBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FullBookViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class FullBookViewHolder extends RecyclerView.ViewHolder {

        public FullBookViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
