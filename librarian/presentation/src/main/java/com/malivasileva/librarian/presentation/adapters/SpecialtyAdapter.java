package com.malivasileva.librarian.presentation.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.malivasileva.model.Specialty;
import com.malivasileva.presentation.R;

import java.util.List;

public class SpecialtyAdapter extends RecyclerView.Adapter<SpecialtyAdapter.SpecialtyViewHolder> {

    public interface OnItemClickListener {
        void OnItemClick(Specialty specialty);
    }

    private List<Specialty> specialtyList;
    private OnItemClickListener listener;

    public SpecialtyAdapter (
            List<Specialty> specialtyList,
            OnItemClickListener listener
    ) {
        this.listener = listener;
        this.specialtyList = specialtyList;
    }

    @NonNull
    @Override
    public SpecialtyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.specialty_card, parent, false);
        return new SpecialtyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialtyViewHolder holder, int position) {
        holder.bind(specialtyList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return specialtyList.size();
    }

    public void updateList(List<Specialty> newList) {
        specialtyList.clear();
        specialtyList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class SpecialtyViewHolder extends RecyclerView.ViewHolder {

        private final TextView num;
        private final TextView name;

        public SpecialtyViewHolder(@NonNull View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.specialty_num);
            name = itemView.findViewById(R.id.specialty_name);
        }

        public void bind(Specialty specialty, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.OnItemClick(specialty));

            num.setText(String.valueOf(specialty.getNum()));
            name.setText(specialty.getName());
        }
    }
}
