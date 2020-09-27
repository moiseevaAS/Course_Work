package main.repository;

import main.entity.Book;
import main.entity.BookType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    Iterable<Book> findByType(BookType type);
}
