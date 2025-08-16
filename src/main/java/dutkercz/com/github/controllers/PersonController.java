package dutkercz.com.github.controllers;

import dutkercz.com.github.controllers.docs.PersonControllerDocs;
import dutkercz.com.github.data.dto.PersonDTO;
import dutkercz.com.github.services.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/api/person")
@Tag(name ="Person", description = ("Endpoints para o gerenciamento de Person"))
public class PersonController implements PersonControllerDocs {

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
    @Override
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO person, UriComponentsBuilder builder){
        PersonDTO personReturn = personService.create(person);
        var uri = builder.path("/{id}").buildAndExpand(personReturn.getId()).toUri();
        return ResponseEntity.created(uri).body(personReturn);
    }

    @GetMapping(value = "/{id}",
            produces = {APPLICATION_JSON_VALUE,
                    APPLICATION_XML_VALUE,
                    APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<PersonDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(personService.findById(id));
    }

    @GetMapping(produces = {APPLICATION_JSON_VALUE,
                    APPLICATION_XML_VALUE,
                    APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<List<PersonDTO>> findAll(){
        return ResponseEntity.ok(personService.findAll());
    }

    @PutMapping(consumes = {APPLICATION_JSON_VALUE,
            APPLICATION_XML_VALUE,
            APPLICATION_YAML_VALUE},
            produces = {APPLICATION_JSON_VALUE,
                    APPLICATION_XML_VALUE,
                    APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<PersonDTO> update(@RequestBody PersonDTO personDTO){
        PersonDTO personReturn = personService.update(personDTO);
        return ResponseEntity.ok().body(personReturn);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable Long id){
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/{id}", produces = {APPLICATION_JSON_VALUE,
            APPLICATION_XML_VALUE,
            APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<?> disablePerson(@PathVariable Long id){
        return ResponseEntity.ok(personService.disablePerson(id));
    }

}
