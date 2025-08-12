package dutkercz.com.github.integration.tests;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;


/*
Isso diz para o Spring Test carregar o contexto de aplicação usando
 um initializer customizado — no caso, AbstracIntegrationTest.Initializer.

O initializer é chamado antes do Spring criar o contexto, permitindo configurar
variáveis de ambiente, propriedades, ou até iniciar serviços externos
 */
@ContextConfiguration(initializers = AbstracIntegrationTest.Initializer.class)
public class AbstracIntegrationTest {

    /***   AbstracIntegrationTest
     * classe base para testes de integração.
     * Todas as classes de teste que estendem essa vão herdar essa
     * não precisa repetir a inicialização de containers de banco.
     */



    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        /** Initializer
         * ApplicationContextInitializer é uma interface do Spring que você implementa
         * para mexer no ApplicationContext antes do refresh dele.
         * Dentro dela, você pode setar properties que vão ser usadas na configuração da aplicação.
         */



        static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:9.1.0");
        //o próprio MySQLContainer já define algumas coisas específicas para MySQL, como
        //portas e variáveis de ambiente padrãop

        private static void startContainers() {
            //Esse mét.odo é chamado antes de injetar as propriedades do container no Spring (host, porta, usuário, senha, etc.).
            Startables.deepStart(Stream.of(mysql)).join();
        }

        private Map<String, Object> createConnectionConfiguration() {
            return Map.of(
                    "spring.datasource.url", mysql.getJdbcUrl(),
                    "spring.datasource.username", mysql.getUsername(),
                    "spring.datasource.password", mysql.getPassword()
            );
        }

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            //1º passo:
            startContainers();
            //2º passo:
           ConfigurableEnvironment environment = applicationContext.getEnvironment();

            MapPropertySource testcontainers = new MapPropertySource("testcontainers",
                    createConnectionConfiguration());
            environment.getPropertySources().addFirst(testcontainers);
        }
    }

    /*
    RESUMO
    O metodo 'initilize' é o inicio (rly?)

     1º COMEÇA CHAMANDO O METODO starContainers()
        esse metodo usa uma o Test Containers (dependencia maven) para inicializar uma instancia do MySQL Container,
        no nosso causo, 1 unico container do MYSQL para todos os testes (performance)

     2º pegamos o contexto do spring, adicionamos novas propiedades do "testcontainers" antes de todoas as outras,
        com as propiedades setadas no MapPropertySource() criadas dinamicamentes como url, pw, username

     3º Adiciona essas configurações de conexão e adiciona ao ambiente do spring e ai o spring termina de inicializar,
     inicia os testes, e ao fim o container é destruido

             */
}
