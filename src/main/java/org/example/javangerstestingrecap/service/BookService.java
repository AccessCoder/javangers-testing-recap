package org.example.javangerstestingrecap.service;

import org.example.javangerstestingrecap.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final IdService idService;

    public BookService(BookRepository bookRepository, IdService idService) {
        this.bookRepository = bookRepository;
        this.idService = idService;
    }


}
