package ru.fisher.task1;

public interface Storage {
    void save(String data);
    String retrieve(int id);
}
