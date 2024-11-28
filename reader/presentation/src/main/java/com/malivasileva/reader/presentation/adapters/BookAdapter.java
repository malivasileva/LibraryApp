package com.malivasileva.reader.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.malivasileva.model.Book;
import com.malivasileva.resources.R;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> bookList;
    private OnItemClickListener listener;

    public BookAdapter(List<Book> bookList, OnItemClickListener listener) {

        this.bookList = bookList;
        this.listener = listener;
    }

    // Вложенный интерфейс для обработки нажатий
    public interface OnItemClickListener {
        void onItemClick(Book book);
    }


    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_full, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bind(book);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void updateBooks(List<Book> newBooks) {
        bookList.clear();
        bookList.addAll(newBooks);
        notifyDataSetChanged();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {

        private final TextView bookNum;
        private final TextView title;
        private final TextView author;
        private final TextView year;
        private final TextView page;
        private final TextView amount;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookNum = itemView.findViewById(R.id.book_num);
            title = itemView.findViewById(R.id.book_title);
            author = itemView.findViewById(R.id.book_author);
            year = itemView.findViewById(R.id.book_year);
            page = itemView.findViewById(R.id.book_page);
            amount = itemView.findViewById(R.id.book_amount);
        }

        public void bind (Book book) {
            bookNum.setText(String.valueOf(book.getId()));
            title.setText(book.getTitle());
            author.setText(book.getAuthors());
            year.setText(String.valueOf(book.getYear()));
            page.setText(String.valueOf(book.getPages()));
            amount.setText(String.valueOf(book.getCopies()));
        }
    }
}
