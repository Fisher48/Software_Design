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
        this.books = books;
    }

    public Person takeBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Книга не может быть пустой");
        }
        this.books.add(book);
        return new Person(this.name, this.books);
    }

    public Person returnBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Книга не может быть пустой");
        }
        books.remove(book);
        return new Person(this.name, this.books);
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
        Book book = new Book("SQL База","Алан Бьюли", 500);
        Book book2 = new Book("Основы программирования на Java","Герберт Шилдт", 1900);
        Book book3 = new Book("Spring быстро","Лауренциу Спилкэ", 450);
        Person person = new Person("Читатель", new ArrayList<>());
        person.takeBook(book);
        person.takeBook(book2);
        person.takeBook(book3);
        System.out.println(person);
        person.returnBook(book);

        Book book4 = new Book("Объектно-ориентированное мышление","Мэтт Вайсфельд", 450);
        Person person2 = new Person("Иван", Collections.singletonList(book4));
        person.returnBook(book4);
        System.out.println(person);

        System.out.println(person2);

        // Будет ошибка
        Book book5 = new Book("","", 1);
    }
}