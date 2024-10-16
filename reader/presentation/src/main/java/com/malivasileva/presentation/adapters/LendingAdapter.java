package com.malivasileva.presentation.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LendingAdapter extends RecyclerView.Adapter<LendingAdapter.LendingViewHolder>{
    @NonNull
    @Override
    public LendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull LendingViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class LendingViewHolder extends RecyclerView.ViewHolder {

        public LendingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
