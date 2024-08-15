package org.example;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.NoResultException;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        List<Book> listOfBooks = new ArrayList<>();

        Author author = new Author("Mati Orzel",29,"fantasy",new ArrayList<>());
        Author author1 = new Author("Mati",9,"drama",new ArrayList<>());
        Author author2 = new Author("Orzel",22,"comedy",new ArrayList<>());

        Book book1 = new Book("ksiazka 1","fantasy",123,author);
        Book book3 = new Book("ksiazka 3","comedy",533,author);
        Book book2 = new Book("ksiazka 2","fantasy",233,author2);

        listOfBooks.add(new Book("ksiazka 1","fantasy",111,author2));
        listOfBooks.add(new Book("takie tam","drama",333,author2));
        listOfBooks.add(new Book("hello","comedy",223,author2));
        listOfBooks.add(new Book("ksiazka 51","fantasy",734,author2));


        LibrayDAO dao = new LibrayDAO();

        //*add Author
        dao.addAuthor(author1);
        dao.addAuthor(author);
        dao.addAuthor(author2);
        //*find Author
        try {
            System.out.println("result: " + dao.findAuthor("Mati Orz"));
        }catch (NoResultException e){
            System.out.println("there is no Author with that name");
        }

        //get all authors
        try {
            System.out.println("result: " + dao.getAllAuthors());
        }catch (NoResultException e){
            System.out.println("there is no results");
        }

        //add book to author
        dao.addBookToAuthor("Mati",book1);
        dao.addBookToAuthor("Mati",book2);
        dao.addBookToAuthor("Orzel",book3);
        //EntityExistsException
        try {
            dao.addBookToAuthor("Mati", book1);
        }catch (EntityExistsException e){
            System.out.println("taki rekord już istnieje");
        }


        //PathElementException
        System.out.println("ksiąki autora: " + dao.getBooksOfAuthor("Mati"));

       // dao.deleteAuthor("Mati Orzel");
    }
}