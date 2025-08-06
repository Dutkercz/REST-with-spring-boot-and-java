package dutkercz.com.github.services;

import dutkercz.com.github.models.Person;
import dutkercz.com.github.repositories.PersonRepository;
import dutkercz.com.github.unitetests.mapper.mock.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.apache.commons.lang3.Validate.notNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}