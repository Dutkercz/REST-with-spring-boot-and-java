package dutkercz.com.github.controllers;

import dutkercz.com.github.controllers.docs.BookControllerDocs;
import dutkercz.com.github.data.dto.BookRequestDTO;
import dutkercz.com.github.data.dto.BookResponseDTO;
import dutkercz.com.github.data.dto.BookUpdateDTO;
import dutkercz.com.github.services.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/books")
public class BookController implements BookControllerDocs {

    private final BookService bookService;


    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody @Valid BookRequestDTO requestDTO,
                                                      UriComponentsBuilder builder) {
        BookResponseDTO responseDTO = bookService.create(requestDTO);
        URI uri = builder.path("/{id}").buildAndExpand(responseDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @Override
    @PutMapping
    public ResponseEntity<BookResponseDTO> updateBook(@RequestBody BookUpdateDTO bookUpdateDTO) {
        return null;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> findById(@PathVariable Long id) {
        return null;
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<Page<BookResponseDTO>> findAll(Pageable pageable) {
        return null;
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookById(Long id) {
        return null;
    }
}
