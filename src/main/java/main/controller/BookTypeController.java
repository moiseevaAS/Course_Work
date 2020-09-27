package main.controller;
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
@RequestMapping(path = "/api/bookTypes", produces = "application/json")
public class BookTypeController {

    private BookTypeRepository bookTypeRepository;

    @Autowired
    public BookTypeController(
            BookTypeRepository bookTypeRepository
    ) {
        this.bookTypeRepository = bookTypeRepository;
    }

    @GetMapping
    public Iterable<BookType> getAllTypes() {
        return bookTypeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookType> getTypeById(@PathVariable long id) {
        return bookTypeRepository.findById(id).map(types -> new ResponseEntity<>(types, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public BookType createType(@Valid @RequestBody BookType type) {
        return bookTypeRepository.save(type);
    }

    @PutMapping("/{id}")
    public void updateType(
            @PathVariable long id,
            @Valid @RequestBody BookType type
    ) {
        if (id != type.getId()) {
            throw new IllegalStateException("Given id doesn't match the id in the path");
        }
        bookTypeRepository.save(type);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteType(@PathVariable long id) {
        try {
            bookTypeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignored) {
            throw new EntityNotFoundException(id);
        }    }

}

