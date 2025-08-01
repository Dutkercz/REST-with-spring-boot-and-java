package dutkercz.com.github;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = Startup.class)
@ActiveProfiles("test")
class StartupTests {

	@Test
	void contextLoads() {
	}

}
