package dutkercz.com.github.integration.tests.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dutkercz.com.github.integration.tests.config.TestConfigs;
import dutkercz.com.github.integration.tests.dto.PersonDTO;
import dutkercz.com.github.integration.tests.testcontainers.AbstractIntegrationTest;
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

    @BeforeAll
    static void setUp() {
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
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_AUTORIZED)
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

        //pegando o JSON da resposta (content) e
        // convertendo ele de volta para um objeto Java do tipo PersonDTO
        // usando o objectMapper do Jackson para a deserealização
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
    @Order(2)
    void createWithWrongOrigin() throws JsonProcessingException {
        requestSpecification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_UNAUTHORIZED)
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
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);
    }

    @Test
    @Order(3)
    void findById() throws JsonProcessingException {
        requestSpecification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_AUTORIZED)
                .setBasePath("/api/person/")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(requestSpecification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", personDTO.getId()) //verficar a string do param no controller, tem que ser a mesma
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract().asString();

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
    @Order(4)
    void findByIdWithWrongOrigin() throws JsonProcessingException {
        requestSpecification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_UNAUTHORIZED)
                .setBasePath("/api/person/")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(requestSpecification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", personDTO.getId()) //verficar a string do param no controller, tem que ser a mesma
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract().asString();

        assertEquals("Invalid CORS request", content);
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