package com.malivasileva.librarian.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.malivasileva.model.Sylabus;
import com.malivasileva.presentation.R;

import java.util.List;

public class SylabusAdapter extends RecyclerView.Adapter<SylabusAdapter.SylabusViewHolder> {

    public interface OnDeleteClickListener {
        void OnDeleteClick(int book, int studyPlan);
    }

    private final List<Sylabus> sylabusList;
    private final OnDeleteClickListener listener;

    public SylabusAdapter(
            List<Sylabus> list,
            OnDeleteClickListener listener
    ) {
        sylabusList = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SylabusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sylabus_card, parent, false);
        return new SylabusAdapter.SylabusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SylabusViewHolder holder, int position) {
        Sylabus sylabus = sylabusList.get(position);
        holder.bind(sylabus);

        holder.deleteButton.setOnClickListener(v -> {
            listener.OnDeleteClick(sylabus.getBookNum(), sylabus.getStudyPlanNum());
        });
    }

    @Override
    public int getItemCount() {
        return sylabusList.size();
    }

    public void updateList(List<Sylabus> list) {
        sylabusList.clear();
        sylabusList.addAll(list);
        notifyDataSetChanged();
    }

    public static class SylabusViewHolder extends RecyclerView.ViewHolder {

        TextView subject;
        TextView bookNum;
        TextView title;
        TextView authors;
        ImageButton deleteButton;

        public SylabusViewHolder(@NonNull View itemView) {
            super(itemView);

            subject = itemView.findViewById(R.id.subject);
            bookNum = itemView.findViewById(R.id.book_num);
            title = itemView.findViewById(R.id.title);
            authors = itemView.findViewById(R.id.authors);
            deleteButton = itemView.findViewById(R.id.delete_button);

        }

        public void bind(Sylabus sylabus){
            subject.setText(sylabus.getSubject());
            bookNum.setText(Integer.toString(sylabus.getBookNum()));
            title.setText(sylabus.getTitle());
            authors.setText(sylabus.getAuthors());
        }
    }
}
