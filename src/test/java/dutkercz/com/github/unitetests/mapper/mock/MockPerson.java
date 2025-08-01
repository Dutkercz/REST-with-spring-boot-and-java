package dutkercz.com.github.unitetests.mapper.mock;

import dutkercz.com.github.data.dto.PersonDTO;
import dutkercz.com.github.models.Person;

import java.util.ArrayList;
import java.util.List;

import static dutkercz.com.github.models.PersonGenderEnum.FEMALE;
import static dutkercz.com.github.models.PersonGenderEnum.MALE;


public class MockPerson {


    public Person mockEntity() {
        return mockEntity(0);
    }
    
    public PersonDTO mockDTO() {
        return mockDTO(0);
    }
    
    public List<Person> mockEntityList() {
        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockEntity(i));
        }
        return persons;
    }

    public List<PersonDTO> mockDTOList() {
        List<PersonDTO> persons = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockDTO(i));
        }
        return persons;
    }
    
    public Person mockEntity(Integer number) {
        Person person = new Person();
        person.setAddress("Address Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setPersonGenderEnum(((number % 2)==0) ? MALE : FEMALE);
        person.setId(number.longValue());
        person.setLastName("Last Name Test" + number);
        return person;
    }

    public PersonDTO mockDTO(Integer number) {
        PersonDTO person = new PersonDTO();
        person.setAddress("Address Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setPersonGenderEnum(((number % 2)==0) ? MALE : FEMALE);
        person.setId(number.longValue());
        person.setLastName("Last Name Test" + number);
        return person;
    }

}