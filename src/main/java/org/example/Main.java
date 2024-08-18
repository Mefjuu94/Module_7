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


        Book book4 = new Book("amanda","fantasy",223,author);
        Book book5 = new Book("Gra","comedy",533,author);
        Book book6 = new Book("Wojna stulecia","drama",766,author2);
        Book book7 = new Book("Poparaniec 3","horror",545,author1);
        Book book8 = new Book("rozwój osobisty","comedy",422,author1);
        Book book9 = new Book("niekonczaca sie opowiesc","fantasy",999,author1);


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
        dao.addBookToAuthor("Mati",book7);
        dao.addBookToAuthor("Mati",book8);
        dao.addBookToAuthor("Mati",book9);
        dao.addBookToAuthor("Mati",book2);

        dao.addBookToAuthor("Mati Orzel",book4);
        dao.addBookToAuthor("Mati Orzel",book5);

        dao.addBookToAuthor("Orzel",book3);
        dao.addBookToAuthor("Orzel",book6);


        //EntityExistsException
        dao.addBookToAuthor("Mati", book1);


        //PathElementException

        //get books of author"
        System.out.println(dao.getBooksOfAuthor("Mati"));

        List<Book> allBooks = dao.getAllBooks();

        for (Book allBook : allBooks) {
            System.out.println(allBook.toString());
        }

        System.out.println("******************");

        System.out.println(dao.getAllBooksAndAuthors());
        dao.deleteBook("Poparaniec 3");
        dao.deleteBook("ksiazka 1");
        dao.deleteBook("    ");
//        System.out.println(dao.getAllBooksAndAuthors());
        dao.getAllBooks();
//
//        dao.deleteAuthor("Mati");
//        dao.deleteAuthor("Orzel");
//        System.out.println(dao.getAllAuthors());
    }
}