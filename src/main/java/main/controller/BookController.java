package main.controller;
import main.entity.Book;
import main.entity.BookType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import main.repository.*;
import main.exceptoin.*;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "/api/books", produces = "application/json")
public class BookController {

    private BookRepository bookRepository;
    private BookTypeRepository bookTypeRepository;

    @Autowired
    public BookController(
            BookRepository bookRepository,
            BookTypeRepository bookTypeRepository
    ) {
        this.bookRepository = bookRepository;
        this.bookTypeRepository = bookTypeRepository;
    }

    @GetMapping
    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable long id) {
        return bookRepository.findById(id).map(books -> new ResponseEntity<>(books, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/bookTypes/{id}")
    public Iterable<Book> getTypeId(@PathVariable long id) {
        BookType type = bookTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        return bookRepository.findByType(type);
    }


    @PostMapping
    public Book createBook(@Valid @RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping("/{id}")
    public void updateBook(
            @PathVariable long id,
            @Valid @RequestBody Book book
    ) {
        if (id != book.getId()) {
            throw new IllegalStateException("Given id doesn't match the id in the path");
        }
        bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable long id) {
        try {
            bookRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignored) {
            throw new EntityNotFoundException(id);
        }
    }

}

