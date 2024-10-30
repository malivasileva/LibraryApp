package com.malivasileva.data;

import android.util.Log;

import com.malivasileva.data.entities.BookEntity;
import com.malivasileva.data.entities.LendingEntity;
import com.malivasileva.data.entities.ReaderEntity;
import com.malivasileva.data.entities.SpecialtyEntity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;


//todo LIBRARIAN CONNECTION
public class DatabaseService {

    private final String TAG = "DatabaseService";

    //todo lendingsRepo
    //current lendings
    //lendings for book
    //todo libr-readers
    //reader with id ??

    public Single<List<SpecialtyEntity>> getAllSpecialties() {
        return Single.fromCallable(() -> {
            List<SpecialtyEntity> specialties = new ArrayList<>();
            String query = "SELECT * FROM public.specialties";
            try (Connection connection = DatabaseConnection.getLibrConnection();
                 PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    SpecialtyEntity specialty = new SpecialtyEntity(
                            resultSet.getInt("specialty_num"),
                            resultSet.getString("specialty_name")
                    );
                    specialties.add(specialty);
                }

            } catch (SQLException e) {
                Log.e(TAG, "Произошла ошибка: " + e.getMessage(), e);
                throw new RuntimeException(e);
            }
            return specialties;
        });
    }

    public Single<List<SpecialtyEntity>> getSpecialtyFor(String request) {
        return Single.fromCallable(() -> {
            List<SpecialtyEntity> specialties = new ArrayList<>();
            String query = "SELECT * FROM public.specialties WHERE specialty_name ILIKE '%" + request + "%'";
            try (Connection connection = DatabaseConnection.getLibrConnection();
                 PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    SpecialtyEntity specialty = new SpecialtyEntity(
                            resultSet.getInt("specialty_num"),
                            resultSet.getString("specialty_name")
                    );
                    specialties.add(specialty);
                }

            } catch (SQLException e) {
                Log.e(TAG, "Произошла ошибка: " + e.getMessage(), e);
                throw new RuntimeException(e);
            }
            return specialties;
        });
    }

    public Single<List<ReaderEntity>> getActiveReaders() {
        return Single.fromCallable(() -> {
            List<ReaderEntity> readers = new ArrayList<>();
            String query = "SELECT DISTINCT\n" +
                    "    R.card_num, \n" +
                    "    R.name, \n" +
                    "    R.phone, \n" +
                    "    R.address\n" +
                    "FROM \n" +
                    "    public.readers R\n" +
                    "JOIN \n" +
                    "    public.book_lendings BL ON R.card_num = BL.card_num\n" +
                    "WHERE \n" +
                    "    BL.fact_return_date IS NULL;";
            try (Connection connection = DatabaseConnection.getLibrConnection();
                 PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    ReaderEntity reader = new ReaderEntity(
                            resultSet.getLong("card_num"),
                            resultSet.getString("name"),
                            resultSet.getString("phone"),
                            resultSet.getString("address")
                    );
                    readers.add(reader);
                }

            } catch (SQLException e) {
                Log.e(TAG, "Произошла ошибка: " + e.getMessage(), e);
                throw new RuntimeException(e);
            }
            return readers;
        });
    }

    public Single<List<ReaderEntity>> getReadersFor(String request) {
        return Single.fromCallable(() -> {
            List<ReaderEntity> readers = new ArrayList<>();
            String query = "SELECT * FROM public.readers WHERE name ILIKE '%" + request + "%' OR phone ILIKE '%" + request + "%'";
            try (Connection connection = DatabaseConnection.getLibrConnection();
                 PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    ReaderEntity reader = new ReaderEntity(
                            resultSet.getLong("card_num"),
                            resultSet.getString("name"),
                            resultSet.getString("phone"),
                            resultSet.getString("address")
                    );
                    readers.add(reader);
                }

            } catch (SQLException e) {
                Log.e(TAG, "Произошла ошибка: " + e.getMessage(), e);
                throw new RuntimeException(e);
            }
            return readers;
        });
    }

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
                Log.e(TAG, "Произошла ошибка: " + e.getMessage(), e);
                throw new RuntimeException(e);
            }
            return books;
        });
    }

    public Single<Boolean> updateLending(LendingEntity lending) {
        return Single.create( emitter -> {
            String query = "UPDATE public.book_lendings\n" +
                    "SET " +
                    "book_num=?, " +
                    "card_num=?, " +
                    "required_return_date=?\n" +
                    "WHERE lending_num=?;";

            try (Connection connection = DatabaseConnection.getLibrConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, lending.getBookId());
                statement.setInt(2, lending.getReaderId());
                statement.setDate(3, new Date (lending.getRequiredDate().getTime()));
                statement.setInt(4, lending.getId());

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    emitter.onSuccess(true);
                } else {
                    emitter.onSuccess(false);
                }

            } catch (SQLException e) {
                Log.e(TAG, "Произошла ошибка: " + e.getMessage(), e);
                emitter.onError(e);
            }

        });
    }

    public Single<Boolean> expendReturnDate(int lendingId) {
        return Single.create(emitter -> {
            String query = "UPDATE public.book_lendings " +
                    "SET required_return_date = add_days_to_required_return_date(?) " +
                    "WHERE lending_num = ?;";

            try (Connection connection = DatabaseConnection.getLibrConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                // Устанавливаем lendingId как параметр для функции
                statement.setInt(1, lendingId);
                statement.setInt(2, lendingId);

                int rowsAffected = statement.executeUpdate();

                // Если строки были затронуты, возвращаем true
                if (rowsAffected > 0) {
                    emitter.onSuccess(true);
                } else {
                    emitter.onSuccess(false);
                }

            } catch (SQLException e) {
                Log.e(TAG, "Произошла ошибка: " + e.getMessage(), e);
                emitter.onError(e);
            }
        });
    }

    public Single<Boolean> returnBook(int lendingId) {
        return Single.create(emitter -> {
            // Выполняем вызов функции set_fact_return_date
            String callFunctionQuery = "SELECT set_fact_return_date(?);";

            try (Connection connection = DatabaseConnection.getLibrConnection();
                 PreparedStatement callStatement = connection.prepareStatement(callFunctionQuery)) {

                // Устанавливаем lendingId для вызова функции
                callStatement.setInt(1, lendingId);
                // Используем executeQuery(), поскольку функция возвращает результат
                callStatement.executeQuery();

                // Если выполнение прошло успешно, возвращаем true
                emitter.onSuccess(true);

            } catch (SQLException e) {
                Log.e(TAG, "Произошла ошибка: " + e.getMessage(), e);
                emitter.onError(e);
            }
        });
    }



    public Single<LendingEntity> getLendingWitId(int id) {
        return Single.fromCallable(() -> {
            LendingEntity lending;
            String query = "SELECT L.lending_num, R.card_num, R.name, B.book_num, B.title, B.authors, L.lending_date, L.required_return_date, L.fact_return_date " +
                    "FROM book_lendings L, books B, readers R  " +
                    "WHERE L.lending_num = ? and L.book_num = B.book_num and L.card_num = R.card_num " +
                    "ORDER BY lending_date DESC";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, id);

                ResultSet resultSet = statement.executeQuery();

                resultSet.next();
                // Создаем объект Book из данных, полученных из базы данных
                lending = new LendingEntity(
                        resultSet.getInt("lending_num"),
                        resultSet.getInt("card_num"),
                        resultSet.getString("name"),
                        resultSet.getInt("book_num"),
                        resultSet.getString("title"),
                        resultSet.getString("authors"),
                        resultSet.getDate("lending_date"),
                        resultSet.getDate("required_return_date"),
                        resultSet.getDate("fact_return_date")
                );
            }
            return lending;
        });
    }

    public Single<List<LendingEntity>> getLendingsForReader(long reader) {
        return Single.fromCallable(() -> {
            List<LendingEntity> lendings = new ArrayList<LendingEntity>();
            String query = "SELECT L.lending_num, B.title, B.authors, L.lending_date, L.required_return_date, L.fact_return_date " +
                    "FROM book_lendings L, books B " +
                    "WHERE card_num = ? and L.book_num = B.book_num " +
                    "ORDER BY lending_date DESC";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setLong(1, reader);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    // Создаем объект Book из данных, полученных из базы данных
                    LendingEntity lending = new LendingEntity(
                            resultSet.getInt("lending_num"),
                            0,
                            "name",
                            0,
                            resultSet.getString("title"),
                            resultSet.getString("authors"),
                            resultSet.getDate("lending_date"),
                            resultSet.getDate("required_return_date"),
                            resultSet.getDate("fact_return_date")
                    );
                    lendings.add(lending);
                }
            }
            return lendings;
        });
    }

    public Single<List<LendingEntity>> getLendingsForBook(int book) { //todo
        return Single.fromCallable(() -> {
            List<LendingEntity> lendings = new ArrayList<LendingEntity>();
            String query = "SELECT L.lending_num, R.card_num, R.name, B.book_num, B.title, B.authors, L.lending_date, L.required_return_date, L.fact_return_date " +
                    "FROM book_lendings L, books B, readers R  " +
                    "WHERE book_num = ? and L.book_num = B.book_num and L.card_num = R.card_num " +
                    "ORDER BY lending_date DESC";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, book);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    // Создаем объект Book из данных, полученных из базы данных
                    LendingEntity lending = new LendingEntity(
                            resultSet.getInt("lending_num"),
                            0,
                            "name",
                            0,
                            resultSet.getString("title"),
                            resultSet.getString("authors"),
                            resultSet.getDate("lending_date"),
                            resultSet.getDate("required_return_date"),
                            resultSet.getDate("fact_return_date")
                    );
                    lendings.add(lending);
                }
            }
            return lendings;
        });
    }

    public Single<List<LendingEntity>> getCurrentLendingsFor(long reader) {
        return Single.fromCallable(() -> {
            List<LendingEntity> lendings = new ArrayList<LendingEntity>();
            String query = "SELECT L.lending_num, B.title, B.authors, L.lending_date, L.required_return_date, L.fact_return_date " +
                    "FROM book_lendings L, books B " +
                    "WHERE card_num = ? and L.book_num = B.book_num and L.fact_return_date is null " +
                    "ORDER BY lending_date DESC";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setLong(1, reader);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    // Создаем объект Book из данных, полученных из базы данных
                    LendingEntity lending = new LendingEntity(
                            resultSet.getInt("lending_num"),
                            0,
                            "name",
                            0,
                            resultSet.getString("title"),
                            resultSet.getString("authors"),
                            resultSet.getDate("lending_date"),
                            resultSet.getDate("required_return_date"),
                            null
                    );
                    lendings.add(lending);
                }
            }
            return lendings;
        });

    }

    public Single<List<LendingEntity>> getAllCurrentLendings() {
        return Single.fromCallable(() -> {
            List<LendingEntity> lendings = new ArrayList<LendingEntity>();
            String query = "SELECT L.lending_num, B.title, B.authors, L.lending_date, L.required_return_date, L.fact_return_date " +
                    "FROM book_lendings L, books B " +
                    "WHERE L.book_num = B.book_num AND L.fact_return_date IS NULL " +
//                    "AND DATE(L.required_return_date) <= CURRENT_DATE " +
                    "ORDER BY required_return_date ASC";


            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery();) {

                while (resultSet.next()) {
                    // Создаем объект Book из данных, полученных из базы данных
                    LendingEntity lending = new LendingEntity(
                            resultSet.getInt("lending_num"),
                            0,
                            "name",
                            0,
                            resultSet.getString("title"),
                            resultSet.getString("authors"),
                            resultSet.getDate("lending_date"),
                            resultSet.getDate("required_return_date"),
                            null
                    );
                    lendings.add(lending);
                }
            }
            return lendings;
        });

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
                Log.e(TAG, "Произошла ошибка: " + e.getMessage(), e);
                emitter.onError(e);
            }

        });
    }

    public Single<Boolean> deleteReader(long card) {
        return Single.create(emitter -> {
            String query = "UPDATE public.readers " +
                    "SET name = 'deleted', " +
                    "phone = 'deleted', " +
                    "address = 'deleted', " +
                    "pw = 'deleted' " +
                    "WHERE card_num = ?";
            try (Connection connection = DatabaseConnection.getReaderConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setLong(1, card);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    emitter.onSuccess(true);
                } else {
                    emitter.onSuccess(false);
                }
            } catch (SQLException e) {
                Log.e(TAG, "Произошла ошибка: " + e.getMessage(), e);
                emitter.onError(e);
            }
        });
    }
}
