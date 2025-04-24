package org.example.javangerstestingrecap.controller;

import org.example.javangerstestingrecap.dto.BookDto;
import org.example.javangerstestingrecap.exceptions.BookNotFoundException;
import org.example.javangerstestingrecap.model.Book;
import org.example.javangerstestingrecap.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn) throws BookNotFoundException {
        return bookService.getBookByIsbn(isbn);
    }

    @PostMapping
    public Book addBook(@RequestBody BookDto newBook) {
        return bookService.createBook(newBook);
    }

    @PutMapping
    public Book updateBook(@RequestBody Book updatedBook) throws BookNotFoundException {
        return bookService.updateBook(updatedBook);
    }

    @DeleteMapping("/{isbn}")
    public boolean deleteBook(@PathVariable String isbn) throws BookNotFoundException {
        return bookService.deleteBook(isbn);
    }
}
