package dutkercz.com.github.integration.tests.config;

public interface TestConfigs {
    int SERVER_PORT = 8888;

    String HEADER_PARAM_AUTHORIZATION = "Authorization";
    String HEADER_PARAM_ORIGIN = "Origin";
    String ORIGIN_AUTHORIZED = "http://localhost:8888";
    String ORIGIN_UNAUTHORIZED = "http://localhost:2121";
}
