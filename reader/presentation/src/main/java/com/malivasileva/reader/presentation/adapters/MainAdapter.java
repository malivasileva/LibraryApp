package com.malivasileva.reader.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.malivasileva.model.Lending;
import com.malivasileva.resources.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    List<Lending> lendingsList;

    public MainAdapter (List<Lending> lendingsList) {
        this.lendingsList = lendingsList;
    }

    public void updateLendings(List<Lending> newLendings) {
        lendingsList.clear();
        lendingsList.addAll(newLendings);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lending_short, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.bind(lendingsList.get(position));
    }

    @Override
    public int getItemCount() {
        return lendingsList.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        private TextView num;
        private TextView title;
        private TextView authors;
        private TextView requiredDate;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.l_short_num);
            title = itemView.findViewById(R.id.l_short_title);
            authors = itemView.findViewById(R.id.l_short_author);
            requiredDate = itemView.findViewById(R.id.l_short_required_date);
        }

        public void bind(Lending lending) {
            num.setText(String.valueOf(lending.getId()));
            title.setText(lending.getTitle());
            authors.setText(lending.getAuthors());
            requiredDate.setText(dateFormat.format(lending.getRequiredDate()));
        }
    }
}
