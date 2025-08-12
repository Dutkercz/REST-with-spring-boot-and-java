package dutkercz.com.github.unit.tests.mock;


import dutkercz.com.github.data.dto.BookRequestDTO;
import dutkercz.com.github.data.dto.BookResponseDTO;
import dutkercz.com.github.data.dto.BookUpdateDTO;
import dutkercz.com.github.models.Book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MockBook {

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
