package ru.fisher.task1;

import java.sql.*;

public class DatabaseStorage implements Storage {

    private final Connection connection;

    public DatabaseStorage(Connection connection) {
        this.connection = connection;
        createTableIfNotExist();
    }

    private void createTableIfNotExist() {
        String sql = "CREATE TABLE IF NOT EXISTS storage" +
                    "(id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "data VARCHAR NOT NULL)";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка создания таблицы storage", e);
        }
    }

    @Override
    public void save(String data) {
        String sql = "INSERT INTO storage(data) VALUES(?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, data);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Ошибка сохранения данных", ex);
        }
    }

    @Override
    public String retrieve(int id) {
        String sql = "SELECT * FROM storage WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                return set.getString("data");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения данных", e);
        }
        return null;
    }


    public static void main(String[] args) {
        String url = "jdbc:h2:mem:testdb";
        String user = "sa";
        String password = "";

        // Для простоты используем H2 in-memory базу
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            Storage dbStorage = new DatabaseStorage(connection);

            // Сохраняем
            dbStorage.save("Data in Database - 1");
            dbStorage.save("Data in Database - 2");

            // Извлекаем данные
            System.out.println("dbStorage 1 item: " + dbStorage.retrieve(1));
            System.out.println("dbStorage 2 item: " + dbStorage.retrieve(2));

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
