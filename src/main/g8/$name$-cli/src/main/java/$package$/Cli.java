package $package$;

import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparsers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import $package$.system.app.CliCommand;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Optional;

/**
 * @author Shu Tadaka
 */
@SpringBootApplication
@Slf4j
public class Cli {

    /**
     * Entry point of the program.
     *
     * @param args  command line arguments
     */
    @SneakyThrows
    public static void main(String... args) {
        try (ConfigurableApplicationContext applicationContext = newApplicationContext(args)) {
            runCommand(applicationContext, args);
        }
    }

    private static ConfigurableApplicationContext newApplicationContext(@NotNull String[] args) {
        SpringApplication application = new SpringApplication(Cli.class);
        application.setWebEnvironment(false);
        return application.run(args);
    }

    @SneakyThrows
    @SuppressWarnings("rawtypes")
    private static void runCommand(@NotNull ApplicationContext applicationContext, @NotNull String[] args) {
        //
        ArgumentParser parser = ArgumentParsers.newArgumentParser("$name$-cli");
        Subparsers subparsers = parser.addSubparsers().dest("command");

        Map<String, CliCommand> commandIdMap = Maps.newHashMap();
        for (CliCommand command : applicationContext.getBeansOfType(CliCommand.class).values()) {
            Optional<String> commandId = command.register(subparsers);
            if (commandId.isPresent()) {
                commandIdMap.put(commandId.get(), command);
            }
        }

        //
        Namespace arguments;
        try {
            arguments = parser.parseArgs(args);
        } catch (ArgumentParserException ex) {
            parser.handleError(ex);
            return;
        }

        //
        CliCommand command = commandIdMap.get(arguments.getString("command"));
        try {
            command.execute(arguments, applicationContext);
        } catch (Exception ex) {
            log.error("an error happened during execution of a command", ex);
        }
    }

}
