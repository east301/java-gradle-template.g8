package $package$;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Launches HTTP server.
 */
@SpringBootApplication
public class Server {

    /**
     * Entry point of the program.
     *
     * @param args  command line arguments
     */
    public static void main(String... args) {
        SpringApplication.run(Server.class, args);
    }

}
