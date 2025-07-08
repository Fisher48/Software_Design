package ru.fisher.task10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Person {

    private final String name;

    private final List<Book> books;

    public Person(String name, List<Book> books) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Название должно быть указано и не может быть null");
        }
        if (books.contains(null)) {
            throw new IllegalArgumentException("Список книг не может содержать null");
        }
        this.name = name;
        this.books = List.copyOf(books);
    }

    public Person takeBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Книга не может быть пустой");
        }
        List<Book> updatedBooks = new ArrayList<>(books);
        updatedBooks.add(book);
        return new Person(this.name, updatedBooks);
    }

    public Person returnBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Книга не может быть пустой");
        }
        List<Book> updatedBooks = new ArrayList<>(books);
        updatedBooks.remove(book);
        return new Person(this.name, updatedBooks);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(books, person.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, books);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", books=" + books +
                '}' + "\n";
    }
}


final class Book {

    private final String title;
    private final String author;
    private final Integer pages;

    public Book(String title, String author, Integer pages) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Название должно быть указано и не может быть null");
        }
        if (author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Автор должен быть указан и не может быть null");
        }
        if (pages < 1) {
            throw new IllegalArgumentException("В книге не может быть меньше 1-й страницы");
        }
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(pages, book.pages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, pages);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", pages=" + pages +
                '}' + "\n";
    }

    public static void main(String[] args) {
        Book b1 = new Book("SQL База", "Алан Бьюли", 500);
        Book b2 = new Book("Java", "Г. Шилдт", 1000);
        Book b3 = new Book("Spring", "Л. Спилкэ", 450);

        Person reader = new Person("Читатель", new ArrayList<>());

        reader = reader.takeBook(b1);
        reader = reader.takeBook(b2);
        reader = reader.takeBook(b3);

        System.out.println("После взятия книг: " + reader);

        reader = reader.returnBook(b1);

        System.out.println("После возврата одной книги: " + reader);

        // Будет ошибка
        try {
            new Book("","", 1);
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}