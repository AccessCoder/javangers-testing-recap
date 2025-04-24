package org.example.javangerstestingrecap.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
}
