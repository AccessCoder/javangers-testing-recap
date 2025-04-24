package org.example.javangerstestingrecap.repository;

import org.example.javangerstestingrecap.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BookRepository extends MongoRepository<Book, String> {
}
