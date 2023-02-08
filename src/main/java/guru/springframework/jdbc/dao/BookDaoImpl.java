package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
//import guru.springframework.jdbc.domain.Book;
//import guru.springframework.jdbc.repositories.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Query;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jt on 8/28/21.
 */
@Component
public class BookDaoImpl implements BookDao {

    private final EntityManagerFactory emf;

    public BookDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Book findByISBN(String isbn) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery("SELECT b from Book b where b.isbn = :isbn", Book.class);
            query.setParameter("isbn", isbn);
            Book book =  query.getSingleResult();
            return book;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Book> findAll() {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<Book> query = em.createNamedQuery("book_find_all", Book.class);
            List<Book> books = query.getResultList();
            return books;
        } finally {
            em.close();
        }
    }

    @Override
    public Book getById(Long id) {
        EntityManager em = getEntityManager();
        Book book = em.find(Book.class, id);
        em.close();
        return book;
    }

    @Override
    public Book findBookByTitle(String title) {
        EntityManager em = this.getEntityManager();
        TypedQuery<Book> query = em.createNamedQuery("find_by_title", Book.class);
        query.setParameter("title", title);
        Book book = query.getSingleResult();
        em.close();
        return book;
    }

    @Override
    public Book findBookByTitleNative(String title) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery("SELECT a.* FROM book a WHERE a.title = ?", Book.class);
            query.setParameter(1, title);

            return (Book) query.getSingleResult();
        } finally {
            em.close();
        }
    }
    @Override
    public Book findBookByCriteria(String title) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
            Root<Book> root = criteriaQuery.from(Book.class);
            ParameterExpression<String> titleParam = criteriaBuilder.parameter(String.class);
            Predicate titlePred = criteriaBuilder.equal(root.get("title"), titleParam);

            criteriaQuery.select(root).where(criteriaBuilder.and(titlePred));

            TypedQuery<Book> query = em.createQuery(criteriaQuery);

            query.setParameter(titleParam, title);
            Book book = query.getSingleResult();
            return book;
        } finally {
            em.close();
        }
    }

    @Override
    public Book saveNewBook(Book book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(book);
        em.flush();
        em.getTransaction().commit();
        em.close();
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(book);
        em.flush();
        em.getTransaction().commit();
        em.clear();
        em.close();
        return book;
    }

    @Override
    public void deleteBookById(Long id) {
        EntityManager em = getEntityManager();

        Book book = em.find(Book.class, id);
        em.getTransaction().begin();
        em.remove(book);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }



    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
