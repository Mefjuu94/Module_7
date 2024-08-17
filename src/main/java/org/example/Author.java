package org.example;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Author {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private int age;
    private String favouriteGenre;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;

    public Author() {
    }

    public Author(String name, int age, String favouriteGenre, List<Book> books) {
        this.name = name;
        this.age = age;
        this.favouriteGenre = favouriteGenre;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFavouriteGenre() {
        return favouriteGenre;
    }

    public void setFavouriteGenre(String favouriteGenre) {
        this.favouriteGenre = favouriteGenre;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", favouriteGenre='" + favouriteGenre + '\'' +
                ", books=" + books +
                '}';
    }
}
