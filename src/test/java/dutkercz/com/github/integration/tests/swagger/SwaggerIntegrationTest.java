package dutkercz.com.github.integration.tests.swagger;

import dutkercz.com.github.integration.tests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static dutkercz.com.github.integration.tests.config.TestConfigs.SERVER_PORT;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) //utiliza a porta do app.yml
class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@Test
	void shouldDisplaySwaggerUIPage	() {
		//io.restAssured
		var content = given()
					.basePath("/swagger-ui/index.html")
						.port(SERVER_PORT)
					.when()
						.get()
					.then()
						.statusCode(200)
					.extract()
						.body()
							.asString();
		assertTrue(content.contains("Swagger UI"));
	}

}
