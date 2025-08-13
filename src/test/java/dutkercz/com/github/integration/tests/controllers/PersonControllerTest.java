package dutkercz.com.github.integration.tests.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.log.Log;
import dutkercz.com.github.integration.tests.config.TestConfigs;
import dutkercz.com.github.integration.tests.dto.PersonDTO;
import dutkercz.com.github.integration.tests.testcontainers.AbstractIntegrationTest;
import dutkercz.com.github.models.PersonGenderEnum;
import dutkercz.com.github.repositories.PersonRepository;
import dutkercz.com.github.services.PersonService;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static dutkercz.com.github.models.PersonGenderEnum.MALE;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //defini que os metodos sejam executados na ordem em que estão
class PersonControllerTest extends AbstractIntegrationTest {


    private static RequestSpecification requestSpecification;
    private static ObjectMapper objectMapper;
    private static PersonDTO personDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        //ignorar o erro quando encontrar atributos desconhecidos. caso não sete esse
        //disable, iremos ter erros em relação ao links HATEOAS
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        personDTO = new PersonDTO();
    }

    @Test
    @Order(1)
    void create() throws JsonProcessingException {
        mockPerson();

        requestSpecification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_T1_ORIGIN)
                .setBasePath("/api/person")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(requestSpecification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(personDTO)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();
        personDTO = objectMapper.readValue(content, PersonDTO.class);

        assertNotNull(personDTO.getId(), "O id não deve ser nulo");
        assertNotNull(personDTO.getFirstName(), "O Nome não deve ser nulo");
        assertNotNull(personDTO.getLastName(), "O Sobrenome não deve ser nulo");
        assertNotNull(personDTO.getAddress(), "O Endereço não deve ser nulo");
        assertNotNull(personDTO.getGender(), "O Genero não deve ser nulo");

        assertTrue(personDTO.getId() > 0L, "O id deve ser maior que 0");

        assertEquals("Cristiaum", personDTO.getFirstName());
        assertEquals("Rosa", personDTO.getLastName());
        assertEquals("Dom Pedrito - RS - BR", personDTO.getAddress());
        assertEquals(MALE, personDTO.getGender());
    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findAll() {
    }

    private void mockPerson() {
        personDTO.setFirstName("Cristiaum");
        personDTO.setLastName("Rosa");
        personDTO.setAddress("Dom Pedrito - RS - BR");
        personDTO.setGender(MALE);
    }
}