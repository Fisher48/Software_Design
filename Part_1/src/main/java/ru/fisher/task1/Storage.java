package ru.fisher.task1;

import java.sql.SQLException;

public interface Storage {
    void save(String data);
    String retrieve(int id);
}
