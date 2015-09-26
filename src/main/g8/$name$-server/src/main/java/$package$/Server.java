package $package$;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Launches HTTP server.
 */
@SpringBootApplication
@Configuration
public class Server {

    /**
     * Entry point of the program.
     *
     * @param args  command line arguments
     */
    public static void main(String... args) {
        SpringApplication.run(Server.class, args);
    }

    /**
     * Creates {@link ServletContextInitializer} to change name of session cookie for security reasons.
     *
     * @return  {@link ServletContextInitializer} to change session cookie name
     */
    @Bean
    public ServletContextInitializer sessionCookieNameServletContextInitializer() {
        return c -> c.getSessionCookieConfig().setName("PHPSESSID");
    }

}
