package org.example.javangerstestingrecap.controller;

import org.example.javangerstestingrecap.model.Book;
import org.example.javangerstestingrecap.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void getAllBooks_shouldReturnEmptyList_whenCalledInitially() throws Exception {
        mvc.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getBookByIsbn_shouldReturnBook1_whenCalledWithId1() throws Exception {
        Book book = new Book("1", "Test", "Tester");
        bookRepository.save(book);

        mvc.perform(get("/api/book/"+book.isbn()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                            {
                                              "isbn": "1",
                                              "title": "Test",
                                              "author": "Tester"
                                            }
                                          """));
    }

    @Test
    void createBook_shouldReturnBookWithId_whenCalledWithDto() throws Exception {
        mvc.perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "Test",
                                  "author": "Tester"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                            {
                                              "title": "Test",
                                              "author": "Tester"
                                            }
                                          """))
                .andExpect(jsonPath("$.isbn").isNotEmpty());
    }

    @Test
    void updateBook_shouldReturnUpdatedBook_whenCalled() throws Exception {
        Book book = new Book("1", "Test", "Tester");
        bookRepository.save(book);

        mvc.perform(put("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "isbn": "1",
                                  "title": "Testen für jung und alt!",
                                  "author": "Tester"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                            {
                                              "isbn": "1",
                                              "title": "Testen für jung und alt!",
                                              "author": "Tester"
                                            }
                                          """));

    }

    @Test
    void deleteBook_shouldReturnTrue_whenCalledValidId() throws Exception {
        Book book = new Book("1", "Test", "Tester");
        bookRepository.save(book);
        mvc.perform(delete("/api/book/"+book.isbn()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }


}