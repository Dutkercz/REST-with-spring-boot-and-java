package dutkercz.com.github.services;

import dutkercz.com.github.controllers.PersonController;
import dutkercz.com.github.data.dto.PersonDTO;
import dutkercz.com.github.mapper.EntityMapper;
import dutkercz.com.github.models.Person;
import dutkercz.com.github.repositories.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import static dutkercz.com.github.mapper.EntityMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public PersonDTO create(PersonDTO personDTO){
        Person person = personRepository.save(parseObject(personDTO, Person.class));
        return parseObject(person, PersonDTO.class);
    }

    public List<PersonDTO> findAll(){
        return EntityMapper.parseListObjects(personRepository.findAll(), PersonDTO.class);
    }

    @Transactional
    public PersonDTO update(PersonDTO personToUpdate){
        Person person = personRepository.findById(personToUpdate.getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encotrado"));
        person.update(personToUpdate);
        return parseObject(person, PersonDTO.class);
    }

    @Transactional
    public void delete(Long id){
        personRepository.deleteById(id);
    }

    public PersonDTO findById(Long id) {
        var dto =  parseObject(personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encotrado")), PersonDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel().withType("GET"));

        return dto;
    }
}
