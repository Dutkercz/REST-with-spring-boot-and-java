package dutkercz.com.github.controllers;

import dutkercz.com.github.data.dto.PersonDTO;
import dutkercz.com.github.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/api/person")
@Tag(name ="Person", description = ("Endpoints para o gerenciamento de Person"))
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(
            consumes = {APPLICATION_JSON_VALUE,
                    APPLICATION_XML_VALUE,
                    APPLICATION_YAML_VALUE},
            produces = {APPLICATION_JSON_VALUE,
                    APPLICATION_XML_VALUE,
                    APPLICATION_YAML_VALUE})
    @Operation(
            summary = "Create new Person",
            description = "Cria uma nova instancia de Person",
            tags = {"People"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PersonDTO.class))                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO person, UriComponentsBuilder builder){
        PersonDTO personReturn = personService.create(person);
        var uri = builder.path("/{id}").buildAndExpand(personReturn.getId()).toUri();
        return ResponseEntity.created(uri).body(personReturn);
    }

    @GetMapping(value = "/{id}",
            produces = {APPLICATION_JSON_VALUE,
                    APPLICATION_XML_VALUE,
                    APPLICATION_YAML_VALUE})
    @Operation(
            summary = "Find a Person",
            description = "Retorna uma instancia de Person pelo seu ID",
            tags = {"People"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PersonDTO.class))                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<PersonDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(personService.findById(id));
    }

    @GetMapping(
            produces = {APPLICATION_JSON_VALUE,
                    APPLICATION_XML_VALUE,
                    APPLICATION_YAML_VALUE})
    @Operation(
            summary = "Find All People",
            description = "Retorna um array contendo todas as instancias de Person.class",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success",
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(arraySchema = @Schema(implementation = PersonDTO.class))
                            )}),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<List<PersonDTO>> findAll(){
        return ResponseEntity.ok(personService.findAll());
    }

    @PutMapping(consumes = {APPLICATION_JSON_VALUE,
            APPLICATION_XML_VALUE,
            APPLICATION_YAML_VALUE},
            produces = {APPLICATION_JSON_VALUE,
                    APPLICATION_XML_VALUE,
                    APPLICATION_YAML_VALUE})
    @Operation(
            summary = "Update Person",
            description = "Realiza updates nos parametros de Person",
            tags = {"People"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PersonDTO.class))                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<PersonDTO> update(@RequestBody PersonDTO personDTO){
        PersonDTO personReturn = personService.update(personDTO);
        return ResponseEntity.ok().body(personReturn);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Person",
            description = "Deleta uma instancia de Person pelo seu ID",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<?> delete(@PathVariable Long id){
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
