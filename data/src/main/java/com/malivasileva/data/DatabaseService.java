package com.malivasileva.data;

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
        return Single.fromCallable(() -> {
            List<BookEntity> books = new ArrayList<>();
            String query = "SELECT * FROM books WHERE title ILIKE '%" + request + "%' OR authors ILIKE '%" + request + "%'";
            try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
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
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
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
        return Single.fromCallable(() -> {
            ReaderEntity reader = null;
            String query = "SELECT * FROM readers WHERE card_num = " + id;

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                resultSet.next();
                reader = new ReaderEntity(
                        resultSet.getLong("card_num"),
                        resultSet.getString("name"),
                        resultSet.getString("phone"),
                        resultSet.getString("address")
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            return reader;
        });
    }

    public Single<Boolean> updateReader(ReaderEntity reader) {
        return Single.create( emitter -> {

            String query = "UPDATE public.readers " +
                    "SET name=?, phone=?, address=? WHERE card_num=?;";

            try (Connection connection = DatabaseConnection.getReaderConnection();
                 PreparedStatement statement = connection.prepareStatement(query);) {

                statement.setString(1, reader.getName());
                statement.setString(2, reader.getPhone());
                statement.setString(3, reader.getAddress());
                statement.setLong(4, reader.getId());

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    emitter.onSuccess(true);
                } else {
                    emitter.onSuccess(false);
                }

            } catch (SQLException e) {
                emitter.onError(e);
            }

                });
    }

    public void deleteReader(int card) {
        //todo
    }
}
