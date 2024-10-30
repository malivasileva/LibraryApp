package com.malivasileva.librarian.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.malivasileva.model.Lending;
import com.malivasileva.resources.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class LendingAdapter extends RecyclerView.Adapter<LendingAdapter.LendingViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Lending lending);
    }

    private List<Lending> lendingList;
    private OnItemClickListener listener;

    public LendingAdapter(List<Lending> lendingList, OnItemClickListener listener) {
        this.lendingList = lendingList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public LendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lending_full, parent, false);
        return new LendingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LendingViewHolder holder, int position) {
        holder.bind(lendingList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return lendingList.size();
    }

    public void updateList(List<Lending> newList) {
        lendingList.clear();
        lendingList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class LendingViewHolder extends RecyclerView.ViewHolder {

        private TextView num;
        private TextView title;
        private TextView author;
        private TextView lendDate;
        private TextView requiredDate;
        private TextView returnDate;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        public LendingViewHolder(@NonNull View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.l_full_num);
            title = itemView.findViewById(R.id.l_full_title);
            author = itemView.findViewById(R.id.l_full_author);
            lendDate = itemView.findViewById(R.id.l_full_lend_date);
            requiredDate = itemView.findViewById(R.id.l_full_required_date);
            returnDate = itemView.findViewById(R.id.l_full_returned);
        }

        public void bind(final Lending lending, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(lending));

            num.setText(String.valueOf(lending.getId()));
            title.setText(lending.getTitle());
            author.setText(lending.getAuthors());
            lendDate.setText(dateFormat.format(lending.getLendDate()));
            requiredDate.setText(dateFormat.format(lending.getRequiredDate()));

            if (lending.getReturnDate() != null) {
                returnDate.setText(dateFormat.format(lending.getReturnDate()));
            } else {
                returnDate.setText("не возвращено");
                if (lending.getRequiredDate().before(new java.util.Date())) returnDate.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.secondary));
            }
        }
    }
}
