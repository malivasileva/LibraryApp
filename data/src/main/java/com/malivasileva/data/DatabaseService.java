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

public class DatabaseService {

    public List<BookEntity> getBooksFor(String request) throws SQLException {
        List<BookEntity> books = new ArrayList<>();
        String query = "SELECT * FROM books WHERE title ILIKE '%'||'" + request
                + "'||'%' OR author ILIKE '%'||'" + request + "'||'%'";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                // Создаем объект Book из данных, полученных из базы данных
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
        }
        return books;
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

    public ReaderEntity getReader(int id) throws SQLException {
        ReaderEntity reader = null;
        String query = "SELECT * FROM readers WHERE card_num = " + id;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            resultSet.next();
            reader = new ReaderEntity(
                    resultSet.getInt("card_num"),
                    resultSet.getString("name"),
                    resultSet.getString("phone"),
                    resultSet.getString("address")
            );
        }
        return reader;
    }

    public void updateReader(ReaderEntity reader) {
        //todo
    }

    public void deleteReader(int card) {
        //todo
    }
}
