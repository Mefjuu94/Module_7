package org.example;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class LibrayDAO {

    private final SessionFactory sessionFactory = UserSessionFactory.getUserSessionFactory();

    public List<Book> getBooksOfAuthor(String authorName) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Book> userQuery = cb.createQuery(Book.class);
        Root<Book> root = userQuery.from(Book.class);
        Join<Book, Author> authorJoin = root.join("author");
        userQuery.select(root).where(cb.equal(authorJoin.get("name"), authorName));

        return session.createQuery(userQuery).getResultList();
    }

    public Author findAuthor(String authorName) {
        try {
            Session session = sessionFactory.openSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Author> userQuery = cb.createQuery(Author.class);
            Root<Author> root = userQuery.from(Author.class);
            userQuery.select(root).where(cb.equal(root.get("name"), authorName));
            return session.createQuery(userQuery).getSingleResult();
        } catch (NoResultException e) {
            System.out.println("there is no Author with that name");
            return null;
        }
    }

    public boolean addBookToAuthor(String authorName, Book book) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Author> cq = cb.createQuery(Author.class);
            Root<Author> authorRoot = cq.from(Author.class);

            cq.select(authorRoot).where(cb.equal(authorRoot.get("name"), authorName));
            Author author = session.createQuery(cq).getSingleResult();

            book.setAuthor(author);
            session.persist(book);

            session.getTransaction().commit();
            return true;
        }catch (EntityExistsException e){
            System.out.println("same record already exist");
        }
        return false;
    }

    public List<Author> getAllAuthors() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Author> userQuery = cb.createQuery(Author.class);
        Root<Author> root = userQuery.from(Author.class);
        userQuery.select(root);
        return session.createQuery(userQuery).getResultList();
    }

    public List<Book> getAllBooks() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> bookRoot = cq.from(Book.class);
        cq.select(bookRoot);
        return session.createQuery(cq).getResultList();
    }

    public String getAllBooksAndAuthors() {
        List<Author> authorsList = getAllAuthors();
        List<Book> booksList = getAllBooks();
        List<String> authorsNames = new ArrayList<>();
        List<String> booksNames = new ArrayList<>();
        for (Author author : authorsList) {
            authorsNames.add(author.getName());
        }
        for (Book books : booksList) {
            booksNames.add(books.getTitle());
        }
        return authorsNames.toString() + "\n" + booksNames.toString();
    }

    public boolean addAuthor(Author author) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.merge(author);
            transaction.commit();
            session.close();
            return true;
        } catch (EntityExistsException e) {
            System.out.println("EntityExistsException: entity alerady exist.");
            return false;
        }
    }

    public boolean deleteBook(String title) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaDelete<Book> delete = cb.createCriteriaDelete(Book.class);
            Root<Book> bookRoot = delete.from(Book.class);

            delete.where(cb.equal(bookRoot.get("title"), title));

            session.createMutationQuery(delete).executeUpdate();
            session.getTransaction().commit();
            return true;
        }catch (EntityNotFoundException e){
            System.out.println("record not found!");
        }
        return false;
    }

    public boolean deleteAuthor(String authorName) {
        try {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<Author> delete = cb.createCriteriaDelete(Author.class);
        Root<Author> authorRoot = delete.from(Author.class);

        delete.where(cb.equal(authorRoot.get("name"), authorName));

        session.createMutationQuery(delete).executeUpdate();
        session.getTransaction().commit();
        return true;
        }catch (org.hibernate.HibernateException e){
            System.out.println("??");
        }
        return false;
    }
}
