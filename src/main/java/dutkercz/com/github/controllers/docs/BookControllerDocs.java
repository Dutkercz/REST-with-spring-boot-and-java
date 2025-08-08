package dutkercz.com.github.controllers.docs;

import dutkercz.com.github.data.dto.BookRequestDTO;
import dutkercz.com.github.data.dto.BookResponseDTO;
import dutkercz.com.github.data.dto.BookUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

public interface BookControllerDocs {

    @Operation(summary = "Create new BookEntity",
                description = "Create a new entity, and then return a custom DTO",
                tags = {"Books"},
                responses ={
                        @ApiResponse(description = "Created", responseCode = "201",
                                content = @Content(schema = @Schema(implementation = BookResponseDTO.class))),
                        @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                        @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                        @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
                }
    )
    ResponseEntity<BookResponseDTO> createBook(@RequestBody BookRequestDTO bookRequestDTO, UriComponentsBuilder builder);


    @Operation(summary = "Update BookEntity",
            description = "Update a entity, and then return a custom DTO",
            tags = {"Books"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = BookResponseDTO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<BookResponseDTO> updateBook(@RequestBody BookUpdateDTO bookUpdateDTO);



    @Operation(summary = "Find a Book by ID",
            description = "Find a entity by, and then return a custom DTO",
            tags = {"Books"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = BookResponseDTO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<BookResponseDTO> findById(@PathVariable Long id);



    @Operation(summary = "Find all Books",
            description = "Find all entities , and then return a custom List<DTO>",
            tags = {"Books"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = BookResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<Page<BookResponseDTO>> findAll(Pageable pageable);



    @Operation(summary = "Delete a book by ID",
            description = "Fist find it by ID, a then delete using a ID",
            tags = {"Books"},
            responses = {@ApiResponse(description = "No Content", responseCode = "204"),
                        @ApiResponse(description = "Unauthorized", responseCode = "401"),
                        @ApiResponse(description = "Not Found", responseCode = "404")
            }
    )
    ResponseEntity<?> deleteBookById(@PathVariable Long id);
}
