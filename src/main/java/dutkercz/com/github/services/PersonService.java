package dutkercz.com.github.services;

import dutkercz.com.github.controllers.PersonController;
import dutkercz.com.github.data.dto.PersonDTO;
import dutkercz.com.github.mapper.EntityMapper;
import dutkercz.com.github.models.Person;
import dutkercz.com.github.repositories.PersonRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static dutkercz.com.github.mapper.EntityMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public PersonDTO create(PersonDTO personDTO){
        if (personDTO == null) throw new IllegalArgumentException("Verifique os campos, e tente novamente");
        Person person = personRepository.save(parseObject(personDTO, Person.class));
        var dto = parseObject(person, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public Page<PersonDTO> findAll(Pageable pageable) {
        logger.info("Finding all People!");

        var people = personRepository.findAll(pageable);

        return people.map(x -> {
            var dto = parseObject(x, PersonDTO.class);
            addHateoasLinks(dto);
            return dto;
        });
    }

    @Transactional
    public PersonDTO update(PersonDTO personToUpdate){
        logger.info("UPDATE Person!");

        if (personToUpdate == null) throw new IllegalArgumentException("Verifique os campos, e tente novamente");

        Person person = personRepository.findById(personToUpdate.getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        var dto = parseObject(person.update(personToUpdate), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Transactional
    public void delete(Long id){
        logger.info("DELETE Person!");

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        personRepository.delete(person);
    }

    @Transactional
    public PersonDTO disablePerson(Long id){
        logger.info("DISABLE Person!");

        personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        personRepository.disablePerson(id);

        var person = personRepository.findById(id).get();
        return parseObject(person, PersonDTO.class);
    }

    public PersonDTO findById(Long id) {
        logger.info("FIND Person by ID!");

        var dto =  parseObject(personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encotrado")), PersonDTO.class);
            addHateoasLinks(dto);
        return dto;
    }

    private static void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(PersonController.class).disablePerson(dto.getId())).withRel("disable").withType("PATCH"));
        dto.add(linkTo(methodOn(PersonController.class).findAll(1, 12, "asc")).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto, null)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));

    }
}
