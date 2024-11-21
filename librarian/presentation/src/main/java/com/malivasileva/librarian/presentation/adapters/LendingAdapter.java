package com.malivasileva.librarian.presentation.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.malivasileva.model.Lending;
import com.malivasileva.resources.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LendingAdapter extends RecyclerView.Adapter<LendingAdapter.LendingViewHolder> implements Filterable {

    public interface OnItemClickListener {
        void onItemClick(Lending lending);
    }

    private final List<Lending> originalList;
    private final List<Lending> filteredList;

    private final OnItemClickListener listener;

    public LendingAdapter(List<Lending> lendingList,
                          OnItemClickListener listener) {
        this.originalList = lendingList;
        this.filteredList = new ArrayList<>(lendingList);
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
        holder.bind(filteredList.get(position), listener);  // Используем filteredList
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void updateList(List<Lending> newLendings) {
        originalList.clear();
        originalList.addAll(newLendings);
        getFilter().filter("");

        notifyDataSetChanged();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Lending> filteredResults = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    // Если строка поиска пустая, возвращаем оригинальный список
                    filteredResults.addAll(originalList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Lending lending : originalList) {
                        if (lending.getReaderName().toLowerCase().contains(filterPattern) ||
                                lending.getTitle().toLowerCase().contains(filterPattern) ||
                                lending.getAuthors().toLowerCase().contains(filterPattern)) {
                            filteredResults.add(lending);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;
                return results;
            }


            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                List<Lending> result = (List<Lending>) results.values;
                filteredList.clear();
                filteredList.addAll(result);
                notifyDataSetChanged();
            }
        };
    }


    public static class LendingViewHolder extends RecyclerView.ViewHolder {

        private TextView num;
        private TextView title;
        private TextView author;
        private TextView lendDate;
        private TextView requiredDate;
        private TextView returnDate;
        private TextView readerName;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        public LendingViewHolder(@NonNull View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.l_full_num);
            title = itemView.findViewById(R.id.l_full_title);
            author = itemView.findViewById(R.id.l_full_author);
            lendDate = itemView.findViewById(R.id.l_full_lend_date);
            requiredDate = itemView.findViewById(R.id.l_full_required_date);
            returnDate = itemView.findViewById(R.id.l_full_returned);
            readerName = itemView.findViewById(R.id.reader_name);
        }

        public void bind(final Lending lending, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(lending));

            readerName.setText(lending.getReaderName());
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
