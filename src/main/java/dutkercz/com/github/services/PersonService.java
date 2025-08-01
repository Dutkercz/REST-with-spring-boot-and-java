package dutkercz.com.github.services;

import dutkercz.com.github.models.Person;
import dutkercz.com.github.repositories.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public Person create(Person person){
        return personRepository.save(new Person(null, person.getFirstName(), person.getLastName(), person.getAddress(), person.getPersonGenderEnum()));
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    @Transactional
    public Person update(Person personToUpdate){
        Person person = personRepository.findById(personToUpdate.getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encotrado"));
        person.update(personToUpdate);
        return person;
    }

    @Transactional
    public void delete(Long id){
        personRepository.deleteById(id);
    }

    public Person findById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encotrado"));
    }
}
