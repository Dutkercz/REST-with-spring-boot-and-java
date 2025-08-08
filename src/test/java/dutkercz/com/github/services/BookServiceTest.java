package dutkercz.com.github.services;

import dutkercz.com.github.data.dto.BookRequestDTO;
import dutkercz.com.github.data.dto.BookResponseDTO;
import dutkercz.com.github.models.Book;
import dutkercz.com.github.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    MockBook inputBook;
    BookRepository bookRepository;
    BookService bookService;

    @BeforeEach
    void setUp() {
        inputBook = new MockBook();
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
    }

    @Test
    void create() {
        Book book = inputBook.mockBook(1);
        BookRequestDTO requestDTO = inputBook.mockRequest(1);
        BookResponseDTO responseDTO = inputBook.mockResponse(1);
        book.setId(1L);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        var result = bookService.create(requestDTO);

        assertNotNull(result, "E entidade não pode estar nula");
        assertEquals(1L, result.getId());
        assertEquals(book.getAuthor(), result.getAuthor());
        assertEquals(book.getTitle(), result.getTitle());
        assertEquals(book.getReleaseDate(), result.getReleaseDate());
        assertEquals(0, book.getPrice().compareTo(result.getPrice()));

        assertNotNull(responseDTO.getLinks());

        boolean hasValidsLinks = assertTrue(responseDTO.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("self")
                                && link.getHref().endsWith("/books/1")
                                &&  link.getType().equals("GET")));
        assertTrue(responseDTO.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("findALl")
                                && link.getHref().endsWith("/books")
                                &&  link.getType().equals("GET")));
        assertTrue(responseDTO.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("create")
                                && link.getHref().endsWith("/books")
                                &&  link.getType().equals("POST")));
        assertTrue(responseDTO.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("update")
                                && link.getHref().endsWith("/books")
                                &&  link.getType().equals("PUT")));
        assertTrue(responseDTO.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("delete")
                                && link.getHref().endsWith("/books/1")
                                &&  link.getType().equals("DELETE")));
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void update() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void deleteById() {
    }

    private static class MockBook {

        public Book mockEntity(){
            return mockBook(0);
        }


        public Book mockBook(Integer number) {
            Book book = new Book();
            book.setAuthor("Autor"+number);
            book.setTitle("Titulo"+number);
            book.setReleaseDate(LocalDate.of(2000, 1, number));
            book.setPrice(BigDecimal.valueOf(number));
            return book;
        }
        public List<Book> mockBookList(Integer number){
            List<Book> bookList = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                bookList.add(mockBook(i));
            }
            return bookList;
        }

        public BookRequestDTO mockRequest(Integer number) {
            BookRequestDTO book = new BookRequestDTO();
            book.setAuthor("Autor"+number);
            book.setTitle("Titulo"+number);
            book.setReleaseDate(LocalDate.of(2000, 1, number));
            book.setPrice(BigDecimal.valueOf(number));
            return book;
        }

        public BookResponseDTO mockResponse(Integer number) {
            BookResponseDTO book = new BookResponseDTO();
            book.setAuthor("Autor"+number);
            book.setTitle("Titulo"+number);
            book.setReleaseDate(LocalDate.of(2000, 1, number));
            book.setPrice(BigDecimal.valueOf(number));
            return book;
        }
    }
}