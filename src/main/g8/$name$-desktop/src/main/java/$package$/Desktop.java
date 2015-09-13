package $package$;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Launches JavaFx application.
 */
@SpringBootApplication
public class Desktop extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //
        String[] args = getParameters().getRaw().toArray(new String[0]);

        SpringApplication application = new SpringApplication(Desktop.class);
        application.setWebEnvironment(false);
        ApplicationContext applicationContext = application.run(args);

        //
        primaryStage.setScene(new Scene(new Label("Hello World")));
        primaryStage.show();
    }

    /**
     * Entry point of the program.
     *
     * @param args  command line arguments
     */
    public static void main(String... args) {
        launch(args);
    }

}
