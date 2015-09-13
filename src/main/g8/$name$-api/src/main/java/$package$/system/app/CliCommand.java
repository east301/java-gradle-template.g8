package $package$.system.app;

import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparsers;
import org.springframework.context.ApplicationContext;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * CLI command.
 */
public interface CliCommand {

    /**
     * Registers a task.
     *
     * @param subparsers    parent parser
     * @return  ID of registered task
     */
    @NotNull
    Optional<String> register(@NotNull Subparsers subparsers);

    /**
     * Executes a task.
     *
     * @param arguments             command line arguments
     * @param applicationContext    application context
     * @throws Exception    an error happened during execution
     */
    void execute(@NotNull Namespace arguments, @NotNull ApplicationContext applicationContext)
            throws Exception;

}
