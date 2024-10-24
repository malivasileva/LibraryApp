package com.malivasileva.reader.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.malivasileva.model.Lending;
import com.malivasileva.reader.presentation.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class LendingAdapter extends RecyclerView.Adapter<LendingAdapter.LendingViewHolder>{
    private List<Lending> lendingList;

    public LendingAdapter(List<Lending> lendingList) { this.lendingList = lendingList; }

    public void updateLendings(List<Lending> newLendings) {
        lendingList.clear();
        lendingList.addAll(newLendings);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lending_full, parent, false);
        return new LendingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LendingViewHolder holder, int position) {
        Lending lending = lendingList.get(position);
        holder.bind(lending);
    }

    @Override
    public int getItemCount() {
        return lendingList.size();
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

        public void bind(Lending lending) {
            num.setText(String.valueOf(lending.getId()));
            title.setText(lending.getTitle());
            author.setText(lending.getAuthors());
            lendDate.setText(dateFormat.format(lending.getLendDate()));
            requiredDate.setText(dateFormat.format(lending.getRequiredDate()));

            if (lending.getReturnDate() != null) {
                returnDate.setText(dateFormat.format(lending.getReturnDate()));
            } else {
                returnDate.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.secondary));
                returnDate.setText("не возвращено");
            }
        }
    }
}
