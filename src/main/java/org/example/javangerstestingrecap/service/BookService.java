package org.example.javangerstestingrecap.service;

import org.example.javangerstestingrecap.dto.BookDto;
import org.example.javangerstestingrecap.exceptions.BookNotFoundException;
import org.example.javangerstestingrecap.model.Book;
import org.example.javangerstestingrecap.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final IdService idService;

    public BookService(BookRepository bookRepository, IdService idService) {
        this.bookRepository = bookRepository;
        this.idService = idService;
    }


    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    public Book createBook(BookDto newBook) {
        Book tempBook = new Book(
                idService.generateId(),
                newBook.title(),
                newBook.author());

        bookRepository.save(tempBook);
        return tempBook;
    }

    public Book getBookByIsbn(String isbn) throws BookNotFoundException {
        return bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book with Isbn: " + isbn + "not found!"));
    }

    public Book updateBook(Book updatedBook) throws BookNotFoundException {
        if (bookRepository.existsById(updatedBook.isbn())){
            bookRepository.save(updatedBook);
            return updatedBook;
        }else {
            throw new BookNotFoundException("No Book with ISBN: " + updatedBook.isbn() + " found!");
        }
    }


    public boolean deleteBook(String isbn) throws BookNotFoundException {
        if (bookRepository.existsById(isbn)){
            bookRepository.deleteById(isbn);
            return true;
        }else {
            throw new BookNotFoundException("No Book with ISBN: " + isbn + " found!");
        }

    }
}
