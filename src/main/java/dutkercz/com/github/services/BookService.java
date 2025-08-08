package dutkercz.com.github.services;

import dutkercz.com.github.controllers.BookController;
import dutkercz.com.github.data.dto.BookRequestDTO;
import dutkercz.com.github.data.dto.BookResponseDTO;
import dutkercz.com.github.data.dto.BookUpdateDTO;
import dutkercz.com.github.models.Book;
import dutkercz.com.github.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional
    public BookResponseDTO create(BookRequestDTO requestDTO) {
        Book book = bookRepository.save(parseObject(requestDTO, Book.class));
        var dto = parseObject(book, BookResponseDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Transactional
    public BookResponseDTO update(BookUpdateDTO updateDTO) {
        Book book = bookRepository.findById(updateDTO.id())
                .orElseThrow(() -> new EntityNotFoundException("Book não encontrado"));
        book.setPrice(updateDTO.price());
        var dto = parseObject(book, BookResponseDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public BookResponseDTO findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book não encontrado"));
        var dto = parseObject(book, BookResponseDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public Page<BookResponseDTO> findAll(Pageable pageable) {
        Page<BookResponseDTO> responseDTOS = bookRepository.findAll(pageable)
                .map(x -> parseObject(x, BookResponseDTO.class));
        responseDTOS.forEach(BookService::addHateoasLinks);
        return responseDTOS;
    }

    @Transactional
    public void deleteById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book não encontrado"));
        bookRepository.deleteById(book.getId());
    }

    private static void addHateoasLinks(BookResponseDTO dto){
        dto.add(linkTo(methodOn(BookController.class).createBook(new BookRequestDTO(null, null, null, null), null)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(BookController.class).updateBook(new BookUpdateDTO(dto.getId(),null))).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(BookController.class).deleteBookById(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).findAll(null)).withRel("findAll").withType("GET"));
    }
}
