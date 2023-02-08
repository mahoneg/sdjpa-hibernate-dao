package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;

import java.util.List;

/**
 * Created by jt on 8/22/21.
 */
public interface AuthorDao {
    List<Author> listAuthorByLastNameLike(String lastName);
    List<Author> findAll();

    Author getById(Long id);

    Author findAuthorByName(String firstName, String lastName);

    Author findAuthorByNameNative(String firstName, String lastName);

    Author findAuthorByCriteria(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);
}
