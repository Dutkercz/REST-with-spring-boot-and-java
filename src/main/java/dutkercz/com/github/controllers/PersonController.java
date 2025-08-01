package dutkercz.com.github.controllers;

import dutkercz.com.github.models.Person;
import dutkercz.com.github.services.PersonService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<Person> create(@RequestBody Person person, UriComponentsBuilder builder){
        Person personReturn = personService.create(person);
        var uri = builder.path("/{id}").buildAndExpand(personReturn.getId()).toUri();
        return ResponseEntity.created(uri).body(personReturn);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable Long id){
        return ResponseEntity.ok(personService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Person>> findAll(){
        return ResponseEntity.ok(personService.findAll());
    }

    @PutMapping
    public ResponseEntity<Person> update(@RequestBody Person person){
        Person personReturn = personService.update(person);
        return ResponseEntity.ok().body(personReturn);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
