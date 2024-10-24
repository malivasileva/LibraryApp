package com.malivasileva.reader.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.malivasileva.model.Book;
import com.malivasileva.reader.presentation.R;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> bookList;

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
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

        private TextView bookNum;
        private TextView title;
        private TextView author;
        private TextView year;
        private TextView page;
        private TextView amount;

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
