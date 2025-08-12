package dutkercz.com.github.unit.tests.services;

import dutkercz.com.github.data.dto.PersonDTO;
import dutkercz.com.github.models.Person;
import dutkercz.com.github.repositories.PersonRepository;
import dutkercz.com.github.services.PersonService;
import dutkercz.com.github.unit.tests.mock.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static dutkercz.com.github.models.PersonGenderEnum.FEMALE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    MockPerson input;
    PersonRepository repository;
    PersonService service;


    @BeforeEach
    void setUp() {
        input = new MockPerson();
        repository = mock(PersonRepository.class);
        service = new PersonService(repository);
    }

    @Test
    void findById() {

        Person person = input.mockEntity(1);
        person.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        var result = service.findById(1L);

        assertNotNull(result, "O resultado não pode ser nulo.");
        assertNotNull(result.getId(), "O ID da pessoa não pode ser nulo.");
        assertEquals(1L, result.getId(), "O ID retornado deve ser 1.");
        assertNotNull(result.getLinks(), "Os links não podem ser nulos.");

        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Address Test1", result.getAddress());
        assertEquals(FEMALE, result.getGender());

        boolean hasSelfLink = result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("self")
                                && link.getHref().endsWith("/person/1")
                                &&  link.getType().equals("GET"));
        assertTrue(hasSelfLink, "deve contem o link self com GET e href correto");

        hasSelfLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/person")
                        && link.getType().equals("GET"));
        assertTrue(hasSelfLink, "deve contem o link 'findAll' com GET e href correto");

        hasSelfLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/person")
                        && link.getType().equals("POST"));
        assertTrue(hasSelfLink, "deve contem o link 'create' com POST e href correto");

        hasSelfLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/person")
                        && link.getType().equals("PUT"));
        assertTrue(hasSelfLink, "deve contem o link 'update' com PUT e href correto");

        hasSelfLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/person/1")
                        && link.getType().equals("DELETE"));
        assertTrue(hasSelfLink, "deve contem o link 'delete' com DELETE e href correto");



    }

    @Test
    void create() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        PersonDTO dto = input.mockDTO(1);
        dto.setId(null);
        when(repository.save(any(Person.class))).thenReturn(person);

        var result = service.create(dto);

        assertNotNull(result, "entidade não pode ser nula");
        assertEquals(1L, result.getId());
        assertEquals(person.getFirstName(), result.getFirstName());
        assertEquals(person.getLastName(), result.getLastName());
        assertEquals(person.getAddress(), result.getAddress());
        assertEquals(person.getGender(), result.getGender());

        assertNotNull(result.getLinks());
        boolean hasSelfLink = result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("self")
                                && link.getHref().endsWith("/person/1")
                                &&  link.getType().equals("GET"));
        assertTrue(hasSelfLink, "deve contem o link self com GET e href correto");

        hasSelfLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/person")
                        && link.getType().equals("GET"));
        assertTrue(hasSelfLink, "deve contem o link 'findAll' com GET e href correto");

        hasSelfLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/person")
                        && link.getType().equals("POST"));
        assertTrue(hasSelfLink, "deve contem o link 'create' com POST e href correto");

        hasSelfLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/person")
                        && link.getType().equals("PUT"));
        assertTrue(hasSelfLink, "deve contem o link 'update' com PUT e href correto");

        hasSelfLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/person/1")
                        && link.getType().equals("DELETE"));
        assertTrue(hasSelfLink, "deve contem o link 'delete' com DELETE e href correto");

    }

    @Test
    void createWithNullPerson(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.create(null));

        String expectedMessage = "Verifique os campos, e tente novamente";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    void findAll() {
        List<Person> personList = input.mockEntityList();

        //quando "alguem" chamar o metodo do repository, retorne esse "objeto"
        when(repository.findAll()).thenReturn(personList);

        // aqui o "alguem" chamou o metodo do repository, então eu devolvo aquele objeto mockado
        var result = service.findAll();

        assertNotNull(result, "a list não deve ser nula");
        assertEquals(personList.size(), result.size());

        //pega esse ternário dentro do for kkkk
        for (int i = 0; i < result.size(); i = (i % 3 == 0 ? i +2 : i + 1)) {
            var pass = i;
            System.out.println(pass);
            var resultDTO = result.get(pass);
            assertEquals("First Name Test"+pass, resultDTO.getFirstName());
            assertEquals("Last Name Test"+pass, resultDTO.getLastName());
            assertEquals("Address Test"+pass, resultDTO.getAddress());
            assertEquals(personList.get(pass).getGender(), resultDTO.getGender());


            assertNotNull(resultDTO.getLinks());
            boolean hasSelfLink = resultDTO.getLinks().stream()
                    .anyMatch(link ->
                            link.getRel().value().equals("self")
                                    && link.getHref().endsWith("/person/"+pass)
                                    &&  link.getType().equals("GET"));
            assertTrue(hasSelfLink, "deve contem o link self com GET e href correto");

            hasSelfLink = resultDTO.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("findAll")
                            && link.getHref().endsWith("/person")
                            && link.getType().equals("GET"));
            assertTrue(hasSelfLink, "deve contem o link 'findAll' com GET e href correto");

            hasSelfLink = resultDTO.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("create")
                            && link.getHref().endsWith("/person")
                            && link.getType().equals("POST"));
            assertTrue(hasSelfLink, "deve contem o link 'create' com POST e href correto");

            hasSelfLink = resultDTO.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("update")
                            && link.getHref().endsWith("/person")
                            && link.getType().equals("PUT"));
            assertTrue(hasSelfLink, "deve contem o link 'update' com PUT e href correto");

            hasSelfLink = resultDTO.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("delete")
                            && link.getHref().endsWith("/person/"+pass)
                            && link.getType().equals("DELETE"));
            assertTrue(hasSelfLink, "deve contem o link 'delete' com DELETE e href correto");

            verify(repository, times(1)).findAll();
        }
    }

    @Test
    void update() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        PersonDTO dto = input.mockDTO(1);
        dto.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        var result = service.update(dto);

        assertNotNull(result, "entidade não pode ser nula");
        assertEquals(1L, result.getId());
        assertEquals(person.getFirstName(), result.getFirstName());
        assertEquals(person.getLastName(), result.getLastName());
        assertEquals(person.getAddress(), result.getAddress());
        assertEquals(person.getGender(), result.getGender());

        assertNotNull(result.getLinks());
        boolean hasSelfLink = result.getLinks().stream()
                .anyMatch(link ->
                        link.getRel().value().equals("self")
                                && link.getHref().endsWith("/person/1")
                                &&  link.getType().equals("GET"));
        assertTrue(hasSelfLink, "deve contem o link self com GET e href correto");

        hasSelfLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/person")
                        && link.getType().equals("GET"));
        assertTrue(hasSelfLink, "deve contem o link 'findAll' com GET e href correto");

        hasSelfLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/person")
                        && link.getType().equals("POST"));
        assertTrue(hasSelfLink, "deve contem o link 'create' com POST e href correto");

        hasSelfLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/person")
                        && link.getType().equals("PUT"));
        assertTrue(hasSelfLink, "deve contem o link 'update' com PUT e href correto");

        hasSelfLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/person/1")
                        && link.getType().equals("DELETE"));
        assertTrue(hasSelfLink, "deve contem o link 'delete' com DELETE e href correto");
    }

    @Test
    void updateWithNullPerson(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.update(null));

        String expectedMessage = "Verifique os campos, e tente novamente";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void delete() {
        Person person = input.mockEntity(1);
        person.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        service.delete(1L);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(any(Person.class));
    }
}