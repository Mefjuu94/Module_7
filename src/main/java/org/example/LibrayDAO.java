package org.example;


import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class LibrayDAO {

    private final SessionFactory sessionFactory = UserSessionFactory.getUserSessionFactory();

    public List<Book> getBooksOfAuthor(String authorName){
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Book> userQuery = cb.createQuery(Book.class);
        Root<Book> root = userQuery.from(Book.class);
        Join<Book, Author> authorJoin = root.join("author");
        userQuery.select(root).where(cb.equal(authorJoin.get("name"), authorName));

        return session.createQuery(userQuery).getResultList();
    }

    public Author findAuthor(String authorName) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Author> userQuery = cb.createQuery(Author.class);
        Root<Author> root = userQuery.from(Author.class);
        userQuery.select(root).where(cb.equal(root.get("name"), authorName));

        return session.createQuery(userQuery).getSingleResult();
    }

    public void addBookToAuthor(String authorName, Book book){
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
    }
    public List<Author> getAllAuthors(){
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Author> userQuery = cb.createQuery(Author.class);
        Root<Author> root = userQuery.from(Author.class);
        userQuery.select(root);
        return session.createQuery(userQuery).getResultList();
    }
    public List<Book> getAllBooks(){
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> bookRoot = cq.from(Book.class);

        cq.select(bookRoot);

        return session.createQuery(cq).getResultList();
    }
    public List<Object[]> getAllBooksAndAuthors() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Book> bookRoot = cq.from(Book.class);
        Join<Book, Author> authorJoin = bookRoot.join("author");

        cq.multiselect(bookRoot.get("title"), authorJoin.get("name"));

        return session.createQuery(cq).getResultList();
    }
    public void addAuthor(Author author) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(author);
        transaction.commit();
        session.close();
    }

    public void deleteBook(String title){
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<Book> delete = cb.createCriteriaDelete(Book.class);
        Root<Book> bookRoot = delete.from(Book.class);

        delete.where(cb.equal(bookRoot.get("title"), title));

        session.createMutationQuery(delete).executeUpdate();
        session.getTransaction().commit();
    }
    public void deleteAuthor(String authorName){
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<Author> delete = cb.createCriteriaDelete(Author.class);
        Root<Author> authorRoot = delete.from(Author.class);

        delete.where(cb.equal(authorRoot.get("name"), authorName));

        session.createMutationQuery(delete).executeUpdate();
        session.getTransaction().commit();
    }
}
