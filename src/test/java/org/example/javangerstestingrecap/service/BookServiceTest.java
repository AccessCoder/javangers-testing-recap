package org.example.javangerstestingrecap.service;

import org.example.javangerstestingrecap.dto.BookDto;
import org.example.javangerstestingrecap.exceptions.BookNotFoundException;
import org.example.javangerstestingrecap.model.Book;
import org.example.javangerstestingrecap.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    BookRepository mockRepo = Mockito.mock(BookRepository.class);
    IdService mockIdService = Mockito.mock(IdService.class);

    @Test
    void getAllBooks_shouldReturnEmptyList_whenCalledInitially(){
        //GIVEN
        BookService bookService = new BookService(mockRepo, mockIdService);
        List<Book> expected = Collections.emptyList();
        //WHEN
        List<Book> actual = bookService.getAllBooks();
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void getAllBooks_shouldReturnListOfBooks_whenCalled(){
        //GIVEN
        BookService bookService = new BookService(mockRepo, mockIdService);
        Book book1 = new Book("1", "Test", "Tester");
        List<Book> expected = List.of(book1);

        Mockito.when(mockRepo.findAll()).thenReturn(expected);
        //WHEN
        List<Book> actual = bookService.getAllBooks();
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void createBook_shouldReturnBookWithId_whenCalledWithDto(){
        //GIVEN
        BookService bookService = new BookService(mockRepo, mockIdService);
        BookDto bookDto = new BookDto("Test", "Tester");
        Book expected = new Book("1", "Test", "Tester");

        Mockito.when(mockIdService.generateId()).thenReturn("1");
        //WHEN
        Book actual = bookService.createBook(bookDto);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void createBook_shouldReturnBookWithIdAndSaveInDB_whenCalledWithDto(){
        //GIVEN
        BookService bookService = new BookService(mockRepo, mockIdService);
        BookDto bookDto = new BookDto("Test", "Tester");
        Book expected = new Book("1", "Test", "Tester");

        Mockito.when(mockIdService.generateId()).thenReturn("1");
        //WHEN
        Book actual = bookService.createBook(bookDto);
        //THEN
        assertEquals(expected, actual);
        Mockito.verify(mockRepo).save(expected);
    }

    @Test
    void getBookByIsbn_shouldReturnBook1_whenCalledWithId1() throws BookNotFoundException {
        //GIVEN
        BookService bookService = new BookService(mockRepo, mockIdService);
        Book expected = new Book("1", "Test", "Tester");


        Mockito.when(mockRepo.findById("1")).thenReturn(Optional.of(expected));
        //WHEN
        Book actual = bookService.getBookByIsbn(expected.isbn());
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void getBookByIsbn_shouldThrowException_whenCalledWithInvalidId(){
        //GIVEN
        BookService bookService = new BookService(mockRepo, mockIdService);

        Mockito.when(mockRepo.findById("1")).thenReturn(Optional.empty());
        //WHEN
        try {
            bookService.getBookByIsbn("1");
            //THEN
            fail();
        }catch (BookNotFoundException e){
            assertTrue(true);
        }

    }

    @Test
    void updateBook_shouldReturnUpdatedBook_whenCalled() throws BookNotFoundException {
        //GIVEN
        BookService bookService = new BookService(mockRepo, mockIdService);
        Book expected = new Book("1", "Test2", "Tester");

        Mockito.when(mockRepo.existsById(expected.isbn())).thenReturn(true);
        //WHEN
        Book actual = bookService.updateBook(expected);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void updateBook_shouldUpdateBookInDBAndReturnUpdatedBook_whenCalled() throws BookNotFoundException {
        //GIVEN
        BookService bookService = new BookService(mockRepo, mockIdService);
        Book expected = new Book("1", "Test2", "Tester");

        Mockito.when(mockRepo.existsById(expected.isbn())).thenReturn(true);
        //WHEN
        Book actual = bookService.updateBook(expected);
        //THEN
        assertEquals(expected, actual);
        Mockito.verify(mockRepo).save(expected);
    }

    @Test
    void updateBook_shouldThrowException_whenIdDoesntExist() throws BookNotFoundException {
        //GIVEN
        BookService bookService = new BookService(mockRepo, mockIdService);
        Book expected = new Book("1", "Test2", "Tester");

        Mockito.when(mockRepo.existsById("1")).thenReturn(false);
        //WHEN
        try{
            bookService.updateBook(expected);
            fail();
        }catch (BookNotFoundException e){
            assertTrue(true);
        }

    }

    @Test
    void deleteBook_shouldReturnTrue_whenCalledWithValidId() throws BookNotFoundException {
        //GIVEN
        BookService bookService = new BookService(mockRepo, mockIdService);
        Mockito.when(mockRepo.existsById("1")).thenReturn(true);
        //WHEN
        boolean actual = bookService.deleteBook("1");
        //THEN
        assertTrue(actual);

    }

    @Test
    void deleteBook_shouldDeleteDataFromDBANdReturnTrue_whenCalledWithValidId() throws BookNotFoundException {
        //GIVEN
        BookService bookService = new BookService(mockRepo, mockIdService);

        Mockito.when(mockRepo.existsById("1")).thenReturn(true);
        //WHEN
        boolean actual = bookService.deleteBook("1");
        //THEN
        assertTrue(actual);
        Mockito.verify(mockRepo).deleteById("1");

    }

    @Test
    void deleteBook_shouldThrowException_whenCalledWithInvalidId() throws BookNotFoundException {
        //GIVEN
        BookService bookService = new BookService(mockRepo, mockIdService);

        Mockito.when(mockRepo.existsById("1")).thenReturn(false);
        //WHEN
        try {
            bookService.deleteBook("1");
            fail();
        }catch (BookNotFoundException e){
            assertTrue(true);
        }

    }






}