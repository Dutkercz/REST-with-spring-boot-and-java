package dutkercz.com.github.integration.tests.controllers.cors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dutkercz.com.github.integration.tests.config.TestConfigs;
import dutkercz.com.github.integration.tests.dto.PersonDTO;
import dutkercz.com.github.integration.tests.testcontainers.AbstractIntegrationTest;
import dutkercz.com.github.repositories.PersonRepository;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static dutkercz.com.github.models.PersonGenderEnum.MALE;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //defini que os metodos sejam executados na ordem em que estão
class PersonControllerCorsTest extends AbstractIntegrationTest {


    private static RequestSpecification requestSpecification;
    private static ObjectMapper objectMapper;
    private static PersonDTO personDTO;
    @Autowired
    private PersonRepository personRepository;

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
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_AUTHORIZED)
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
        var result = objectMapper.readValue(content, PersonDTO.class);

        assertResults(personDTO, result);

        //para ser usado nos próximos testes
        personDTO.setId(result.getId());
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
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_AUTHORIZED)
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

        var result = objectMapper.readValue(content, PersonDTO.class);

        assertResults(personDTO, result);
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
    @Order(5)
    void findAll() throws JsonProcessingException {
        personRepository.deleteAll();
        assertEquals(0, personRepository.findAll().size());
        requestSpecification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_AUTHORIZED)
                .setBasePath("/api/person")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        //Aqui criamos uma iteração para criar novos objetos na DB, e guarda o resultado em uma lista
        //ATENÇÃO, POR ESTARMOS USANDO UM MESMO CONTAINER PARA TODOS OS TESTES,
        // AQUI A NOSSA LISTA TAMBEM TERA O TESTE REALIZADOS
        // EM PersonControllerJsonTest.class
        //se rodarmos todos os testes juntos, poderemos ter essa alteração no tamnho da lista
        List<PersonDTO> expectedList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            PersonDTO dto = new PersonDTO(null,
                    "Name" + i,
                    "LastName" + i,
                    "Adress"+i,
                    MALE);

            var content = given(requestSpecification)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(dto)
                    .when()
                    .post()
                    .then()
                    .statusCode(201)
                    .extract()
                    .body()
                    .asString();

            PersonDTO created = objectMapper.readValue(content, PersonDTO.class);
            expectedList.add(created);
        }

        var content = given(requestSpecification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body().asString();

        List<PersonDTO> result = objectMapper.readValue(content, new TypeReference<List<PersonDTO>>() {});
        result = result.stream().sorted(Comparator.comparing(PersonDTO::getId)).toList();

        assertNotNull(result);
        assertTrue(result.getFirst().getId() > 0);
        assertEquals(expectedList.size(), result.size());
        assertFalse(result.isEmpty());

        for (int i = 0; i < result.size(); i++) {

            assertResults(expectedList.get(i), result.get(i));
        }
    }

    private void assertResults(PersonDTO expected, PersonDTO result) {
        assertNotNull(result.getId(), "O id não deve ser nulo");
        assertNotNull(result.getFirstName(), "O Nome não deve ser nulo");
        assertNotNull(result.getLastName(), "O Sobrenome não deve ser nulo");
        assertNotNull(result.getAddress(), "O Endereço não deve ser nulo");
        assertNotNull(result.getGender(), "O Genero não deve ser nulo");

        assertEquals(expected.getFirstName(), result.getFirstName());
        assertEquals(expected.getLastName(), result.getLastName());
        assertEquals(expected.getAddress(), result.getAddress());
        assertEquals(expected.getGender(), result.getGender());
        assertTrue(result.getEnabled());
    }

    private void mockPerson() {
        personDTO.setFirstName("Cristiaum");
        personDTO.setLastName("Rosa");
        personDTO.setAddress("Dom Pedrito - RS - BR");
        personDTO.setGender(MALE);
        personDTO.setEnabled(true);
    }


}