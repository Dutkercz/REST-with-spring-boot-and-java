package dutkercz.com.github.services;

import dutkercz.com.github.controllers.BookController;
import dutkercz.com.github.data.dto.BookRequestDTO;
import dutkercz.com.github.data.dto.BookResponseDTO;
import dutkercz.com.github.data.dto.BookUpdateDTO;
import dutkercz.com.github.models.Book;
import dutkercz.com.github.repositories.BookRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import static dutkercz.com.github.mapper.EntityMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponseDTO create(BookRequestDTO requestDTO) {
        Book book = bookRepository.save(parseObject(requestDTO, Book.class));
        var dto = parseObject(book, BookResponseDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    private static void addHateoasLinks(BookResponseDTO dto){
        dto.add(linkTo(methodOn(BookController.class).createBook(new BookRequestDTO(null, null, null, null), null)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(BookController.class).updateBook(new BookUpdateDTO(null))).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(BookController.class).deleteBookById(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).findAll(null)).withRel("findAll").withType("GET"));
    }

}
