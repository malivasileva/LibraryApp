package com.malivasileva.data;

import android.util.Log;

import com.malivasileva.data.entities.BookEntity;
import com.malivasileva.data.entities.LendingEntity;
import com.malivasileva.data.entities.ReaderEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class DatabaseService {

    public Single<List<BookEntity>> getBooksFor(String request) {
//        Log.d("govno-dbs", "in getBooksFor");
        return Single.fromCallable(() -> {
            List<BookEntity> books = new ArrayList<>();
            String query = "SELECT * FROM books WHERE title ILIKE '%" + request + "%' OR authors ILIKE '%" + request + "%'";
//            Log.d("govno-dbs", "before try-catch");
            try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
//                Log.d("govno-dbs", "in try");
                while (resultSet.next()) {
//                    Log.d("govno-dbs-while", resultSet.getString("title"));
                    BookEntity book = new BookEntity(
                            resultSet.getInt("book_num"),
                            resultSet.getString("title"),
                            resultSet.getString("authors"),
                            resultSet.getString("publisher_address"),
                            resultSet.getString("publisher_name"),
                            resultSet.getInt("page_total"),
                            resultSet.getFloat("price_num"),
                            resultSet.getInt("copy_total"),
                            resultSet.getInt("publishing_year")
                    );
                    books.add(book);
//                    Log.d("govno-dbs", Integer.toString(book.getId()));
                }
//                Log.d("govno-dbs", "in try final!!!!");
            } catch (SQLException e) {
                e.printStackTrace();
//                Log.d("govno-dbs-exception", Arrays.toString(e.getStackTrace()));
                throw new RuntimeException(e);
            }
//            Log.d("govno-dbs", books.get(0).getTitle());
            return books;
        });
    }

    public List<LendingEntity> getLendingsFor(int reader) throws SQLException {
        List<LendingEntity> lendings = new ArrayList<LendingEntity>();

        String query = "SELECT * FROM lendings WHERE card_num = " + reader;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                // Создаем объект Book из данных, полученных из базы данных
                LendingEntity lending = new LendingEntity(
                        resultSet.getInt("lending_num"),
                        resultSet.getInt("book_num"),
                        resultSet.getInt("card_num"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("required_date"),
                        resultSet.getDate("return_date")
                );
                lendings.add(lending);
            }
        }

        return lendings;
    }

    public Single<ReaderEntity> getReader(long id) {
        Log.d("govno-dbs", "in getReader");
        return Single.fromCallable(() -> {
            ReaderEntity reader = null;
            String query = "SELECT * FROM readers WHERE card_num = " + id;

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                Log.d("govno-dbs", "in try");
                resultSet.next();
                reader = new ReaderEntity(
                        resultSet.getLong("card_num"),
                        resultSet.getString("name"),
                        resultSet.getString("phone"),
                        resultSet.getString("address")
                );
                Log.d("govno-dbs-try", reader.getName());
            } catch (Exception e) {
                Log.d("govno-dbs-exception", e.getMessage());
                e.printStackTrace();
//            Log.d("govno-dbs-exception", Arrays.toString(e.getStackTrace()));
            }
            return reader;
        });
    }

    public void updateReader(ReaderEntity reader) {
        //todo
    }

    public void deleteReader(int card) {
        //todo
    }
}
