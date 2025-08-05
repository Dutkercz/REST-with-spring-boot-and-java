package dutkercz.com.github.controllers;

import dutkercz.com.github.data.dto.PersonDTO;
import dutkercz.com.github.services.PersonService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/person")
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
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO person, UriComponentsBuilder builder){
        PersonDTO personReturn = personService.create(person);
        var uri = builder.path("/{id}").buildAndExpand(personReturn.getId()).toUri();
        return ResponseEntity.created(uri).body(personReturn);
    }

    @GetMapping(value = "/{id}",
            produces = {APPLICATION_JSON_VALUE,
                    APPLICATION_XML_VALUE,
                    APPLICATION_YAML_VALUE})
    public ResponseEntity<PersonDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(personService.findById(id));
    }

    @GetMapping(
            produces = {APPLICATION_JSON_VALUE,
                    APPLICATION_XML_VALUE,
                    APPLICATION_YAML_VALUE})
    public ResponseEntity<List<PersonDTO>> findAll(){
        return ResponseEntity.ok(personService.findAll());
    }

    @PutMapping(consumes = {APPLICATION_JSON_VALUE,
            APPLICATION_XML_VALUE,
            APPLICATION_YAML_VALUE},
            produces = {APPLICATION_JSON_VALUE,
                    APPLICATION_XML_VALUE,
                    APPLICATION_YAML_VALUE})
    public ResponseEntity<PersonDTO> update(@RequestBody PersonDTO personDTO){
        PersonDTO personReturn = personService.update(personDTO);
        return ResponseEntity.ok().body(personReturn);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
