package main.controller;

import main.entity.*;
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
@RequestMapping(path = "/api/journal", produces = "application/json")
public class JournalController {

    private JournalRepository journalRepository;
    private BookRepository bookRepository;
    private ClientRepository clientRepository;

    @Autowired
    public JournalController(
            JournalRepository journalRepository,
            BookRepository bookRepository,
            ClientRepository clientRepository
    ) {
        this.journalRepository = journalRepository;
        this.bookRepository = bookRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public Iterable<Journal> getAllJournal() {
        return journalRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Journal> getJournalById(@PathVariable long id) {
        return journalRepository.findById(id).map(journal -> new ResponseEntity<>(journal, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/books/{id}")
    public Iterable<Journal> getBookId(@PathVariable long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        return journalRepository.findByBook(book);
    }

    @GetMapping("/clients/{id}")
    public Iterable<Journal> getClientId(@PathVariable long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        return journalRepository.findByClient(client);
    }

    @PostMapping
    public Journal createJournal(@Valid @RequestBody Journal journal) {
        return journalRepository.save(journal);
    }

    @PutMapping("/{id}")
    public void updateJournal(
            @PathVariable long id,
            @Valid @RequestBody Journal journal
    ) {
        if (id != journal.getId()) {
            throw new IllegalStateException("Given id doesn't match the id in the path");
        }
        journalRepository.save(journal);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJournal(@PathVariable long id) {
        try {
            journalRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignored) {
            throw new EntityNotFoundException(id);
        }
    }

}
