package org.example.javangerstestingrecap.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Books")
public record Book(@Id String isbn, String title, String author) {
}
