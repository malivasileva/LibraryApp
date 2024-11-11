package com.malivasileva.librarian.presentation.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.malivasileva.model.Reader;
import com.malivasileva.presentation.R;

import java.util.List;

public class ReaderAdapter extends RecyclerView.Adapter<ReaderAdapter.ReaderViewHolder>{

    public interface OnItemClickListener {
        void onItemClick(Reader reader);
    }

    private List<Reader> readerList;
    private final OnItemClickListener listener;

    public ReaderAdapter(
            List<Reader> readerList,
            OnItemClickListener listener
    ) {
        this.readerList = readerList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reader_card, parent, false);
        return new ReaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReaderViewHolder holder, int position) {
        holder.bind(readerList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return readerList.size();
    }

    public void updateReaders(List<Reader> newReaders) {
        readerList.clear();
        readerList.addAll(newReaders);
        notifyDataSetChanged();
    }

    public static class ReaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView readerNum;
        private final TextView name;
        private final TextView phone;
        private final TextView address;

        public ReaderViewHolder(@NonNull View itemView) {
            super(itemView);
            readerNum = itemView.findViewById(R.id.reader_num);
            name = itemView.findViewById(R.id.reader_name);
            phone = itemView.findViewById(R.id.reader_phone);
            address = itemView.findViewById(R.id.reader_address);
        }

        public void bind(Reader reader, final ReaderAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(reader));

            readerNum.setText(String.valueOf(reader.getCard()));
            name.setText(reader.getName());
            phone.setText(reader.getPhone());
            address.setText(reader.getAddress());
        }
    }
}
