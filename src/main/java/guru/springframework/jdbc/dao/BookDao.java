package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;

import java.util.List;

/**
 * Created by jt on 8/22/21.
 */
public interface BookDao {
    Book findByISBN(String isbn);
    List<Book> findAll();

    Book getById(Long id);

    Book findBookByTitle(String title);

    Book findBookByCriteria(String title);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);

    Book findBookByTitleNative(String title);
}
