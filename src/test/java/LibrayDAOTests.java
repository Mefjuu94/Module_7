import org.example.Author;
import org.example.Book;
import org.example.LibrayDAO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibrayDAOTests {

    private final LibrayDAO testObject = new LibrayDAO();
    private final List<Book> books = new ArrayList<>();
    private final Author testAuthor = new Author("Test", 99, "Favourite", new ArrayList<>());

    private void setListOfBooksToAuthor() {
        books.add(new Book("Test1", "genere", 99, testAuthor));
        books.add(new Book("Test2", "genere", 199, testAuthor));
        books.add(new Book("Test3", "genere", 299, testAuthor));
        testAuthor.setBooks(books);
    }

    @Test
    public void addAuthorTest() {
        setListOfBooksToAuthor();
        testObject.addAuthor(testAuthor);
        assertTrue(testObject.addAuthor(testAuthor));
    }

    @Test
    public void addAuthorTestFail() {
        setListOfBooksToAuthor();
        testObject.addAuthor(testAuthor);
        assertTrue(testObject.addAuthor(testAuthor));
    }


    @Test
    public void findAuthorTest() {
        setListOfBooksToAuthor();
        testObject.addAuthor(testAuthor);

        assertEquals(testAuthor.toString(), testObject.findAuthor("Test").toString());
    }

    @Test
    public void findAuthorTestFail() {
        setListOfBooksToAuthor();
        testObject.addAuthor(testAuthor);

        assertNull(testObject.findAuthor("Tested"));
    }

    @Test
    public void getBooksOfAuthorTest() {
        setListOfBooksToAuthor();
        testObject.addAuthor(testAuthor);

        assertEquals(books, testObject.getBooksOfAuthor("Test"));
    }

    @Test
    public void getBooksOfAuthorFailTest() {
        setListOfBooksToAuthor();
        testObject.addAuthor(testAuthor);

        assertNotEquals(books, testObject.getBooksOfAuthor("Tested"));
    }

    @Test
    public void deleteBookTest() {
        setListOfBooksToAuthor();
        testObject.addAuthor(testAuthor);

        assertTrue(testObject.deleteBook("Test1"));
    }

    @Test
    public void deleteAuthorTest() {
        setListOfBooksToAuthor();
        testObject.addAuthor(testAuthor);

        assertTrue(testObject.deleteAuthor("Test"));
    }



    @Test
    public void addBookToAuthorTest() {
        setListOfBooksToAuthor();
        testObject.addAuthor(testAuthor);

        Book b1 = new Book("Test99", "genere", 599, testAuthor);
        testObject.addBookToAuthor("Test",b1);
        books.add(b1);
        assertEquals(books,testObject.getBooksOfAuthor("Test"));
    }


    @Test
    public void getAllAuthorsTest() {
        setListOfBooksToAuthor();
        testObject.addAuthor(testAuthor);
        List<Author> testAuthorsList = new ArrayList<>();
        testAuthorsList.add(testAuthor);

        assertEquals(testAuthorsList,testObject.getAllAuthors());
    }

    @Test
    public void getAllAuthorsTestFail() {
        setListOfBooksToAuthor();
        testObject.addAuthor(testAuthor);
        List<Author> testAuthorsList = new ArrayList<>();

        assertNotEquals(testAuthorsList,testObject.getAllAuthors());
    }

    @Test
    public void getAllBooks() {
        setListOfBooksToAuthor();
        testObject.addAuthor(testAuthor);

        assertEquals(books,testObject.getAllBooks());
    }

    @Test
    public void getAllBooksAndAuthors() {
        setListOfBooksToAuthor();
        testObject.addAuthor(testAuthor);

        List<Author> authorsList = testObject.getAllAuthors();
        List<Book> booksList = testObject.getAllBooks();

        List<String> authorsNames = new ArrayList<>();
        List<String> booksNames = new ArrayList<>();

        for (Author author : authorsList) {
            authorsNames.add(author.getName());
        }
        for (Book books : booksList) {
            booksNames.add(books.getTitle());
        }

        String result = authorsNames.toString() + "\n" + booksNames.toString();

        assertEquals(result,testObject.getAllBooksAndAuthors());
    }

}
