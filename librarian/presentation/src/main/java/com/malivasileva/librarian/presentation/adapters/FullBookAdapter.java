package com.malivasileva.librarian.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.malivasileva.model.Book;
import com.malivasileva.presentation.R;

import org.w3c.dom.Text;

import java.util.List;

public class FullBookAdapter extends RecyclerView.Adapter<FullBookAdapter.FullBookViewHolder> {

    private List<Book> bookList;

    public FullBookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public FullBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_card, parent, false);
        return new FullBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FullBookViewHolder holder, int position) {
        holder.bind(bookList.get(position));
    }

    public void updateList(List<Book> newBookList) {
        bookList.clear();
        bookList.addAll(newBookList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class FullBookViewHolder extends RecyclerView.ViewHolder {

        private final TextView bookNum;
        private final TextView title;
        private final TextView author;
        private final TextView year;
        private final TextView page;
        private final TextView amount;
        private final TextView publisher;
        private final TextView publisherAddress;

        public FullBookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookNum = itemView.findViewById(R.id.book_num);
            title = itemView.findViewById(R.id.book_title);
            author = itemView.findViewById(R.id.book_author);
            year = itemView.findViewById(R.id.book_year);
            page = itemView.findViewById(R.id.book_page);
            amount = itemView.findViewById(R.id.book_amount);
            publisher = itemView.findViewById(R.id.publisher);
            publisherAddress = itemView.findViewById(R.id.publisher_address);
        }

        public void bind(Book book) {

            bookNum.setText(String.valueOf(book.getId()));
            title.setText(book.getTitle());
            author.setText(book.getAuthors());
            year.setText(String.valueOf(book.getYear()));
            page.setText(String.valueOf(book.getPages()));
            amount.setText(String.valueOf(book.getCopies()));
            publisher.setText(String.valueOf(book.getPublisherName()));
            publisherAddress.setText(String.valueOf(book.getPublisheAddress()));

        }
    }
}
