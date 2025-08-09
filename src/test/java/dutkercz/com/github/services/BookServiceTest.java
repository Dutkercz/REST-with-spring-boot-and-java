package dutkercz.com.github.services;

import dutkercz.com.github.data.dto.BookRequestDTO;
import dutkercz.com.github.data.dto.BookResponseDTO;
import dutkercz.com.github.data.dto.BookUpdateDTO;
import dutkercz.com.github.models.Book;
import dutkercz.com.github.models.Person;
import dutkercz.com.github.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Links;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        book.setId(1L);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        var result = bookService.create(requestDTO);

        assertNotNull(result, "E entidade não pode estar nula");
        assertBookEquals(book, result);
        assertValidLinks(result.getId(), result.getLinks());


        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void update() {
        Book book = inputBook.mockBook(1);
        book.setId(1L);
        book.setPrice(BigDecimal.valueOf(49.99));
        BookUpdateDTO updateDTO = inputBook.mockUpdate(1, BigDecimal.valueOf(99.90));

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        System.out.println("ID" + updateDTO.id());
        var result = bookService.update(updateDTO);

        assertNotNull(result, "E entidade não pode estar nula");

        assertEquals(1L, result.getId(), "O ID deve permanecer o mesmo");
        assertEquals(0, updateDTO.price().compareTo(result.getPrice()),
                "O preço deve ser atualizado para o valor do DTO");
        assertEquals(book.getAuthor(), result.getAuthor(), "O autor não deve ser alterado");
        assertEquals(book.getTitle(), result.getTitle(), "O título não deve ser alterado");
        assertEquals(book.getReleaseDate(), result.getReleaseDate(), "A data de lançamento não deve ser alterada");

        assertValidLinks(result.getId(), result.getLinks());

        verify(bookRepository, times(1)).findById(book.getId());
    }

    @Test
    void findById() {
        Book book = inputBook.mockBook(1);
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        var result = bookService.findById(1L);

        assertNotNull(result, "E entidade não pode estar nula");
        assertBookEquals(book, result);
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void findAll() {
        List<Book> books = inputBook.mockBookList();
        Pageable pageable = PageRequest.of(0, books.size());
        Page<Book> bookPaged = new PageImpl<>(books, pageable, books.size());

        when(bookRepository.findAll(pageable)).thenReturn(bookPaged);
        var results = bookService.findAll(pageable);

        assertNotNull(results, "O resultado da lista não pode estar nulo");
        assertEquals(bookPaged.getContent().size(), results.getContent().size());

        for (int i = 0; i < results.getContent().size(); i++) {
            var expected = bookPaged.getContent().get(i);
            var result = results.getContent().get(i);
            assertBookEquals(expected, result);
        }
        verify(bookRepository, times(1)).findAll(pageable);

    }

    @Test
    void deleteById() {
        var book = inputBook.mockBook(1);
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        bookService.deleteById(1L);
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).delete(any(Book.class));

    }

    private static class MockBook {

        public Book mockEntity(){
            return mockBook(0);
        }


        public Book mockBook(Integer number) {
            Book book = new Book();
            book.setAuthor("Autor"+number);
            book.setTitle("Titulo"+number);
            book.setReleaseDate(LocalDate.of(2000, 1, 1));
            book.setPrice(BigDecimal.valueOf(number));
            return book;
        }
        public List<Book> mockBookList(){
            List<Book> bookList = new ArrayList<>();
            long id = 1L;
            for (int i = 0; i < 30; i++) {
                bookList.add(mockBook(i));
                bookList.get(i).setId(id);
                id ++;
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

        public BookUpdateDTO mockUpdate(Integer number, BigDecimal price){
            return new BookUpdateDTO(Long.valueOf(number), price);
        }
    }

    private static void assertValidLinks(Long id, Links links) {
        assertNotNull(links);
        String path = "/api/books";

        assertTrue(links.stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith(path + "/" + id)
                        && link.getType().equals("GET")));

        assertTrue(links.stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith(path)
                        && link.getType().equals("GET")));

        assertTrue(links.stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith(path)
                        && link.getType().equals("POST")));

        assertTrue(links.stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith(path)
                        && link.getType().equals("PUT")));

        assertTrue(links.stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith(path + "/" + id)
                        && link.getType().equals("DELETE")));
    }

    private static void assertBookEquals(Book expected, BookResponseDTO result){
        assertEquals(expected.getId(), result.getId(), "O ID deve ser o mesmo");
        assertEquals(expected.getAuthor(), result.getAuthor(), "O Autor deve ser o mesmo");
        assertEquals(expected.getTitle(), result.getTitle(), "O Titulo deve ser o mesmo");
        assertEquals(expected.getReleaseDate(), result.getReleaseDate(), "A data deve ser a mesma");
        assertEquals(0, expected.getPrice().compareTo(result.getPrice()), "O preços devem ser iguais");
        assertValidLinks(result.getId(), result.getLinks());
    }
}